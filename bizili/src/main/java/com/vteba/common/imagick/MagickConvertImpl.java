package com.vteba.common.imagick;

/**
 * ImageMagick的convert/composite命令接口
 * 该命令会创建一个新的文件，不会覆盖源文件
 *
 */
public class MagickConvertImpl extends AbstractMagickCommand<MagickConvertImpl> {
	
	public MagickConvertImpl() {
	}

	public MagickConvertImpl resizeWidth(Integer value) {
		return resize(value + "x");
	}

	public MagickConvertImpl scaleWidth(Integer value) {
		return scale(value + "x");
	}

	public MagickConvertImpl resizeHeight(Integer value) {
		return resize("x" + value);
	}

	public MagickConvertImpl scaleHeight(Integer value) {
		return scale("x" + value);
	}

	public MagickConvertImpl resizePercent(Integer value) {
		return resize(value + "%");
	}

	public MagickConvertImpl scalePercent(Integer value) {
		return scale(value + "%");
	}

	public MagickConvertImpl resizeForce(Integer width, Integer height) {
		return resize(width + "x" + height + "!");
	}

	public MagickConvertImpl scaleForce(Integer width, Integer height) {
		return scale(width + "x" + height + "!");
	}

	public MagickConvertImpl resize(Integer width, Integer height) {
		return resize(width + "x" + height);
	}

	public MagickConvertImpl scale(Integer width, Integer height) {
		return scale(width + "x" + height);
	}

	public MagickConvertImpl geometry(String value) {
		return option("-geometry " + value);
	}

	public MagickConvertImpl resize(String value) {
		return option("-resize " + value);
	}

	public MagickConvertImpl scale(String value) {
		return option("-scale " + value);
	}

	public MagickConvertImpl size(String value) {
		return option("-size " + value);
	}

	public MagickConvertImpl depth(String value) {
		return option("-depth " + value);
	}

	public MagickConvertImpl density(String value) {
		return option("-density " + value);
	}

	public MagickConvertImpl colors(String value) {
		return option("-colors " + value);
	}

	public MagickConvertImpl blur(String value) {
		return option("-blur " + value);
	}

	public MagickConvertImpl contrast() {
		return option("-contrast");
	}

	public MagickConvertImpl equalize() {
		return option("-equalize");
	}

	public MagickConvertImpl monochrome() {
		return option("-monochrome");
	}

	public MagickConvertImpl edge(String value) {
		return option("-edge " + value);
	}

	public MagickConvertImpl emboss(String value) {
		return option("-emboss " + value);
	}

	public MagickConvertImpl shade(String value) {
		return option("-shade " + value);
	}

	public MagickConvertImpl transparent(String value) {
		return option("-transparent " + value);
	}

	public MagickConvertImpl crop(String value) {
		return option("-crop " + value);
	}

	public MagickConvertImpl rotate(String value) {
		return option("-rotate " + value);
	}

	public MagickConvertImpl spread(String value) {
		return option("-spread " + value);
	}

	public MagickConvertImpl swirl(String value) {
		return option("-swirl " + value);
	}

	public MagickConvertImpl wave(String value) {
		return option("-wave " + value);
	}

	public MagickConvertImpl border(String width, String color) {
		return option("-border " + width + " -bordercolor " + color);
	}

	public MagickConvertImpl raise(String value) {
		return option("-raise " + value);
	}

	public MagickConvertImpl frame(String value, String color) {
		return option("-frame " + value + " -mattecolor " + color);
	}

	public MagickConvertImpl mosaic() {
		return option("-sample 10% -sample 1000%");
	}

	public MagickConvertImpl background(String value) {
		return option("-background " + value);
	}

	public MagickConvertImpl font(String path) {
		return option("-font " + path);
	}

	public MagickConvertImpl fill(String value) {
		return option("-fill " + value);
	}

	public MagickConvertImpl pointsize(String size) {
		return option("-pointsize " + size);
	}

	public MagickConvertImpl dissolve(String dissolve) {
		return option("-dissolve " + dissolve);
	}

	public MagickConvertImpl gravity(String gravity) {
		return option("-gravity " + gravity);
	}
}
