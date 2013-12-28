package com.vteba.cache.memcached.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

/**
 * Wraps a set to provide a list interface.
 * All list methods not application to set throws an {@link UnsupportedOperationException}
 *
 * @author Chris Dennis
 * @param <E>
 */
public class SetAsList<E> implements List<E> {

    private final Set<E> set;
    private transient Object[] array;

    /**
     * @param set
     */
    public SetAsList(Set<E> set) {
        this.set = set;
    }

    /**
     * {@inheritDoc}
     */
    public int size() {
        return set.size();
    }

    /**
     * {@inheritDoc}
     */
    public boolean isEmpty() {
        return set.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    public boolean contains(Object o) {
        return set.contains(o);
    }

    /**
     * {@inheritDoc}
     */
    public Iterator<E> iterator() {
        return set.iterator();
    }

    /**
     * {@inheritDoc}
     */
    public Object[] toArray() {
        return set.toArray();
    }

    /**
     * {@inheritDoc}
     */
    public <T> T[] toArray(T[] a) {
        return set.toArray(a);
    }

    /**
     * {@inheritDoc}
     */
    public boolean add(E e) {
        return set.add(e);
    }

    /**
     * {@inheritDoc}
     */
    public boolean remove(Object o) {
        return set.remove(o);
    }

    /**
     * {@inheritDoc}
     */
    public boolean containsAll(Collection<?> c) {
        return set.containsAll(c);
    }

    /**
     * {@inheritDoc}
     */
    public boolean addAll(Collection<? extends E> c) {
        return set.addAll(c);
    }

    /**
     * Does not support List methods {@link UnsupportedOperationException}.
     */
    public boolean addAll(int index, Collection<? extends E> c) {
        throw new UnsupportedOperationException("Delegates to set, operation not supported");
    }

    /**
     * {@inheritDoc}
     */
    public boolean removeAll(Collection<?> c) {
        return set.removeAll(c);
    }

    /**
     * {@inheritDoc}
     */
    public boolean retainAll(Collection<?> c) {
        return set.retainAll(c);
    }

    /**
     * {@inheritDoc}
     */
    public void clear() {
        set.clear();
    }

    /**
     * Does not support List methods {@link UnsupportedOperationException}.
     *
     * @param index Index
     */
    @SuppressWarnings("unchecked")
	public E get(int index) {
        if (this.array == null) {
            this.array = toArray();
        }
        if (array.length <= index) {
            throw new IndexOutOfBoundsException();
        }
        return (E) this.array[index];
    }

    /**
     * Does not support List methods {@link UnsupportedOperationException}.
     */
    public E set(int index, E element) {
        throw new UnsupportedOperationException("Delegates to set, operation not supported");
    }

    /**
     * Does not support List methods {@link UnsupportedOperationException}.
     */
    public void add(int index, E element) {
        throw new UnsupportedOperationException("Delegates to set, operation not supported");
    }

    /**
     * Does not support List methods {@link UnsupportedOperationException}.
     */
    public E remove(int index) {
        throw new UnsupportedOperationException("Delegates to set, operation not supported");
    }

    /**
     * Does not support List methods {@link UnsupportedOperationException}.
     */
    public int indexOf(Object o) {
        throw new UnsupportedOperationException("Delegates to set, operation not supported");
    }

    /**
     * Does not support List methods {@link UnsupportedOperationException}.
     */
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException("Delegates to set, operation not supported");
    }

    /**
     * Does not support List methods {@link UnsupportedOperationException}.
     */
    public ListIterator<E> listIterator() {
        throw new UnsupportedOperationException("Delegates to set, operation not supported");
    }

    /**
     * Does not support List methods {@link UnsupportedOperationException}.
     */
    public ListIterator<E> listIterator(int index) {
        throw new UnsupportedOperationException("Delegates to set, operation not supported");
    }

    /**
     * Does not support List methods {@link UnsupportedOperationException}.
     */
    public List<E> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("Delegates to set, operation not supported");
    }
}
