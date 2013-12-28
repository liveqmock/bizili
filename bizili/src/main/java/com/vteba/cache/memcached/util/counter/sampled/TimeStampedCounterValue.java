package com.vteba.cache.memcached.util.counter.sampled;

import java.io.Serializable;

/**
 * A counter value at a particular time instance
 * 
 * @author <a href="mailto:asanoujam@terracottatech.com">Abhishek Sanoujam</a>
 * @since 1.7
 */
public class TimeStampedCounterValue implements Serializable {

	private static final long serialVersionUID = 1L;
	private final long counterValue;
    private final long timestamp;

    /**
     * Constructor accepting the value of both timestamp and the counter value.
     * 
     * @param timestamp
     * @param value
     */
    public TimeStampedCounterValue(long timestamp, long value) {
        this.timestamp = timestamp;
        this.counterValue = value;
    }

    /**
     * Get the counter value
     * 
     * @return The counter value
     */
    public long getCounterValue() {
        return this.counterValue;
    }

    /**
     * Get value of the timestamp
     * 
     * @return the timestamp associated with the current value
     */
    public long getTimestamp() {
        return this.timestamp;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "value: " + this.counterValue + ", timestamp: " + this.timestamp;
    }

}
