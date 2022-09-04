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
    private final int[] bandIndexes;
    private final double[] bandVolumes;

    private ISpectrumDataProvider spectrumProvider;

    public VolumeAddOnEffect(AbstractEffect effect, double maxVolume, int... bandIndexes) {
        super(effect);
        hasVolume = (HasVolume) effect;
        this.maxVolume = maxVolume;
        this.bandIndexes = bandIndexes;
        bandVolumes = new double[bandIndexes.length];
    }

    @Override
    public void setSpectrumDataProvider(ISpectrumDataProvider provider) {
        spectrumProvider = provider;
    }

    @Override
    public void renderForeground() {
        //StringBuilder sb = new StringBuilder();
        int n = bandIndexes.length;
        float[] data = spectrumProvider.getData();
        for (int i = 0; i < n; i++) {
            double newVolume = (data[bandIndexes[i]] + 60) / 60 * maxVolume;
            //sb.append(newVolume).append(',');
            if (i < n) {
                double oldVolume = bandVolumes[i];
                if (newVolume != oldVolume)
                    newVolume = oldVolume + (newVolume < oldVolume ? -0.1 : 0.1);
                bandVolumes[i] = newVolume;
            }
        }
        //Console.log(sb);
        hasVolume.setBandVolumes(bandVolumes);
        super.renderForeground();
    }
}
