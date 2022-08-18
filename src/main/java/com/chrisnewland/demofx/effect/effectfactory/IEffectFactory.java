/*
 * Copyright (c) 2015-2016 Chris Newland.
 * Licensed under https://github.com/chriswhocodes/demofx/blob/master/LICENSE-BSD
 */
package com.chrisnewland.demofx.effect.effectfactory;

import com.chrisnewland.demofx.DemoConfig;
import com.chrisnewland.demofx.effect.IEffect;

import java.util.List;

public interface IEffectFactory
{
	List<IEffect> getEffects(DemoConfig config);
}
