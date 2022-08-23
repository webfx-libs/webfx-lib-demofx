package com.chrisnewland.demofx.effect.addon;

import com.chrisnewland.demofx.effect.AbstractEffect;

/**
 * @author Bruno Salmon
 */
public class AddOnFadeInEffect extends AbstractAddOnFadeEffect {

    public AddOnFadeInEffect(AbstractEffect effect) {
        this(effect, 5000);
    }

    public AddOnFadeInEffect(AbstractEffect effect, long duration) {
        super(effect, duration, true, false);
    }
}
