package com.vteba.tx.bitronix.infinispan;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.transaction.xa.XAResource;

import org.infinispan.transaction.xa.TransactionXaAdapter;

import bitronix.tm.resource.common.AbstractXAResourceHolder;
import bitronix.tm.resource.common.ResourceBean;
import bitronix.tm.resource.common.XAResourceHolder;

/**
 * Infinispan XAResourceHolder
 * @author yinlei
 * date 2013-6-30 下午5:33:31
 */
public class InfinispanXAResourceHolder extends AbstractXAResourceHolder {
    private TransactionXaAdapter adapter;
    private ResourceBean resourceBean;
    
    public InfinispanXAResourceHolder(TransactionXaAdapter adapter, ResourceBean resourceBean) {
        this.adapter = adapter;
        this.resourceBean = resourceBean;
    }

    @Override
    public XAResource getXAResource() {
        return adapter;
    }

    @Override
    public List<XAResourceHolder> getXAResourceHolders() {
        return Collections.emptyList();
    }

    @Override
    public Object getConnectionHandle() throws Exception {
        throw new UnsupportedOperationException("unmanaged resource");
    }

    @Override
    public void close() throws Exception {
    }

    @Override
    public Date getLastReleaseDate() {
        return null;
    }

	@Override
	public ResourceBean getResourceBean() {
		return resourceBean;
	}
}
