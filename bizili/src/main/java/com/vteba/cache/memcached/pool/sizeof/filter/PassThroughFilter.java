package com.vteba.cache.memcached.pool.sizeof.filter;

import java.lang.reflect.Field;
import java.util.Collection;

/**
 * Filter that doesn't filter!
 * @author Chris Dennis
 */
public class PassThroughFilter implements SizeOfFilter {

    /**
     * {@inheritDoc}
     */
    public Collection<Field> filterFields(Class<?> klazz, Collection<Field> fields) {
        return fields;
    }

    /**
     * {@inheritDoc}
     */
    public boolean filterClass(Class<?> klazz) {
        return true;
    }
}
