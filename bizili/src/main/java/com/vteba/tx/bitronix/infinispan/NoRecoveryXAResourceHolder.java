package com.vteba.tx.bitronix.infinispan;

import bitronix.tm.resource.common.AbstractXAResourceHolder;
import bitronix.tm.resource.common.ResourceBean;
import bitronix.tm.resource.common.XAResourceHolder;

import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 不可恢复XAResourceHolder，空实现。
 * @author yinlei
 * date 2013-6-30 下午5:33:48
 */
public class NoRecoveryXAResourceHolder extends AbstractXAResourceHolder {
	private ResourceBean resourceBean;
	
    public NoRecoveryXAResourceHolder(ResourceBean resourceBean) {
    	this.resourceBean = resourceBean;
    }


    @Override
    public XAResource getXAResource() {
        return new XAResource() {

            @Override
            public void commit(Xid xid, boolean b) throws XAException {
            }

            @Override
            public void end(Xid xid, int i) throws XAException {
            }

            @Override
            public void forget(Xid xid) throws XAException {
            }

            @Override
            public int getTransactionTimeout() throws XAException {
                return 0;
            }

            @Override
            public boolean isSameRM(XAResource xaResource) throws XAException {
                return false;
            }

            @Override
            public int prepare(Xid xid) throws XAException {
                return 0;
            }

            @Override
            public Xid[] recover(int i) throws XAException {
                return new Xid[0];
            }

            @Override
            public void rollback(Xid xid) throws XAException {
            }

            @Override
            public boolean setTransactionTimeout(int i) throws XAException {
                return false;
            }

            @Override
            public void start(Xid xid, int i) throws XAException {
            }
        };
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

