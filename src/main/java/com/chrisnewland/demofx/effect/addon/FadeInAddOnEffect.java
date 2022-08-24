package com.chrisnewland.demofx.effect.addon;

import com.chrisnewland.demofx.effect.AbstractEffect;

/**
 * @author Bruno Salmon
 */
public class FadeInAddOnEffect extends AbstractFadeAddOnEffect {

    public FadeInAddOnEffect(AbstractEffect effect) {
        this(effect, 4000);
    }

    public FadeInAddOnEffect(AbstractEffect effect, long duration) {
        super(effect, duration, true, false);
    }
}
