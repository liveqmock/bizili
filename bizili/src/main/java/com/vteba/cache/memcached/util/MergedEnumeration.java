package com.vteba.cache.memcached.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
/**
 *
 * An utility class to merge several enumerations into a single one
 *
 * @author Anthony Dahanne
 *
 * @param <E>
 */
public class MergedEnumeration<E> implements Enumeration<E> {

    private final Enumeration<E> enumeration;

    /**
     * Merges all enumerations found as constructor arguments into a single one
     *
     * @param enumerations
     */
    @SafeVarargs
	public MergedEnumeration(Enumeration<E>... enumerations) {
        List<E>  list = new ArrayList<E>();
        for (Enumeration<E> element : enumerations) {
            while (element.hasMoreElements()) {
                E e = element.nextElement();
                list.add(e);
            }
        }
        enumeration = Collections.enumeration(list);
    }

    @Override
    public boolean hasMoreElements() {
        return enumeration.hasMoreElements();
    }

    @Override
    public E nextElement() {
        return enumeration.nextElement();
    }

}
