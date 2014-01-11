package com.vteba.service.multitenant;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.hibernate.HibernateException;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.engine.config.spi.ConfigurationService;
import org.hibernate.engine.jdbc.connections.spi.AbstractMultiTenantConnectionProvider;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.service.spi.ServiceRegistryAwareService;
import org.hibernate.service.spi.ServiceRegistryImplementor;
import org.hibernate.service.spi.Stoppable;

import com.vteba.service.context.spring.ApplicationContextHolder;

/**
 * 多租户连接提供者实现，基于Spring配置和atomikos/btm jta datasource
 * @author yinlei 
 * date 2012-8-13 下午9:38:49
 */
public class MultiTenantConnectionProviderImpl extends
		AbstractMultiTenantConnectionProvider implements ServiceRegistryAwareService, Stoppable {

	private static final long serialVersionUID = 4943897792496421261L;

	private ConcurrentMap<String, ConnectionProvider> providerMap;
	private String tenantIdentifierForAny;
	
	@Override
	public void injectServices(ServiceRegistryImplementor serviceRegistry) {
		
		final Object providerConfigValue = serviceRegistry.getService(ConfigurationService.class)
				.getSettings().get(AvailableSettings.DATASOURCE);
		
		if (providerConfigValue == null || !String.class.isInstance(providerConfigValue)) {
			throw new HibernateException( "Improper set up of hibernate.connection.datasource property" );
		}
		
		String jndiBeanName = (String) providerConfigValue;
//		WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
//		Object object = context.getBean(jndiBeanName);
		Object namedObject = ApplicationContextHolder.getApplicationContext().getBean(jndiBeanName);
		if (ConnectionProvider.class.isInstance(namedObject)) {
			tenantIdentifierForAny = jndiBeanName;
			providerMap().put(tenantIdentifierForAny, (ConnectionProvider) namedObject);
		} else {
			throw new HibernateException("Unknown object [" + namedObject.getClass().getName() + "] found by spring bean id [" + jndiBeanName + "]");
		}
		
	}
	
	/**
	 * select any one of connection
	 */
	@Override
	protected ConnectionProvider getAnyConnectionProvider() {
		return selectConnectionProvider(tenantIdentifierForAny);
	}
	
	/**
	 * select connection by tenant ID
	 * @param tenantIdentifier tenant ID
	 */
	@Override
	protected ConnectionProvider selectConnectionProvider(
			String tenantIdentifier) {
		ConnectionProvider provider = providerMap().get(tenantIdentifier);
		if ( provider == null ) {
			throw new HibernateException(" No connection provider by tenant identifier" + tenantIdentifier);
		}
		return provider;
	}

	private Map<String,ConnectionProvider> providerMap() {
		if ( providerMap == null ) {
			providerMap = new ConcurrentHashMap<String, ConnectionProvider>();
		}
		return providerMap;
	}
	
	@Override
	public void stop() {
		if (providerMap != null) {
			providerMap.clear();
			providerMap = null;
		}
	}

}
