package com.vteba.cache.memcached.util.counter.sampled;

import com.vteba.cache.memcached.util.counter.Counter;

/**
 * An implementation of {@link SampledCounterConfig}
 * 
 * @author <a href="mailto:asanoujam@terracottatech.com">Abhishek Sanoujam</a>
 * @since 1.7
 * 
 */
public class SampledRateCounterConfig extends SampledCounterConfig {

    private final long initialNumeratorValue;
    private final long initialDenominatorValue;

    /**
     * Constructor accepting the interval time in seconds, history-size and
     * whether counters should reset on each sample or not.
     * Initial values of both numerator and denominator are zeroes
     * 
     * @param intervalSecs
     * @param historySize
     * @param isResetOnSample
     */
    public SampledRateCounterConfig(int intervalSecs, int historySize, boolean isResetOnSample) {
        this(intervalSecs, historySize, isResetOnSample, 0, 0);
    }

    /**
     * Constructor accepting the interval time in seconds, history-size and
     * whether counters should reset on each sample or not. Also the initial
     * values for the numerator and the denominator
     * 
     * @param intervalSecs
     * @param historySize
     * @param isResetOnSample
     * @param initialNumeratorValue
     * @param initialDenominatorValue
     */
    public SampledRateCounterConfig(int intervalSecs, int historySize, boolean isResetOnSample, long initialNumeratorValue,
            long initialDenominatorValue) {
        super(intervalSecs, historySize, isResetOnSample, 0);
        this.initialNumeratorValue = initialNumeratorValue;
        this.initialDenominatorValue = initialDenominatorValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Counter createCounter() {
        SampledRateCounterImpl sampledRateCounter = new SampledRateCounterImpl(this);
        sampledRateCounter.setValue(initialNumeratorValue, initialDenominatorValue);
        return sampledRateCounter;
    }

}
