package com.vteba.tx.bitronix.datasource;

/**
 *
 * @author lorban
 */
public interface PoolingDataSourceMBean {

    public int getMinPoolSize();
    public int getMaxPoolSize();
    public long getInPoolSize();
    public long getTotalPoolSize();
    public boolean isFailed();
    public void reset() throws Exception;
    public boolean isDisabled();
    public void setDisabled(boolean disabled);

}
