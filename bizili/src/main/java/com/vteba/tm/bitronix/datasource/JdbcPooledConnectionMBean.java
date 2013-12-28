package com.vteba.tm.bitronix.datasource;

import java.util.Collection;
import java.util.Date;

/**
 * {@link JdbcPooledConnection} Management interface.
 *
 * @author lorban
 */
public interface JdbcPooledConnectionMBean {

    String getStateDescription();
    Date getAcquisitionDate();
    Collection<?> getTransactionGtridsCurrentlyHoldingThis();

}
