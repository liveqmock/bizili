package com.vteba.cache.mybatis.memcached.spymemcached;

import java.util.concurrent.TimeUnit;

/**
 * Setter from String to TimeUnit representation.
 *
 * @version $Id: TimeUnitSetter.java 5217 2012-04-23 19:59:07Z simonetripodi $
 */
final class TimeUnitSetter extends AbstractPropertySetter<TimeUnit> {

    /**
     * Instantiates a String to TimeUnit setter.
     */
    public TimeUnitSetter() {
        super("org.mybatis.caches.memcached.timeoutunit",
                "timeUnit",
                TimeUnit.SECONDS);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected TimeUnit convert(String property) throws Throwable {
        return TimeUnit.valueOf(property.toUpperCase());
    }

}
