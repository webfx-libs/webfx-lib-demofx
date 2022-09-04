/*
 * Copyright (c) 2015-2016 Chris Newland.
 * Licensed under https://github.com/chriswhocodes/demofx/blob/master/LICENSE-BSD
 */
package com.chrisnewland.demofx.effect.text;

import com.chrisnewland.demofx.DemoConfig;
import com.chrisnewland.demofx.effect.AbstractEffect;
import com.chrisnewland.demofx.util.TextUtil;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TextFlash extends AbstractEffect
{
	private List<String> stringList;

	private static final double INITIAL_FONT_SIZE = 80;
	private Font font = Font.font("Georgia", FontWeight.BOLD, INITIAL_FONT_SIZE);

	private long fadeInMillis;
	private long stayMillis;
	private long fadeOutMillis;

	private long time;

	private int stringIndex = 0;

	private boolean loopStringList = true;

	private double yPercent;
	private double textYPos;

	private Color fontColour = Color.WHITE;

	public TextFlash(DemoConfig config)
	{
		super(config);

		init("The end is the beginning is the end".toUpperCase().split(" "), true, Color.WHITE, font, 50, 200);
	}
	
	public TextFlash(DemoConfig config, String string)
	{
		super(config);

		init(string.toUpperCase().split(" "), true, Color.WHITE, font, 50, 200);
	}

	public TextFlash(DemoConfig config, String string, boolean loopStringList, long fadeInMillis, long stayMillis, long fadeOutMillis) {
		super(config);

		init(string.toUpperCase().split(" "), loopStringList, Color.WHITE, font, 50, fadeInMillis,  stayMillis, fadeOutMillis);
	}

	public TextFlash(DemoConfig config, String string, Font font, Color colour)
	{
		super(config);

		init(string.toUpperCase().split(" "), true, colour, font, 50, 200);
	}

	public TextFlash(DemoConfig config, String string, String split, double yPercent, double fontSize, long showMillis)
	{
		super(config);

		init(string.toUpperCase().split(split), true, Color.WHITE, Font.font("Linux Biolinum Keyboard O", FontWeight.BOLD, fontSize), yPercent, showMillis);
	}


	private void init(String[] strings, boolean loopStringList, Color colour, Font font, double yPercent, long showMillis) {
		init(strings, loopStringList, colour, font, yPercent, 0, 0, showMillis);
	}

	private void init(String[] strings, boolean loopStringList, Color colour, Font font, double yPercent, long fadeInMillis, long stayMillis, long fadeOutMillis)
	{
		stringList = new ArrayList<>();

		stringList.addAll(Arrays.asList(strings));

		this.loopStringList = loopStringList;

		this.fontColour = colour;
		this.font = font;
		this.yPercent = yPercent;
		this.fadeInMillis = fadeInMillis;
		this.stayMillis = stayMillis;
		this.fadeOutMillis = fadeOutMillis;

		precalulateStringDimensions();
	}

	private final void precalulateStringDimensions()
	{
		gc.setFont(font);

		for (String str : stringList)
		{
			TextUtil.getStringDimensions(font, gc, str);
		}

		textYPos = yPercent * height / 100;
	}

	@Override
	public void renderForeground()
	{
		long now = System.currentTimeMillis();

		if (time == 0)
			time = now;

		long elapsed = now - time; // 0 .. showMillis

		double opacityFactor = elapsed < fadeInMillis ? ((double) elapsed / (double) fadeOutMillis) : elapsed < fadeInMillis + stayMillis ? 1 : 1 - ((double) (elapsed - fadeInMillis - stayMillis) / (double) fadeOutMillis); // 1
																				// -
																				// (0
																				// ..
																				// 1)

		Color derivedColor = fontColour.deriveColor(0, 1.0, 1.0, opacityFactor);

		gc.setFill(derivedColor);

		if (!effectFinished)
		{
			plotText();
		}

		if (elapsed > fadeInMillis + stayMillis + fadeOutMillis)
		{
			stringIndex++;

			if (stringIndex == stringList.size())
			{
				if (loopStringList)
				{
					stringIndex = 0;
				}
				else
				{
					effectFinished = true;
				}
			}

			time = now;
		}
	}

	private final void plotText()
	{
		gc.setFont(font);

		String str = stringList.get(stringIndex);

		Point2D dimensions = TextUtil.getStringDimensions(font, gc, str);

		double strWidth = dimensions.getX();
		double strHeight = dimensions.getY();

		double x = halfWidth - strWidth / 2;
		double y = textYPos + strHeight / 2;

		gc.fillText(str, x, y);
	}
}