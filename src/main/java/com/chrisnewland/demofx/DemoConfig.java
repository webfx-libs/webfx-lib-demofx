/*
 * Copyright (c) 2015-2016 Chris Newland.
 * Licensed under https://github.com/chriswhocodes/demofx/blob/master/LICENSE-BSD
 */
package com.chrisnewland.demofx;

import com.chrisnewland.demofx.util.PreCalc;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;

public class DemoConfig
{
	public enum PlotMode
	{
		PLOT_MODE_LINE, PLOT_MODE_POLYGON, PLOT_MODE_FILL_POLYGON
	}

	private String effect = "stars";
	private String audioFileName = null; 
	private int count = -1;
	private double width = 800;
	private double height = 600;
	
	private int runForSeconds = -1;

	private boolean fullScreen = false;
	private boolean lookupSqrt = false;
	private boolean lookupTrig = true;
	private boolean lookupRandom = true;

	private PlotMode plotMode = PlotMode.PLOT_MODE_FILL_POLYGON;

	private String demoScriptName = null;
	
	private Group groupNode;
	private GraphicsContext onScreenCanvasGC;
	private GraphicsContext offScreenCanvasGC;
	
	private boolean offScreen = false;
	private double offScreenWidth;
	private double offScreenHeight;
	
	private PreCalc precalc;

	private DemoConfig()
	{
	}

	/*public static String getUsageError()
	{
		StringBuilder builder = new StringBuilder();

		List<String> effects = SimpleEffectFactory.getAvailableEffectNames();

		Collections.sort(effects);

		builder.append("DemoFXApplication [options]").append("\n");

		boolean first = true;

		for (String effectName : effects)
		{
			builder.append(buildUsageLine(first ? "-e <effects>" : "", effectName));

			if (first)
			{
				first = false;
			}
		}

		builder.append("\n");

		builder.append(buildUsageLine("-t <seconds>", "run for t seconds"));
		builder.append(buildUsageLine("-c <count>", "number of items on screen"));
		builder.append(buildUsageLine("-f <true>", "fullscreen mode (no stats pane)"));
		builder.append(buildUsageLine("-w <width>", "canvas width"));
		builder.append(buildUsageLine("-h <height>", "canvas height"));
		builder.append(buildUsageLine("-l [sqrt,trig,rand,none]", "use lookup tables for Math.sqrt, Math.{sin|cos}, Math.Random"));
		builder.append(buildUsageLine("-m <line|poly|fill>", "canvas plot mode"));
		builder.append(buildUsageLine("-a <audio filename>", "Play audio file"));
		builder.append(buildUsageLine("-s <true>", "use ScriptedDemoConfig"));

		return builder.toString();
	}

	private static String buildUsageLine(String left, String right)
	{
		int leftWidth = 27;

		StringBuilder builder = new StringBuilder();

		builder.append(left);

		for (int i = 0; i < leftWidth - left.length(); i++)
		{
			builder.append(" ");
		}

		builder.append(right);

		builder.append("\n");

		return builder.toString();
	}*/

	public void setLayers(GraphicsContext onscreenGC, GraphicsContext offscreenGC, Group foreground) 
	{
		this.onScreenCanvasGC = onscreenGC;
		
		this.offScreenCanvasGC = offscreenGC;

		this.groupNode = foreground;
	}
	
