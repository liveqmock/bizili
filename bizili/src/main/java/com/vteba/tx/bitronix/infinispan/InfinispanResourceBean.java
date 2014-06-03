package com.vteba.tx.bitronix.infinispan;

import bitronix.tm.resource.common.ResourceBean;

import java.util.Properties;

/**
 * 代表Infinispan XAResource
 * @author yinlei
 * date 2013-6-30 下午5:33:22
 */
public class InfinispanResourceBean extends ResourceBean {
	private static final long serialVersionUID = -5229870695843506776L;

	public InfinispanResourceBean() {
    }

    @Override
    public void setClassName(String className) {
        throw new UnsupportedOperationException("read-only property");
    }

    @Override
    public void setAutomaticEnlistingEnabled(boolean automaticEnlistingEnabled) {
        throw new UnsupportedOperationException("read-only property");
    }

    @Override
    public void setDriverProperties(Properties driverProperties) {
        throw new UnsupportedOperationException("read-only property");
    }

    @Override
    public void setMaxPoolSize(int maxPoolSize) {
        throw new UnsupportedOperationException("read-only property");
    }

    @Override
    public void setMinPoolSize(int minPoolSize) {
        throw new UnsupportedOperationException("read-only property");
    }

    @Override
    public void setMaxIdleTime(int maxIdleTime) {
        throw new UnsupportedOperationException("read-only property");
    }

    @Override
    public void setAcquireIncrement(int acquireIncrement) {
        throw new UnsupportedOperationException("read-only property");
    }

    @Override
    public void setAcquisitionTimeout(int acquisitionTimeout) {
        throw new UnsupportedOperationException("read-only property");
    }

    @Override
    public void setAcquisitionInterval(int acquisitionInterval) {
        throw new UnsupportedOperationException("read-only property");
    }
}
