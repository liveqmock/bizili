package com.vteba.web.tiles;

import javax.servlet.ServletContext;

import org.apache.tiles.TilesApplicationContext;
import org.apache.tiles.TilesContainer;
import org.apache.tiles.factory.AbstractTilesContainerFactory;
import org.apache.tiles.servlet.context.ServletTilesApplicationContext;
import org.apache.tiles.startup.TilesInitializer;
import org.apache.tiles.web.startup.AbstractTilesListener;

/**
 * tiles容器监听器 2011-11-30
 * @author yinlei
 *
 */
public class YinleiTilesListener extends AbstractTilesListener{
	
	public YinleiTilesListener(){}
	
	/**
	 * 基于servlet的tiles初始化器
	 */
	@Override
	protected TilesInitializer createTilesInitializer() {
		return new YinleiTilesInitializer();
	}
	
	/**
	 * 使用BasicTilesContainerFactory创建tiles容器
	 * @param context
	 * @return
	 */
	protected TilesContainer createContainer(ServletContext context) {
		TilesApplicationContext applicationContext = new ServletTilesApplicationContext(context);
		//AbstractTilesContainerFactory factory = new BasicTilesContainerFactory();
		AbstractTilesContainerFactory factory = new YinleiTilesContainerFactory();
		return factory.createContainer(applicationContext);
    }

}
