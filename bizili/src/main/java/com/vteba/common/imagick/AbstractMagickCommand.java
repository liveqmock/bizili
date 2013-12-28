package com.vteba.common.imagick;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.vteba.common.exception.BaseException;

/**
 * ImageMagick命令基类
 * 用来执行Runtime.exec()
 * @author yinlei
 */
public abstract class AbstractMagickCommand<T> {

	private static final Logger log = Logger.getLogger(AbstractMagickCommand.class);

	private static final Pattern PATTERN_ESCAPE = Pattern.compile("[#&;`|?~^\\(\\)\\[\\]\\{\\}$\\\\,\\p{Cntrl}]");

	protected String command;
	
	protected List<String> commandLines = new ArrayList<String>();

	protected String defaultOptions;

	private boolean ignoreDefaultOptions = false;

	private ArrayList<String> args = new ArrayList<String>();

	private ArrayList<String> options = new ArrayList<String>();

	public void setCommand(String command) {
		
		if(!StringUtils.isEmpty(this.command)){
			commandLines.add(getCommandLine());
			args.clear();
			options.clear();
		}
		
		this.command = AbstractMagickCommand.esc(command);
	}

	public void setDefaultOptions(String options) {
		this.defaultOptions = AbstractMagickCommand.esc(options);
	}

	public String getCommandLine() {
		StringBuilder buf = new StringBuilder(command);
		for(String param : options) {
			buf.append(" ").append(param);
		}
		if (!ignoreDefaultOptions && defaultOptions != null) {
			buf.append(" ").append(defaultOptions);
		}
		for(String param : args) {
			buf.append(" ").append(param);
		}
		return buf.toString();
	}

	@SuppressWarnings("unchecked")
	public T arg(String arg) {
		args.add(AbstractMagickCommand.esc(arg));
		return (T)this;
	}

	@SuppressWarnings("unchecked")
	public T option(String option) {
		options.add(AbstractMagickCommand.esc(option));
		return (T)this;
	}

	@SuppressWarnings("unchecked")
	public T ignoreDefaultOptions() {
		this.ignoreDefaultOptions = true;
		return (T)this;
	}

	public boolean execute() {
		return execute(null);
	}

	public boolean execute(MagickResult result) {
		Process process;
		if(result == null){
			result = new MagickResult();
		}
		try {
			commandLines.add(getCommandLine());

			for(String commandLine : commandLines){
				
				log.info("[ImageMagick]执行命令为:[" + command + "]");
				process = Runtime.getRuntime().exec(commandLine);
				AbstractMagickCommand.getCommandMessage(result.getStdOut(),process.getInputStream());
				AbstractMagickCommand.getCommandMessage(result.getStdErr(),process.getErrorStream());

				process.waitFor();
				process.destroy();

				result.setExitValue(process.exitValue());
				if (!result.isOk()) {
					log.warn("[ImageMagick]命令执行时发生错误" );
					for(String line : result.getStdErr()){
						log.warn("[ImageMagick]命令错误:[" + line + "]");
					}
					
					return false;
				}
			}

		} catch (IOException ioe) {
			log.warn(ioe.getMessage(), ioe);
			return false;
		} catch (InterruptedException ex) {
			;
		}
		return result.isOk();
	}

	private static String esc(String val) {
		if ( PATTERN_ESCAPE.matcher(val).find() ) {
			throw new BaseException("[ImageMagick]命令行包含了错误字符 [" + val + "]");
		}
		return val;
	}

	private static void getCommandMessage(ArrayList<String> buf, InputStream is) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line;
		try {
			while ((line = br.readLine()) != null) {
				buf.add(line);
			}
		} finally {
			try {
				br.close();
			} catch (IOException e) {
			}
		}
	}

}
