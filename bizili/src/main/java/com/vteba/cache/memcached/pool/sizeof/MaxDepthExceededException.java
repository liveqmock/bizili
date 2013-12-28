package com.vteba.cache.memcached.pool.sizeof;

/**
 * @author Ludovic Orban
 */
public class MaxDepthExceededException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private long measuredSize;

    /**
     * Constructor
     */
    public MaxDepthExceededException(String msg) {
        super(msg);
    }

    /**
     * Add to the partially measured size
     *
     * @param toAdd
     */
    public void addToMeasuredSize(long toAdd) {
        measuredSize += toAdd;
    }

    /**
     * Get the partially measured size
     *
     * @return
     */
    public long getMeasuredSize() {
        return measuredSize;
    }
}
