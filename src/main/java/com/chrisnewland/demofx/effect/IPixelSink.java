/*
 * Copyright (c) 2016 Chris Newland.
 * Licensed under https://github.com/chriswhocodes/demofx/blob/master/LICENSE-BSD
 */
package com.chrisnewland.demofx.effect;

import dev.webfx.kit.launcher.spi.FastPixelReaderWriter;

public interface IPixelSink
{
	int getWidth();
	
	int getHeight();
	
	//PixelWriter getPixelWriter();

	FastPixelReaderWriter getFastPixelReaderWriter();
	
	void redraw();
}
