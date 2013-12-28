package com.vteba.cache.memcached.util.counter;

/**
 * A Counter Manager that accepts a config to create counters. Creates counter's
 * based on {@link CounterConfig}. This manages the lifycycle of a counter
 *
 * @author <a href="mailto:asanoujam@terracottatech.com">Abhishek Sanoujam</a>
 * @since 1.7
 *
 */
public interface CounterManager {
    /**
     * Creates a Counter based on the passed config
     *
     * @param config
     * @return The counter created and managed by this CounterManager
     */
    Counter createCounter(CounterConfig config);

    /**
     * Adds a counter.
     */
    void addCounter(Counter counter);

    /**
     * Shuts down this counter manager
     */
    void shutdown();

    /**
     * Shuts down the counter
     *
     * @param counter
     */
    void shutdownCounter(Counter counter);

}
