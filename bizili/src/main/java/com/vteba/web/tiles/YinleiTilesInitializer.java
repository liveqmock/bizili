package com.vteba.web.tiles;

import org.apache.tiles.TilesApplicationContext;
import org.apache.tiles.factory.AbstractTilesContainerFactory;
import org.apache.tiles.startup.AbstractTilesInitializer;

/**
 * tiles容器初始化器
 * @author yinlei 2011-11-30
 *
 */
public class YinleiTilesInitializer extends AbstractTilesInitializer {
	/**
	 * 容器工厂的key，记录哪个容器工厂被存储
	 */
	public static final String CONTAINER_KEY_INIT_PARAMETER = "com.skmbw.util.tiles.YinleiTilesInitializer.CONTAINER_KEY";

	/**
	 * 使用BasicTilesContainerFactory创建容器工厂
	 */
	@Override
	protected AbstractTilesContainerFactory createContainerFactory(
			TilesApplicationContext context) {
		//return new BasicTilesContainerFactory();
		return new YinleiTilesContainerFactory();
	}
	/**
	 * 获得容器工厂key 2011-11-30
	 */
    protected String getContainerKey(TilesApplicationContext applicationContext) {
        String key = applicationContext.getInitParams().get(CONTAINER_KEY_INIT_PARAMETER);
        return key;
    }
    /**
     * 创建TilesApplicationContext 2011-11-30
     */
    protected TilesApplicationContext createTilesApplicationContext(
            TilesApplicationContext preliminaryContext) {
    	//ServletContext servletContext =(ServletContext)preliminaryContext.getContext();
    	//ServletTilesApplicationContext applicationContext = new ServletTilesApplicationContext(servletContext);
    	//return applicationContext;//没有必要重新创建
    	return preliminaryContext;//直接返回亦可，因为传进来的就是ServletTilesApplicationContext
    }
}
