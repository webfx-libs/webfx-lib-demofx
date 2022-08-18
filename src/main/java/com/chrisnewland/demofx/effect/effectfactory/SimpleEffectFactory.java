/*
 * Copyright (c) 2015-2016 Chris Newland.
 * Licensed under https://github.com/chriswhocodes/demofx/blob/master/LICENSE-BSD
 */
package com.chrisnewland.demofx.effect.effectfactory;

import com.chrisnewland.demofx.DemoConfig;
import com.chrisnewland.demofx.effect.IEffect;
//import com.chrisnewland.demofx.effect.background.BinaryBackground;
//import com.chrisnewland.demofx.effect.background.ColourBackground;
//import com.chrisnewland.demofx.effect.background.CycleBackground;
//import com.chrisnewland.demofx.effect.background.ImageBackground;
import com.chrisnewland.demofx.effect.fake3d.*;
//import com.chrisnewland.demofx.effect.fractal.ChristmasTrees;
//import com.chrisnewland.demofx.effect.fractal.FractalRings;
//import com.chrisnewland.demofx.effect.fractal.Mandelbrot;
//import com.chrisnewland.demofx.effect.fractal.Sierpinski;
//import com.chrisnewland.demofx.effect.pixel.*;
//import com.chrisnewland.demofx.effect.ray.RayTrace;
//import com.chrisnewland.demofx.effect.real3d.*;
//import com.chrisnewland.demofx.effect.shape.*;
//import com.chrisnewland.demofx.effect.spectral.Equaliser2D;
//import com.chrisnewland.demofx.effect.spectral.Equaliser3D;
//import com.chrisnewland.demofx.effect.spectral.Feedback;
//import com.chrisnewland.demofx.effect.spectral.VUMeter;
//import com.chrisnewland.demofx.effect.sprite.*;
//import com.chrisnewland.demofx.effect.text.*;
//import com.chrisnewland.demofx.effect.video.*;
//import com.chrisnewland.demofx.util.ShapeEffect;

import java.util.ArrayList;
import java.util.List;

public class SimpleEffectFactory implements IEffectFactory
{
	@Override
	public List<IEffect> getEffects(DemoConfig config)
	{
		List<IEffect> result = new ArrayList<>();

		String effectParam = config.getEffect();

		String[] parts = effectParam.split(",");

		for (String part : parts)
		{
			result.add(getEffect(part, config));
		}

		return result;
	}

	/*private static List<String> availableEffectNames;

	static
	{
		availableEffectNames = new ArrayList<>();

		availableEffectNames.add("binarybackground");
		availableEffectNames.add("blur");
		availableEffectNames.add("bobs");
		availableEffectNames.add("bounce");
		availableEffectNames.add("burst");
		availableEffectNames.add("checkerboard");
		availableEffectNames.add("chord");
		availableEffectNames.add("christmastrees");
		availableEffectNames.add("chromakey");
		availableEffectNames.add("cogs");
		availableEffectNames.add("colourbackground");
		availableEffectNames.add("concentric");
		availableEffectNames.add("credits");
		availableEffectNames.add("creditssprite");
		availableEffectNames.add("cubefield");
		availableEffectNames.add("cyclebackground");
		availableEffectNames.add("diamonds");
		availableEffectNames.add("doomfire");
		availableEffectNames.add("equaliser");
		availableEffectNames.add("equalisercubes");
		availableEffectNames.add("falling");
		availableEffectNames.add("feedback");
		availableEffectNames.add("fireworks");
		availableEffectNames.add("flash");
		availableEffectNames.add("gc");
		availableEffectNames.add("glowboard");
		availableEffectNames.add("grid");
		availableEffectNames.add("hexagons");
		availableEffectNames.add("honeycomb");
		availableEffectNames.add("hue");
		availableEffectNames.add("imagebackground");
		availableEffectNames.add("inversechromakey");
		availableEffectNames.add("mandala");
		availableEffectNames.add("mandelbrot");
		availableEffectNames.add("mask");
		availableEffectNames.add("maskstack");
		availableEffectNames.add("mesh");
		availableEffectNames.add("mirrorx");
		availableEffectNames.add("mirrory");
		availableEffectNames.add("moire");
		availableEffectNames.add("moremoire");
		availableEffectNames.add("pentagons");
		availableEffectNames.add("picinpic");
		availableEffectNames.add("quadplay");
		availableEffectNames.add("rain");
		availableEffectNames.add("rainbow");
		availableEffectNames.add("rawplayer");
		availableEffectNames.add("raytrace");
		availableEffectNames.add("rings");
		availableEffectNames.add("rotations");
		availableEffectNames.add("sea");
		availableEffectNames.add("sheet");
		availableEffectNames.add("shift");
		availableEffectNames.add("sierpinski");
		availableEffectNames.add("sinelines");
		availableEffectNames.add("spin");
		availableEffectNames.add("spritewave");
		availableEffectNames.add("sprite3d");
		availableEffectNames.add("squares");
		availableEffectNames.add("stars");
		availableEffectNames.add("starfield");
		availableEffectNames.add("snowfieldsprite");
		availableEffectNames.add("starfieldsprite");
		availableEffectNames.add("textbounce");
		availableEffectNames.add("texcube");
		availableEffectNames.add("texsphere");
		availableEffectNames.add("textwave");
		availableEffectNames.add("textwavesprite");
		availableEffectNames.add("textlabel");
		availableEffectNames.add("textlayers");
		availableEffectNames.add("textring");
		availableEffectNames.add("tiles");
		availableEffectNames.add("tunnel");
		availableEffectNames.add("triangles");
		availableEffectNames.add("tubestack");
		availableEffectNames.add("twister");
		availableEffectNames.add("twistersprite");
		availableEffectNames.add("typetext");
		availableEffectNames.add("vumeter");
		availableEffectNames.add("wordsearch");
	}
	
	public static List<String> getAvailableEffectNames()
	{
		return availableEffectNames;
	}*/

