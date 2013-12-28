package com.vteba.cache.memcached.transaction.xa;

import javax.transaction.xa.XAException;

/**
 * Small extension to the XAException defined in the JTA specification, to that the errorCode is provided when
 * instantiating the Exception thrown
 *
 * @author Alex Snaps
 */
public class EhcacheXAException extends XAException {

	private static final long serialVersionUID = 1L;

	/**
     * Constructor
     * @param message The message
     * @param errorCode the XA error code
     */
    public EhcacheXAException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode; 
    }

    /**
     * Constructor
     * @param message The message
     * @param errorCode the XA error code
     * @param cause the Exception causing the XAException
     */
    public EhcacheXAException(String message, int errorCode, Throwable cause) {
        super(message);
        this.errorCode = errorCode;
        initCause(cause);
    }
}
