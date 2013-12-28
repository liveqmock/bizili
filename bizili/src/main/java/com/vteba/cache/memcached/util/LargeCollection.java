package com.vteba.cache.memcached.util;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * Collection for large set. The general purpose is not to iterator through
 * all the keys for add and remove operations.
 * @author Nabib El-Rahman
 * @param <E>
 */
public abstract class LargeCollection < E > extends AbstractCollection < E > {

    /**
     * Set that keeps tabs on add add() to collection.
     */
    private final Collection < E > addSet;

    /**
     * Set that keeps tabs of all remove() to collection.
     */
    private final Collection < Object > removeSet;

    /**
     * default constructor.
     */
    public LargeCollection() {
      this.addSet = new HashSet<E>();
      this.removeSet = new HashSet<Object>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean add(final E obj) {
      return this.addSet.add(obj);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean contains(final Object obj) {
      return !removeSet.contains(obj) ? addSet.contains(obj)
        || super.contains(obj) : false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean remove(final Object obj) {
      return removeSet.add(obj);
    }

    /**
     * {@inheritDoc}
     */
    public final boolean removeAll(final Collection < ? > removeCandidates) {
      boolean remove = true;
      for (Iterator<?> iter = removeCandidates.iterator(); iter.hasNext();) {
         remove = remove(iter.next()) & remove;
      }
      return remove;
    }

    /**
     * Iterator for addSet.
     * @return Iterator < E >
     */
    private Iterator < E > additionalIterator() {
      return addSet.iterator();
    }

    /**
     * {@inheritDoc}
     */
    public final Iterator < E > iterator() {
      List < Iterator < E > > iterators = new ArrayList<Iterator < E >>();
      iterators.add(sourceIterator());
      iterators.add(additionalIterator());
      return new AggregateIterator<E>(removeSet, iterators);
    }

    /**
     * {@inheritDoc}
     */
    public final int size() {
      return sourceSize() + addSet.size() - removeSet.size();
    }

    /**
     * Iterator of initial set of entries.
     * @return Iterator < E >
     */
    public abstract Iterator < E > sourceIterator();

    /**
     * Initial set of entries size.
     * @return integer
     */
    public abstract int sourceSize();

}
