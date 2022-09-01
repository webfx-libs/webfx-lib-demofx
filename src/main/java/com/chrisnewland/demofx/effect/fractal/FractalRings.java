/*
 * Copyright (c) 2019 Chris Newland.
 * Licensed under https://github.com/chriswhocodes/demofx/blob/master/LICENSE-BSD
 */
package com.chrisnewland.demofx.effect.fractal;

import com.chrisnewland.demofx.DemoConfig;
import com.chrisnewland.demofx.effect.AbstractEffect;
import com.chrisnewland.demofx.effect.addon.HasAngle;
import com.chrisnewland.demofx.util.ImageUtil;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

import java.util.ArrayList;
import java.util.List;

public class FractalRings extends AbstractEffect implements HasAngle
{
	private static final double SPEED = 1.05; // Was originally 1.025, but speed up to reduce the number of rings (otherwise slower animation frame in the browser)

	private static final double SPAWN_AT_RADIUS = 8;

	private static final double ROOT_2 = Math.sqrt(2.0);

	private List<FractalRing> renderListOld = new ArrayList<>();

	private List<FractalRing> renderListNew = new ArrayList<>();

	private int colourIndex = 0;

	private final Image[] image = new Image[64];

	private /*static*/ class FractalRing
	{
		private double centreX;
		private double centreY;
		private double radius;
		private boolean hasChildren = false;
		private int colourIndex;

		public FractalRing(double x, double y, double radius, int index)
		{
			this.centreX = x;
			this.centreY = y;
			this.radius = radius;
			this.colourIndex = index;
		}

		public void grow(double cx, double cy)
		{
			this.radius *= SPEED;

			centreX = cx + ((centreX - cx) * SPEED);
			centreY = cy + ((centreY - cy) * SPEED);
		}

		public boolean isOnScreen(double width, double height)
		{
			double testRadius = radius / ROOT_2;

			double cx = centreX - width / 2;
			double cy = centreY - height / 2;
			double x = cx * rotate.getMxx() + cy * rotate.getMxy();
			double y = cx * rotate.getMyx() + cy * rotate.getMyy();
			x+= width / 2;
			y+= height /2;

			double left = x - testRadius;
			double right = x + testRadius;
			double top = y - testRadius;
			double bottom = y + testRadius;

			return left > 0 && right < width && bottom > 0 && top < height;
		}
	}

	public FractalRings(DemoConfig config)
	{
		super(config);

		for (int i = 0; i < image.length; i++)
		{
			image[i] = createRing(256, 7, getRandomColour());
		}

		renderListNew.add(new FractalRing(halfWidth, halfHeight, 256, colourIndex));
	}

	private Image createRing(double diameter, double thickness, Color color)
	{
		fillBackground(Color.BLACK);

		gc.setFill(color);
		gc.fillOval(0, 0, diameter, diameter);

		gc.setFill(Color.BLACK);
		gc.fillOval(thickness, thickness, diameter - 2 * thickness, diameter - 2 * thickness);

		return ImageUtil.replaceColour(ImageUtil.createImageFromCanvas(gc.getCanvas(), diameter, diameter, true), Color.BLACK,
				Color.TRANSPARENT);
	}

	@Override public void renderForeground()
	{
		buildRenderList();

		incColourIndex();

		render();
	}

	private void incColourIndex()
	{
		colourIndex++;

		if (colourIndex >= image.length)
		{
			colourIndex = 0;
		}
	}

	private void buildRenderList()
	{
		renderListOld = renderListNew;

		final int oldRenderListSize = renderListOld.size();

		renderListNew = new ArrayList<>(oldRenderListSize);

		for (int i = 0; i < oldRenderListSize; i++)
		{
			update(i);
		}
	}

	private final Rotate rotate = new Rotate(0);

	@Override
	public double getAngle() {
		return rotate.getAngle();
	}

	@Override
	public void setAngle(double angle) {
		rotate.setAngle(angle);
	}

	private void render()
	{
		final int size = renderListNew.size();

		for (int i = 0; i < size; i++)
		{
			FractalRing rc = renderListNew.get(i);

			double x = rc.centreX - width / 2;
			double y = rc.centreY - height / 2;
			gc.drawImage(image[rc.colourIndex], width / 2 + x * rotate.getMxx() + y * rotate.getMxy() - rc.radius, height / 2 + x * rotate.getMyx() + y * rotate.getMyy() - rc.radius, rc.radius * 2, rc.radius * 2);
		}
	}

	private int getSectorCount()
	{
		return 10 - (int) (precalc.getUnsignedRandom() * 3);
	}

	private void update(int index)
	{
		FractalRing rc = renderListOld.get(index);

		rc.grow(halfWidth, halfHeight);

		if (!rc.isOnScreen(width, height))
		{
			return;
		}

		renderListNew.add(rc);

		if (!rc.hasChildren && rc.radius > SPAWN_AT_RADIUS)
		{
			rc.hasChildren = true;

			double subRadius = rc.radius * 0.15;

			renderListNew.add(new FractalRing(rc.centreX, rc.centreY, subRadius, colourIndex));

			double distanceInner = (rc.radius - subRadius) * .9;

			for (int i = 0; i < 3; i++)
			{
				generateChildren(rc, getSectorCount(), distanceInner, subRadius);

				distanceInner *= 0.6;
				subRadius *= 0.65;
			}
		}
	}

	private void generateChildren(FractalRing parent, int sectors, double distance, double radius)
	{
		double sectorAngle = 360.0 / sectors;

		double startAngle = sectorAngle / 2;

		for (double angle = 0; angle < 360; angle += sectorAngle)
		{
			double subX = precalc.sin(startAngle + angle) * distance;
			double subY = precalc.cos(startAngle + angle) * distance;

			renderListNew.add(new FractalRing(parent.centreX + subX, parent.centreY + subY, radius, colourIndex));
		}
	}
}