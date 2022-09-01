/*
 * Copyright (c) 2015-2016 Chris Newland.
 * Licensed under https://github.com/chriswhocodes/demofx/blob/master/LICENSE-BSD
 */
package com.chrisnewland.demofx.effect.sprite;

import com.chrisnewland.demofx.DemoConfig;
import com.chrisnewland.demofx.effect.AbstractEffect;
import com.chrisnewland.demofx.util.ImageUtil;
import com.chrisnewland.demofx.util.MaskingSystem;
import javafx.scene.image.Image;

public class MaskStack extends AbstractEffect
{
	private final Image image;
	private final int count;
	private Image[] imageRing;
	private double[] ringAngle;
	private double[] angleInc;

	private static int thickness;

	public MaskStack(DemoConfig config)
	{
		this(config, ImageUtil.loadImageFromResources("tiger.jpeg"));
	}
	
	public MaskStack(DemoConfig config, Image image)
	{
		this(config, 16, image);
	}

	public MaskStack(DemoConfig config, int count, Image image) {
		super(config);
		this.image = image;
		this.count = count;

		if (image.getWidth() > 0)
			init(count, image);
	}

	private void init(int count, Image image)
	{
		itemCount = count;

		imageRing = new Image[count];

		ringAngle = new double[count];

		angleInc = new double[count];

		double angle = 0;

		double imgWidth = 960; //image.getWidth();
		double imgHeight = 640; // image.getHeight();
		int diameterOuter = (int) Math.min(imgWidth, imgHeight);

		thickness = diameterOuter / count;

		for (int i = 0; i < itemCount; i++)
		{
			ringAngle[i] = i * angle;

			angleInc[i] = (i + 1.0);

			int diameterInner = diameterOuter - thickness;

			if (i == itemCount - 1)
			{
				diameterInner = 0;
			}

			Image mask = MaskingSystem.createMaskRing(diameterOuter, diameterInner);
			//Image mask = MaskingSystem.createMaskBorder(diameterOuter, diameterOuter, thickness);
			
			int offsetX = (int) (imgWidth - mask.getWidth()) / 2;
			int offsetY = (int) (imgHeight - mask.getHeight()) / 2;

			imageRing[i] = MaskingSystem.applyMask(mask, image, offsetX, offsetY);

			diameterOuter = diameterInner;

		}
	}

	@Override
	public void renderForeground()
	{
		if (imageRing == null) {
			if (image.getWidth() > 0)
				init(count, image);
			else
				return;
		}

		for (int i = 0; i < itemCount; i++)
		{
			move(i);

			render(i);
		}
	}

	private final void move(int i)
	{
		ringAngle[i] += angleInc[i];
	}

	private final void render(int i)
	{
		Image image = imageRing[i];

		double imageWidth = image.getWidth();
		double imageHeight = image.getHeight();

		double x = thickness * precalc.sin(ringAngle[i]);
		double y = thickness * precalc.cos(ringAngle[i]);

		x += halfWidth - imageWidth / 2;
		y += halfHeight - imageHeight / 2;

		gc.drawImage(imageRing[i], x, y, imageWidth, imageHeight);
	}
}