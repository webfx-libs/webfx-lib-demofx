package com.chrisnewland.demofx.effect.addon;

import com.chrisnewland.demofx.effect.AbstractEffect;

/**
 * @author Bruno Salmon
 */
public class FadeInOutAddOnEffect extends AbstractFadeAddOnEffect {

    public FadeInOutAddOnEffect(AbstractEffect effect) {
        this(effect, 4000);
    }

    public FadeInOutAddOnEffect(AbstractEffect effect, long duration) {
        super(effect, duration, true, true);
    }
}
