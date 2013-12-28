package com.vteba.cache.memcached.util.counter.sampled;

/**
 * An implementation of {@link SampledRateCounter}
 * 
 * @author <a href="mailto:asanoujam@terracottatech.com">Abhishek Sanoujam</a>
 * @since 1.7
 * 
 */
public class SampledRateCounterImpl extends SampledCounterImpl implements SampledRateCounter {

	private static final long serialVersionUID = 1L;

	private static final String OPERATION_NOT_SUPPORTED_MSG = "This operation is not supported. Use SampledCounter Or Counter instead";

    private long numeratorValue;
    private long denominatorValue;

    /**
     * Constructor accepting the config
     * 
     * @param config
     */
    public SampledRateCounterImpl(SampledRateCounterConfig config) {
        super(config);
    }

    /**
     * {@inheritDoc}
     */
    public synchronized void setValue(long numerator, long denominator) {
        this.numeratorValue = numerator;
        this.denominatorValue = denominator;
    }

    /**
     * {@inheritDoc}
     */
    public synchronized void increment(long numerator, long denominator) {
        this.numeratorValue += numerator;
        this.denominatorValue += denominator;
    }

    /**
     * {@inheritDoc}
     */
    public synchronized void decrement(long numerator, long denominator) {
        this.numeratorValue -= numerator;
        this.denominatorValue -= denominator;
    }

    /**
     * {@inheritDoc}
     */
    public synchronized void setDenominatorValue(long newValue) {
        this.denominatorValue = newValue;
    }

    /**
     * {@inheritDoc}
     */
    public synchronized void setNumeratorValue(long newValue) {
        this.numeratorValue = newValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized long getValue() {
        return denominatorValue == 0 ? 0 : (numeratorValue / denominatorValue);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized long getAndReset() {
        long prevVal = getValue();
        setValue(0, 0);
        return prevVal;
    }

    // ====== unsupported operations. These operations need multiple params for
    // this class
    /**
     * throws {@link UnsupportedOperationException}
     */
    @Override
    public long getAndSet(long newValue) {
        throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_MSG);
    }

    /**
     * throws {@link UnsupportedOperationException}
     */
    @Override
    public synchronized void setValue(long newValue) {
        throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_MSG);
    }

    /**
     * throws {@link UnsupportedOperationException}
     */
    @Override
    public long decrement() {
        throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_MSG);
    }

    /**
     * throws {@link UnsupportedOperationException}
     */
    @Override
    public long decrement(long amount) {
        throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_MSG);
    }

    /**
     * throws {@link UnsupportedOperationException}
     */
    public long getMaxValue() {
        throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_MSG);
    }

    /**
     * throws {@link UnsupportedOperationException}
     */
    public long getMinValue() {
        throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_MSG);
    }

    /**
     * throws {@link UnsupportedOperationException}
     */
    @Override
    public long increment() {
        throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_MSG);
    }

    /**
     * throws {@link UnsupportedOperationException}
     */
    @Override
    public long increment(long amount) {
        throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_MSG);
    }

}
