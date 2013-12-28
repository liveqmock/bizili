package com.vteba.cache.memcached.util.counter.sampled;

/**
 * Interface of a sampled rate counter -- a counter that keeps sampled values of
 * rates
 *
 * @author <a href="mailto:asanoujam@terracottatech.com">Abhishek Sanoujam</a>
 * @since 1.7
 *
 */
public interface SampledRateCounter extends SampledCounter {

    /**
     * Increments the numerator and denominator by the passed values
     *
     * @param numerator
     * @param denominator
     */
    void increment(long numerator, long denominator);

    /**
     * Decrements the numerator and denominator by the passed values
     *
     * @param numerator
     * @param denominator
     */
    void decrement(long numerator, long denominator);

    /**
     * Sets the values of the numerator and denominator to the passed values
     *
     * @param numerator
     * @param denominator
     */
    void setValue(long numerator, long denominator);

    /**
     * Sets the value of the numerator to the passed value
     *
     * @param newValue
     */
    void setNumeratorValue(long newValue);

    /**
     * Sets the value of the denominator to the passed value
     *
     * @param newValue
     */
    void setDenominatorValue(long newValue);

}
