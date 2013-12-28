package com.vteba.common.imagick;

/**
 * ImageMagick的mogrify命令接口
 * 该命令会覆盖源文件
 *
 */
public class MagickMogrifyImpl extends AbstractMagickCommand<MagickMogrifyImpl> {

	public MagickMogrifyImpl() {
	}

	public MagickMogrifyImpl resizeWidth(Integer value) {
		return resize(value + "x");
	}

	public MagickMogrifyImpl scaleWidth(Integer value) {
		return scale(value + "x");
	}

	public MagickMogrifyImpl resizeHeight(Integer value) {
		return resize("x" + value);
	}

	public MagickMogrifyImpl scaleHeight(Integer value) {
		return scale("x" + value);
	}

	public MagickMogrifyImpl resizePercent(Integer value) {
		return resize(value + "%");
	}

	public MagickMogrifyImpl scalePercent(Integer value) {
		return scale(value + "%");
	}

	public MagickMogrifyImpl resizeForce(Integer width, Integer height) {
		return resize(width + "x" + height + "!");
	}

	public MagickMogrifyImpl scaleForce(Integer width, Integer height) {
		return scale(width + "x" + height + "!");
	}

	public MagickMogrifyImpl resize(Integer width, Integer height) {
		return resize(width + "x" + height);
	}

	public MagickMogrifyImpl scale(Integer width, Integer height) {
		return scale(width + "x" + height);
	}

	public MagickMogrifyImpl format(String value) {
		return option("-format " + value);
	}

	public MagickMogrifyImpl geometry(String value) {
		return option("-geometry " + value);
	}

	public MagickMogrifyImpl resize(String value) {
		return option("-resize " + value);
	}

	public MagickMogrifyImpl scale(String value) {
		return option("-scale " + value);
	}

	public MagickMogrifyImpl size(String value) {
		return option("-size " + value);
	}

	public MagickMogrifyImpl depth(String value) {
		return option("-depth " + value);
	}

	public MagickMogrifyImpl density(String value) {
		return option("-density " + value);
	}

	public MagickMogrifyImpl colors(String value) {
		return option("-colors " + value);
	}

	public MagickMogrifyImpl blur(String value) {
		return option("-blur " + value);
	}

	public MagickMogrifyImpl contrast() {
		return option("-contrast");
	}

	public MagickMogrifyImpl equalize() {
		return option("-equalize");
	}

	public MagickMogrifyImpl monochrome() {
		return option("-monochrome");
	}

	public MagickMogrifyImpl edge(String value) {
		return option("-edge " + value);
	}

	public MagickMogrifyImpl emboss(String value) {
		return option("-emboss " + value);
	}

	public MagickMogrifyImpl shade(String value) {
		return option("-shade " + value);
	}

	public MagickMogrifyImpl transparent(String value) {
		return option("-transparent " + value);
	}

	public MagickMogrifyImpl crop(String value) {
		return option("-crop " + value);
	}

	public MagickMogrifyImpl rotate(String value) {
		return option("-rotate " + value);
	}

	public MagickMogrifyImpl spread(String value) {
		return option("-spread " + value);
	}

	public MagickMogrifyImpl swirl(String value) {
		return option("-swirl " + value);
	}

	public MagickMogrifyImpl wave(String value) {
		return option("-wave " + value);
	}

	public MagickMogrifyImpl border(String width, String color) {
		return option("-border " + width + " -bordercolor " + color);
	}

	public MagickMogrifyImpl raise(String value) {
		return option("-raise " + value);
	}

	public MagickMogrifyImpl frame(String value, String color) {
		return option("-frame " + value + " -mattecolor " + color);
	}

	public MagickMogrifyImpl mosaic() {
		return option("-sample 10% -sample 1000%");
	}

	public MagickMogrifyImpl background(String value) {
		return option("-background " + value);
	}

	public MagickMogrifyImpl font(String path) {
		return option("-font " + path);
	}

	public MagickMogrifyImpl fill(String value) {
		return option("-fill " + value);
	}

	public MagickMogrifyImpl pointsize(String size) {
		return option("-pointsize " + size);
	}

}