	public static DemoConfig buildConfig(String... args)
	{
		DemoConfig config = new DemoConfig();
		
		boolean argError = false;

		int argc = args.length;

		for (int i = 0; i < argc; i += 2)
		{
			String arg = args[i];

			boolean lastArg = (i == argc - 1);

			if (arg.startsWith("-") && arg.length() == 2 && !lastArg)
			{
				String value = args[i + 1];

				try
				{
					switch (arg.substring(1))
					{
					// =======================================
					case "e":
						config.effect = value;
						break;
					// =======================================
					case "a":
						config.audioFileName = value;
						break;
					// =======================================
					case "c":
						config.count = Integer.parseInt(value);
						break;
					// =======================================
					case "w":
						config.width = Double.parseDouble(value);
						break;
					// =======================================
					case "h":
						config.height = Double.parseDouble(value);
						break;
					// =======================================
					case "t":
						config.runForSeconds = Integer.parseInt(value);
						break;
					// =======================================
					case "l":
						checkLookupOptions(config, value);
						break;
					// =======================================
					case "m":
						if ("line".equals(value.toLowerCase()))
						{
							config.plotMode = PlotMode.PLOT_MODE_LINE;
						}
						else if ("poly".equals(value.toLowerCase()))
						{
							config.plotMode = PlotMode.PLOT_MODE_POLYGON;
						}
						else if ("fill".equals(value.toLowerCase()))
						{
							config.plotMode = PlotMode.PLOT_MODE_FILL_POLYGON;
						}
						else
						{
							argError = true;
						}
						break;
					// =======================================
					case "s":
							config.demoScriptName = value.toLowerCase();
							config.effect = "script mode";
						break;
					case "f":
						if ("true".equals(value.toLowerCase()))
						{
							config.fullScreen = true;
						}
						break;
					// =======================================
					default:
						argError = true;
						break;
					}
					// =======================================
				}
				catch (Exception e)
				{
					argError = true;
					break;
				}
			}
			else
			{
				argError = true;
			}
		}

		if (argError)
		{
			config = null;
		}
		
		if (config != null)
		{
			config.precalc = new PreCalc(config);
		}

		return config;
	}
	
	public PreCalc getPreCalc()
	{
		return precalc;
	}

	private static void checkLookupOptions(DemoConfig config, String value)
	{
		if (value.toLowerCase().contains("none"))
		{
			config.lookupSqrt = false;
			config.lookupTrig = false;
			config.lookupRandom = false;
		}

		if (value.toLowerCase().contains("sqrt"))
		{
			config.lookupSqrt = true;
		}

		if (value.toLowerCase().contains("trig"))
		{
			config.lookupTrig = true;
		}

		if (value.toLowerCase().contains("rand"))
		{
			config.lookupRandom = true;
		}
	}

	public String getEffect()
	{
		return effect.toLowerCase();
	}
	
	public void setAudioFilename(String filename)
	{
		this.audioFileName = filename;
	}
	
	public String getAudioFilename()
	{
		return audioFileName;
	}

	public int getCount()
	{
		return count;
	}

	public double getWidth()
	{
		return width;
	}

	public double getHeight()
	{
		return height;
	}

	public PlotMode getPlotMode()
	{
		return plotMode;
	}

	public boolean isLookupSqrt()
	{
		return lookupSqrt;
	}

	public boolean isLookupTrig()
	{
		return lookupTrig;
	}

	public boolean isLookupRandom()
	{
		return lookupRandom;
	}

	public String getDemoScriptName()
	{
		return demoScriptName;
	}
	
	public boolean isFullScreen()
	{
		return fullScreen;
	}
	
	public void setItemCount(int count)
	{
		this.count = count;
	}

	public Group getGroupNode()
	{
		return groupNode;
	}

	public GraphicsContext getOnScreenCanvasGC()
	{
		return onScreenCanvasGC;
	}
	
	public GraphicsContext getOffScreenCanvasGC()
	{
		return offScreenCanvasGC;
	}
	
	public boolean isOffScreen()
	{
		return offScreen;
	}
	
	public void clearOffScreen()
	{
		offScreen = false;
		offScreenWidth = -1;
		offScreenHeight = -1;
	}
	
	public void setOffScreen(double newWidth, double newHeight)
	{
		offScreen = true;
		offScreenWidth = newWidth;
		offScreenHeight = newHeight;
	}
	
	public double getOffScreenWidth()
	{
		return offScreenWidth;
	}
	
	public double getOffScreenHeight()
	{
		return offScreenHeight;
	}
	
	public int getRunForSeconds()
	{
		return runForSeconds;
	}
}