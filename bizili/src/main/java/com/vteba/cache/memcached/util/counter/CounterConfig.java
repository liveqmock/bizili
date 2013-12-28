package com.vteba.cache.memcached.util.counter;

/**
 * Config for a simple Counter
 * 
 * @author <a href="mailto:asanoujam@terracottatech.com">Abhishek Sanoujam</a>
 * @since 1.7
 * 
 */
public class CounterConfig {

    private final long initialValue;

    /**
     * Creates a config with the initial value
     * 
     * @param initialValue
     */
    public CounterConfig(long initialValue) {
        this.initialValue = initialValue;
    }

    /**
     * Gets the initial value
     * 
     * @return the initial value of counters created by this config
     */
    public final long getInitialValue() {
        return initialValue;
    }

    /**
     * Creates and returns a Counter based on the initial value
     * 
     * @return The counter created by this config
     */
    public Counter createCounter() {
        return new CounterImpl(initialValue);
    }
}