	private IEffect getEffect(String name, DemoConfig config)
	{
		switch (name)
		{
		case "animtexsphere":
			// return new AnimatedTexturedSphere(config);
			throw new UnsupportedOperationException(
					"AnimatedTexturedSphere can't be run solo, must be combined with an effect that generates the texture");

		case "animtexcube":
			// return new AnimatedTexturedCube(config);
			throw new UnsupportedOperationException(
					"AnimatedTexturedCube can't be run solo, must be combined with an effect that generates the texture");

		/*case "binarybackground":
			return new BinaryBackground(config);

		case "blur":
			return new Blur(config);

		case "bobs":
			return new Bobs(config);

		case "bounce":
			return new Bounce(config);

		case "burst":
			return new Burst(config);

		case "checkerboard":
			return new Checkerboard(config);

		case "chord":
			return new Chord(config);

		case "christmastrees":
			return new ChristmasTrees(config);

		case "chromakey":
			return new ChromaKey(config);

		case "cogs":
			return new Cogs(config);

		case "colourbackground":
			return new ColourBackground(config);

		case "concentric":
			return new Concentric(config);

		case "credits":
			return new Credits(config);

		case "creditssprite":
			return new CreditsSprite(config);

		case "cubefield":
			return new CubeField(config);

		case "cyclebackground":
			return new CycleBackground(config);

		case "diamonds":
			return new Diamonds(config);

		case "doomfire":
			return new DoomFire(config);

		case "equaliser":
			return new Equaliser2D(config);

		case "equalisercubes":
			return new Equaliser3D(config);

		case "falling":
			return new Falling(config);

		case "feedback":
			return new Feedback(config);

		case "fireworks":
			return new Fireworks(config);

		case "flash":
			return new TextFlash(config);

		case "fractalrings":
			return new FractalRings(config);

		case "gc":
			return new GCVisualiser(config);

		case "glowboard":
			return new Glowboard(config);

		case "grid":
			return new Grid(config);

		case "hexagons":
			return new ShapeEffect(config, 6);

		case "honeycomb":
			return new Honeycomb(config);

		case "hue":
			return new Hue(config);

		case "imagebackground":
			return new ImageBackground(config);

		case "inversechromakey":
			return new InverseChromaKey(config);

		case "mandala":
			return new Mandala(config);

		case "mandelbrot":
			return new Mandelbrot(config);

		case "mask":
			return new Mask(config);

		case "maskstack":
			return new MaskStack(config);

		case "mesh":
			return new Mesh(config);

		case "mirrorx":
			return new MirrorX(config);

		case "mirrory":
			return new MirrorY(config);

		case "moire":
			return new com.chrisnewland.demofx.effect.sprite.Moire(config);

		case "moremoire":
			return new MoreMoire(config);

		case "pentagons":
			return new ShapeEffect(config, 5);

		case "picinpic":
			return new PictureInPicture(config);

		case "quadplay":
			return new QuadPlay(config);

		case "rain":
			return new Rain(config);

		case "rainbow":
			return new Rainbow(config);

		case "rawplayer":
			return new RawPlayer(config);

		case "raytrace":
			return new RayTrace(config);

		case "rings":
			return new com.chrisnewland.demofx.effect.shape.Rings(config);

		case "rotations":
			return new Rotations(config);

		case "sea":
			return new Sea(config);

		case "sheet":
			return new Sheet(config);

		case "shift":
			return new Shift(config);

		case "sierpinski":
			return new Sierpinski(config);

		case "sinelines":
			return new SineLines(config);
        */
		case "snowfieldsprite":
			return new SnowfieldSprite(config);
        /*
		case "spin":
			return new Spin(config);

		case "spritewave":
			return new SpriteWave(config);

		case "sprite3d":
			return new Sprite3D(config);

		case "squares":
			return new ShapeEffect(config, 4);

		case "stars":
			ShapeEffect stars = new ShapeEffect(config, 5);
			stars.setDoubleAngle(true);
			return stars;

		case "starfield":
			return new Starfield(config);

		case "starfieldsprite":
			return new StarfieldSprite(config);

		case "textbounce":
			return new TextBounce(config);

		case "texcube":
			return new TexturedCube(config);

		case "texsphere":
			return new TexturedSphere(config);

		case "textwave":
			return new TextWave(config);

		case "textwavesprite":
			return new TextWaveSprite(config);

		case "textlabel":
			return new TextLabel(config);

		case "textlayers":
			return new TextLayers(config);

		case "textring":
			return new TextRing(config);

		case "tiles":
			return new Tiles(config);

		case "tunnel":
			return new Tunnel(config);

		case "triangles":
			return new ShapeEffect(config, 3);

		case "tubestack":
			return new TubeStack(config);

		case "twister":
			return new Twister(config);
			
		case "twistersprite":
			return new TwisterSprite(config);

		case "typetext":
			return new TypeText(config);

		case "vumeter":
			return new VUMeter(config);

		case "wordsearch":
			return new WordSearch(config);*/

		default:
			throw new UnsupportedOperationException("No such effect: " + config.getEffect());
		}
	}
}
