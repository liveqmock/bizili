package com.vteba.cache.memcached.pool;

/**
 * Holder for the size calculated by the SizeOf engine
 *
 * @author Ludovic Orban
 */
public final class Size {

    private final long calculated;
    private final boolean exact;

    /**
     * Constructor
     *
     * @param calculated the calculated size
     * @param exact true if the calculated size is exact, false if it's an estimate or known to be inaccurate in some way
     */
    public Size(long calculated, boolean exact) {
        this.calculated = calculated;
        this.exact = exact;
    }

    /**
     * Get the calculated size
     *
     * @return the calculated size
     */
    public long getCalculated() {
        return calculated;
    }

    /**
     * Check if the calculated size is exact
     *
     * @return true if the calculated size is exact, false if it's an estimate or known to be inaccurate in some way
     */
    public boolean isExact() {
        return exact;
    }

}
