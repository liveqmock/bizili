package com.vteba.tx.bitronix.datasource;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.XADataSource;

import org.hibernate.engine.jdbc.connections.spi.AbstractMultiTenantConnectionProvider;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.hibernate.service.UnknownUnwrapTypeException;
//import org.hibernate.service.jdbc.connections.spi.AbstractMultiTenantConnectionProvider;
//import org.hibernate.service.jdbc.connections.spi.ConnectionProvider;
//import org.hibernate.service.jdbc.connections.spi.MultiTenantConnectionProvider;

import bitronix.tm.resource.jdbc.PoolingDataSource;

/**
 * btm池化连接，实现hibernate多租户ConnectionProvider。
 * <br>可以不使用该包中的其他类。直接继承bitronix.tm.resource.jdbc.PoolingDataSource即可。
 * <br>如果不使用hibernate的多租户，直接使用bitronix.tm.resource.jdbc.PoolingDataSource即可。
 * @author yinlei
 * date 2012-9-20 下午8:25:18
 */
public class BtmPoolingDataSource extends PoolingDataSource implements ConnectionProvider {

	private static final long serialVersionUID = -4476820934735174539L;

	@SuppressWarnings("unchecked")
	@Override
	public <T> T unwrap(Class<T> face) {
		if (isWrapperFor(face) || isUnwrappableAs(face)) {
			return (T) this;
		} else {
			throw new UnknownUnwrapTypeException(face);
		}
	}
	
	public boolean isWrapperFor(Class<?> iface) {
        return iface.isAssignableFrom(XADataSource.class);
    }
	
	@Override
	public boolean isUnwrappableAs(@SuppressWarnings("rawtypes") Class unwrapType) {
		return MultiTenantConnectionProvider.class.equals(unwrapType)
				|| AbstractMultiTenantConnectionProvider.class.isAssignableFrom(unwrapType);
	}
	
	@Override
	public void closeConnection(Connection connection) throws SQLException {
		connection.close();
	}

	@Override
	public boolean supportsAggressiveRelease() {
		return true;
	}

    
}
