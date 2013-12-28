package com.vteba.cache.memcached.util.counter.sampled;

import com.vteba.cache.memcached.util.counter.Counter;
import com.vteba.cache.memcached.util.counter.CounterConfig;

/**
 * Config for a {@link SampledCounter}
 * 
 * @author <a href="mailto:asanoujam@terracottatech.com">Abhishek Sanoujam</a>
 * @since 1.7
 * 
 */
public class SampledCounterConfig extends CounterConfig {
    private final int intervalSecs;
    private final int historySize;
    private final boolean isReset;

    /**
     * Make a new timed counter config (duh)
     * 
     * @param intervalSecs
     *            the interval (in seconds) between sampling
     * @param historySize
     *            number of counter samples that will be retained in memory
     * @param isResetOnSample
     *            true if the counter should be reset to 0 upon each sample
     */
    public SampledCounterConfig(int intervalSecs, int historySize, boolean isResetOnSample, long initialValue) {
        super(initialValue);
        if (intervalSecs < 1) {
            throw new IllegalArgumentException("Interval (" + intervalSecs + ") must be greater than or equal to 1");
        }
        if (historySize < 1) {
            throw new IllegalArgumentException("History size (" + historySize + ") must be greater than or equal to 1");
        }

        this.intervalSecs = intervalSecs;
        this.historySize = historySize;
        this.isReset = isResetOnSample;
    }

    /**
     * Returns the history size
     * 
     * @return The history size
     */
    public int getHistorySize() {
        return historySize;
    }

    /**
     * Returns the interval time (seconds)
     * 
     * @return Interval of the sampling thread in seconds
     */
    public int getIntervalSecs() {
        return intervalSecs;
    }

    /**
     * Returns true if counters created from this config will reset on each
     * sample
     * 
     * @return true if values are reset to the initial value after each sample
     */
    public boolean isResetOnSample() {
        return this.isReset;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Counter createCounter() {
        return new SampledCounterImpl(this);
    }
}
