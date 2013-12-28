package com.vteba.cache.memcached.util.ratestatistics;

import java.util.concurrent.TimeUnit;

/**
 * Abstract rate statistic implementation.
 * <p>
 * Provides exponentially decaying moving average functionality to subclasses.
 *
 * @author Chris Dennis
 */
abstract class AbstractRateStatistic implements RateStatistic {

    private final long rateAveragePeriod;

    /**
     * Create an abstract statistic using the specified time averaging period.
     *
     * @param averagePeriod average period
     * @param unit period time unit
     */
    AbstractRateStatistic(long averagePeriod, TimeUnit unit) {
        this.rateAveragePeriod = unit.toNanos(averagePeriod);
    }

    /**
     * Returns the time averaging period in nanoseconds.
     *
     * @return average period
     */
    long getRateAveragePeriod() {
        return rateAveragePeriod;
    }

    /**
     * Combines two timestamped values using a exponentially decaying weighted average.
     *
     * @param nowValue current value
     * @param now current value timestamp
     * @param thenAverage previous value
     * @param then previous value timestamp
     * @return weighted average
     */
    float iterateMovingAverage(float nowValue, long now, float thenAverage, long then) {
        if (getRateAveragePeriod() == 0 || Float.isNaN(thenAverage)) {
            return nowValue;
        } else {
            float alpha = (float) alpha(now, then);
            return alpha * nowValue + (1 - alpha) * thenAverage;
        }
    }

    private double alpha(long now, long then) {
        return -Math.expm1(-((double) (now - then)) / getRateAveragePeriod());
    }
}
