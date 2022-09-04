package com.chrisnewland.demofx.effect.addon;

import com.chrisnewland.demofx.ISpectrumDataProvider;
import com.chrisnewland.demofx.effect.AbstractEffect;
import com.chrisnewland.demofx.effect.spectral.ISpectralEffect;

/**
 * @author Bruno Salmon
 */
public class VolumeAddOnEffect extends AbstractAddOnEffect implements ISpectralEffect {

    private final HasVolume hasVolume;
    private final double maxVolume;
    private final double[] bandVolumes;

    private ISpectrumDataProvider spectrumProvider;

    public VolumeAddOnEffect(AbstractEffect effect, double maxVolume) {
        super(effect);
        hasVolume = (HasVolume) effect;
        this.maxVolume = maxVolume;
        bandVolumes = new double[hasVolume.getBandCount()];
    }

    @Override
    public void setSpectrumDataProvider(ISpectrumDataProvider provider) {
        spectrumProvider = provider;
    }

    @Override
    public void renderForeground() {
        int n = hasVolume.getBandCount();
        for (int i = 0; i < n; i++) {
            double oldVolume = bandVolumes[i];
            double newVolume = (spectrumProvider.getData()[i] + 60) / 60 * maxVolume;
            if (newVolume > oldVolume)
                newVolume = Math.min(newVolume, oldVolume + 0.005 * maxVolume);
            else
                newVolume = Math.max(newVolume, oldVolume - 0.005 * maxVolume);
            bandVolumes[i] = newVolume;
            hasVolume.setBandVolumes(bandVolumes);
        }
        super.renderForeground();
    }
}
