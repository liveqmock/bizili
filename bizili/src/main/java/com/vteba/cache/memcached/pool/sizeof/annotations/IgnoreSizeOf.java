package com.vteba.cache.memcached.pool.sizeof.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to ignore a field, type or entire package while doing a SizeOf measurement
 * @see net.sf.ehcache.pool.sizeof.SizeOf
 * @author Chris Dennis
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE, ElementType.PACKAGE })
public @interface IgnoreSizeOf {

    /**
     * Controls whether the annotation, when applied to a {@link ElementType#TYPE type} is to be applied to all its subclasses
     * as well or solely on that type only. true if inherited by subtypes, false otherwise
     */
    boolean inherited() default false;
}
