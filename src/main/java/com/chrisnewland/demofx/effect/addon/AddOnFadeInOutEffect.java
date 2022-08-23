package com.chrisnewland.demofx.effect.addon;

import com.chrisnewland.demofx.effect.AbstractEffect;

/**
 * @author Bruno Salmon
 */
public class AddOnFadeInOutEffect extends AbstractAddOnFadeEffect {

    public AddOnFadeInOutEffect(AbstractEffect effect) {
        this(effect, 5000);
    }

    public AddOnFadeInOutEffect(AbstractEffect effect, long duration) {
        super(effect, duration, true, true);
    }
}
