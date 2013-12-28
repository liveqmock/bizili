package com.vteba.cache.memcached.util.counter;

/**
 * A simple counter
 * 
 * @author <a href="mailto:asanoujam@terracottatech.com">Abhishek Sanoujam</a>
 * 
 * @since 1.7
 */
public interface Counter {

    /**
     * Increment the counter by 1
     * 
     * @return the value after incrementing
     */
    long increment();

    /**
     * Decrement the counter by 1
     * 
     * @return the value after decrementing
     */
    long decrement();

    /**
     * Returns the value of the counter and sets it to the new value
     * 
     * @param newValue
     * @return Returns the old value
     */
    long getAndSet(long newValue);

    /**
     * Gets current value of the counter
     * 
     * @return current value of the counter
     */
    long getValue();

    /**
     * Increment the counter by given amount
     * 
     * @param amount
     * @return the value of the counter after incrementing
     */
    long increment(long amount);

    /**
     * Decrement the counter by given amount
     * 
     * @param amount
     * @return the value of the counter after decrementing
     */
    long decrement(long amount);

    /**
     * Sets the value of the counter to the supplied value
     * 
     * @param newValue
     */
    void setValue(long newValue);

}
