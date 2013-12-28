package com.vteba.cache.memcached.util.counter.sampled;

import com.vteba.cache.memcached.util.counter.Counter;

/**
 * Interface of a sampled counter -- a counter that keeps sampled values
 * 
 * @author <a href="mailto:asanoujam@terracottatech.com">Abhishek Sanoujam</a>
 * @since 1.7
 * 
 */
public interface SampledCounter extends Counter {
    /**
     * Shutdown this counter
     */
    void shutdown();

    /**
     * Returns the most recent sampled value
     * 
     * @return Value of the most recent sampled value
     */
    TimeStampedCounterValue getMostRecentSample();

    /**
     * Returns all samples in history
     * 
     * @return An array containing the TimeStampedCounterValue's
     */
    TimeStampedCounterValue[] getAllSampleValues();

    /**
     * Returns the current value of the counter and resets it to 0
     * 
     * @return current value of the counter
     */
    long getAndReset();

}
