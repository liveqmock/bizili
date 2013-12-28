package com.vteba.cache.memcached.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to suppress FindBugs warnings in Ehcache core code.
 * 
 * @author Chris Dennis
 */
@Retention(RetentionPolicy.CLASS)
@Target(value = {ElementType.TYPE, ElementType.FIELD, ElementType.METHOD,
                 ElementType.PARAMETER, ElementType.CONSTRUCTOR, ElementType.LOCAL_VARIABLE })
public @interface FindBugsSuppressWarnings {
  /**
   * List of suppressed FindBugs warnings
   */
  String[] value();
}
