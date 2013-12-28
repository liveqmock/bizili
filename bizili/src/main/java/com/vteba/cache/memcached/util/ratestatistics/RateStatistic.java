package com.vteba.cache.memcached.util.ratestatistics;

/**
 * Rate monitoring statistic.
 *
 * @author Chris Dennis
 */
public interface RateStatistic {

    /**
     * Fired to record the occurrence of a monitored event.
     */
    void event();

    /**
     * Returns the total number of events that have occurred in the lifetime of this statistic.
     *
     * @return total number of events
     */
    long getCount();

    /**
     * Return the rate of events per unit time.
     *
     * @return event rate
     */
    float getRate();
}
