package com.vteba.common.imagick;

/**
 * ImageMagick的identify命令接口
 * 取得图片信息
 *
 */
public class MagickIdentifyImpl extends AbstractMagickCommand<MagickIdentifyImpl> {

	public MagickIdentifyImpl(){
	}

	public MagickIdentifyImpl verbose() {
		return option("-verbose");
	}

}
