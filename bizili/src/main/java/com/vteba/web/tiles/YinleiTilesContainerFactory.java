package com.vteba.web.tiles;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.tiles.TilesApplicationContext;
import org.apache.tiles.context.TilesRequestContextFactory;
import org.apache.tiles.factory.BasicTilesContainerFactory;
import org.apache.tiles.factory.TilesContainerFactoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自己实现tiles容器工厂，加载多个tiles定义文件。
 * @author yinlei
 * date 2013-5-4 下午7:03:54
 */
public class YinleiTilesContainerFactory extends BasicTilesContainerFactory{
	
	private static Logger logger = LoggerFactory.getLogger(YinleiTilesContainerFactory.class);
	public static final String TILES_DEFINITION_LOCATION = "tilesDefinitionLocation";
	public static final String WEB_INF = "WEB-INF";
	public static final String FILE_PREFIX = "/WEB-INF/";
	public static final String DEFAULT_DEFINITION_FILE = FILE_PREFIX + "tiles.xml";
	
	@Override
	protected List<URL> getSourceURLs(TilesApplicationContext applicationContext, TilesRequestContextFactory contextFactory){
		List<URL> list = new ArrayList<URL>();
		try {
			URL defaultUrl = applicationContext.getResource(DEFAULT_DEFINITION_FILE);
			if (defaultUrl == null) {
				throw new IOException("File " + DEFAULT_DEFINITION_FILE + ", is not exist.");
			}
			list.add(defaultUrl);
		} catch (IOException e) {
			logger.error("cannot find tiles definition file " + DEFAULT_DEFINITION_FILE);
			throw new TilesContainerFactoryException("Cannot get URL: /WEB-INF/tiles.xml", e);
		}
		
		try {
			String tilesPath = applicationContext.getInitParams().get(TILES_DEFINITION_LOCATION);
			if (tilesPath != null && !tilesPath.trim().equals("")) {
				String[] tilesPaths = StringUtils.split(tilesPath, ",");
				for (String path : tilesPaths) {
					if (path.indexOf(WEB_INF) < 0) {
						path = FILE_PREFIX + path;
					}
					URL tilesUrl = applicationContext.getResource(path);
					if (tilesUrl == null) {
						throw new IOException("cannot find tiles definition file : " + path);
					}
					list.add(tilesUrl);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new TilesContainerFactoryException(e.getMessage(), e);
		}
		return list;
	}
}
