package com.chrisnewland.demofx.effect.addon;

import com.chrisnewland.demofx.effect.AbstractEffect;
import com.chrisnewland.demofx.util.PreCalc;

/**
 * @author Bruno Salmon
 */
public class AbstractAddOnFadeEffect extends AbstractAddOnEffect {

    private final long duration;
    private long fadeStart;
    private final boolean fadeIn;

    public AbstractAddOnFadeEffect(AbstractEffect effect, long duration, boolean fadeIn) {
        super(effect);
        this.duration = duration;
        this.fadeIn = fadeIn;
    }

    @Override
    public void renderForeground() {
        long now = System.currentTimeMillis();
        if (fadeStart == 0)
            fadeStart = now + (fadeIn ? 0 : effect.getStopOffsetMillis() - duration);
        long elapsed = now - fadeStart;
        if (elapsed < 0 || elapsed > duration)
            super.renderForeground();
        else {
            double alpha = (double) elapsed / (double) duration;
            alpha = PreCalc.clampDouble(alpha, 0, 1);
            if (!fadeIn)
                alpha = 1 - alpha;
            double previousAlpha = effect.gc.getGlobalAlpha();
            effect.gc.setGlobalAlpha(alpha);
            super.renderForeground();
            effect.gc.setGlobalAlpha(previousAlpha);
        }
    }
}
