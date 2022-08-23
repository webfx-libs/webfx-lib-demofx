package com.chrisnewland.demofx.effect.addon;

import com.chrisnewland.demofx.effect.AbstractEffect;
import com.chrisnewland.demofx.util.PreCalc;

/**
 * @author Bruno Salmon
 */
public class AbstractAddOnFadeEffect extends AbstractAddOnEffect {

    private final long duration;
    private final boolean fadeIn, fadeOut;
    private long fadeInStart, fadeOutStart;

    public AbstractAddOnFadeEffect(AbstractEffect effect, long duration, boolean fadeIn, boolean fadeOut) {
        super(effect);
        this.duration = duration;
        this.fadeIn = fadeIn;
        this.fadeOut = fadeOut;
    }

    @Override
    public void renderForeground() {
        long now = System.currentTimeMillis();
        if (fadeInStart == 0)
            fadeInStart = now;
        if (fadeOutStart == 0)
            fadeOutStart = now + (effect.getStopOffsetMillis() - effect.getStartOffsetMillis()) - duration;
        if (!applyFade(now - fadeInStart, true) && !applyFade(now - fadeOutStart, false))
            super.renderForeground();
    }

    private boolean applyFade(double elapsed, boolean fadeIn) {
        if (fadeIn && !this.fadeIn || !fadeIn && !this.fadeOut || elapsed < 0 || elapsed > duration)
            return false;
        double alpha = elapsed / (double) duration;
        alpha = PreCalc.clampDouble(alpha, 0, 1);
        if (!fadeIn)
            alpha = 1 - alpha;
        double previousAlpha = effect.gc.getGlobalAlpha();
        effect.gc.setGlobalAlpha(alpha);
        super.renderForeground();
        effect.gc.setGlobalAlpha(previousAlpha);
        return true;
    }
}
