package com.vteba.common.imagick;

import java.util.ArrayList;

/**
 * ImageMagick执行结果
 *
 */
public class MagickResult {

	private static final int DEFAULT = -9999;

	private int exitValue;

	private ArrayList<String> stdOut = new ArrayList<String>();

	private ArrayList<String> stdErr = new ArrayList<String>();

	public MagickResult() {
		exitValue = DEFAULT;
	}

	public boolean isOk() {
		return (exitValue == 0);
	}

	public int getExitValue() {
		return exitValue;
	}

	public void setExitValue(int code) {
		exitValue = code;
	}

	public ArrayList<String> getStdOut() {
		return stdOut;
	}

	public ArrayList<String> getStdErr() {
		return stdErr;
	}

}
