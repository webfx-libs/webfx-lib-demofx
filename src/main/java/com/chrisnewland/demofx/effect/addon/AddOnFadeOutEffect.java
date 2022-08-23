package com.chrisnewland.demofx.effect.addon;

import com.chrisnewland.demofx.effect.AbstractEffect;

/**
 * @author Bruno Salmon
 */
public class AddOnFadeOutEffect extends AbstractAddOnFadeEffect {

    public AddOnFadeOutEffect(AbstractEffect effect) {
        this(effect, 5000);
    }

    public AddOnFadeOutEffect(AbstractEffect effect, long duration) {
        super(effect, duration, false, true);
    }
}
