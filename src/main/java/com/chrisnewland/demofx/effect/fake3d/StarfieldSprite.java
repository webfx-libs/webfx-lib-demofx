/*
 * Copyright (c) 2015-2016 Chris Newland.
 * Licensed under https://github.com/chriswhocodes/demofx/blob/master/LICENSE-BSD
 */
package com.chrisnewland.demofx.effect.fake3d;

import com.chrisnewland.demofx.DemoConfig;
import com.chrisnewland.demofx.effect.AbstractEffect;
import com.chrisnewland.demofx.util.ImageUtil;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class StarfieldSprite extends AbstractEffect
{
	private final double[] starX;
	private final double[] starY;
	private final double[] starZ;

	private final int[] spriteIndexes;

	private static final double SPEED = 0.01;
	private static final double MAX_DEPTH = 5;

	private final boolean spin = true;

	private final Image sprite;
	private final Color[] colors;
	private final Image[] colorSprites;

	private final boolean adaptive = true;

	public StarfieldSprite(DemoConfig config) {
		this(config, 5000, 800, 600); // 5000 stars on a 800x600 screen or wider, less on a smaller screen
	}

	public StarfieldSprite(DemoConfig config, int maxStarCount, double ceilingWidth, double ceilingHeight) {
		this(config, Math.min(maxStarCount, (int) (config.getWidth() * config.getHeight() / (ceilingWidth * ceilingHeight) * maxStarCount)), ImageUtil.loadImageFromResources("starshine.png"), 8);
	}

	public StarfieldSprite(DemoConfig config, int starCount, Image sprite, int colorCount)
	{
		super(config);
		
		this.itemCount = starCount;

		this.sprite = sprite;

		colors = new Color[colorCount];
		colorSprites = new Image[colorCount];
		for (int colorIndex = 0; colorIndex < colorCount; colorIndex++)
			colors[colorIndex] = Color.hsb(getRandomColour().getHue(), colorIndex == 0 ? 1 : 0.5, 1);
		if (sprite.getProgress() >= 1)
			computeColorSprites();
		else
			sprite.progressProperty().addListener((observableValue, number, progress) -> { if (progress.intValue() >= 1) computeColorSprites();});

		// Building stars
		starX = new double[itemCount];
		starY = new double[itemCount];
		starZ = new double[itemCount];
		spriteIndexes = new int[itemCount];

		for (int i = 0; i < itemCount; i++)
		{
			starX[i] = precalc.getSignedRandom() * halfWidth;
			starY[i] = precalc.getSignedRandom() * halfHeight;
			respawn(i);
		}
	}

	private void computeColorSprites() {
		for (int colorIndex = 0; colorIndex < colorSprites.length; colorIndex++)
			colorSprites[colorIndex] = ImageUtil.tintImage(sprite, colors[colorIndex].getHue(), colors[colorIndex].getSaturation());
	}

	private long lastDuration; // Used for adaptive
	private int renderedItemCount;

	@Override
	public void renderForeground()
	{
		if (spin)
		{
			rotateCanvasAroundCentre(0.5);
		}

		long now;
		if (!adaptive)
			renderedItemCount = itemCount;
		else {
			now = System.currentTimeMillis();
			if (lastDuration == 0)
				renderedItemCount = itemCount / 2;
			else {
				double exceedFactor = (double) lastDuration / 16; // We aim 16ms for the plot
				if (exceedFactor < 0.8)
					renderedItemCount += 100;
				else if (exceedFactor > 1.2)
					renderedItemCount -= 100;
				if (renderedItemCount > itemCount)
					renderedItemCount = itemCount;
			}
		}

		for (int i = 0; i < renderedItemCount; i++)
		{
			moveStar(i);

			plotStar(i);
		}

		if (adaptive)
			lastDuration = System.currentTimeMillis() - now;
	}

	private void moveStar(int i)
	{
		starZ[i] -= SPEED;
	}

	private void respawn(int i)
	{
		starZ[i] = precalc.getUnsignedRandom() * MAX_DEPTH;
		spriteIndexes[i] = (int) (precalc.getUnsignedRandom() * colorSprites.length);
	}

	private double translateX(int i)
	{
		return starX[i] / starZ[i];
	}

	private double translateY(int i)
	{
		return starY[i] / starZ[i];
	}

	private void plotStar(int i)
	{
		double x = halfWidth + translateX(i);
		double y = halfHeight + translateY(i);

		if (isOnScreen(x, y))
		{
			double size = 8 / starZ[i];
			int colorIndex = spriteIndexes[i];
			if (size < 2) { // plotting just a pixel for small stars (a bit faster)
				gc.setFill(colors[colorIndex]);
				gc.fillRect(x, y, 1, 1);
			} else
				gc.drawImage(colorSprites[colorIndex], x, y, size, size);
		}
		else
		{
			respawn(i);
		}
	}

	private boolean isOnScreen(double x, double y)
	{
		if (spin)
		{
			double max = 1.4 * Math.max(width, height);

			return x > -max && y > -max && x < max && y < max;
		}
		else
		{
			return x > 0 && y > 0 && x < width && y < height;
		}
	}
}