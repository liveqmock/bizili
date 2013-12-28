package com.vteba.cache.memcached.transaction.xa.processor;

import javax.transaction.xa.Xid;

/**
 * xa请求JavaBean信息
 * @author Nabib El-Rahman
 */
public class XARequest {

    private final RequestType requestType;

    private final Xid xid;
    private final boolean onePhase;
 
    /**
     * Constructor
     * 
     * @param requestType what request is this representing
     * @param xid the Xid of the transaction this request is being executed for
     */
    public XARequest(RequestType requestType, Xid xid) {
        this(requestType, xid, false);
    }
    
    /**
     * Constructor
     * 
     * @param requestType what request is this representing
     * @param xid the Xid of the transaction this request is being executed for
     * @param onePhase whether this is a single phase commit
     */
    public XARequest(RequestType requestType, Xid xid, boolean onePhase) {
        this.requestType = requestType;
        this.xid = xid;
        this.onePhase = onePhase;
    }
    
    /**
     * XA Requests types
     * 
     * @author Nabib El-Rahman
     *
     */
    public static enum RequestType {
        
        /**
         * prepare
         */
        PREPARE,
        
        /**
         * commit
         */
        COMMIT,
        
        /**
         * forget
         */
        FORGET,
        
        /**
         * rollback
         */
        ROLLBACK;
    }

    /**
     * 
     * @return the type of request
     */
    public RequestType getRequestType() {
        return requestType;
    }

    /**
     * 
     * @return the Xid of the Transaction
     */
    public Xid getXid() {
        return xid;
    }

    /**
     * 
     * @return true is one phase commit requested, otherwise false
     */
    public boolean isOnePhase() {
        return onePhase;
    }

}
