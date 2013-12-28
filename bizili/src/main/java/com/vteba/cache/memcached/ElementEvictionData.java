package com.vteba.cache.memcached;

/**
 * Provides pluggable storage and configuration of TTI eviction data.
 * 
 * @author Geert Bevin
 * @version $Id: ElementEvictionData.java 5631 2012-05-10 08:31:33Z teck $
 */
public interface ElementEvictionData extends Cloneable {

    /**
     * Sets the element creation time. Note that this is optional to implement and might result in a no-op.
     * 
     * @param creationTime the new element's creation time
     */
    public void setCreationTime(long creationTime);
    
    /**
     * Get the element's creation time.
     * 
     * @return the element's creation time in seconds
     */
    public long getCreationTime();

    /**
     * Gets the last access time.
     * Access means a get. So a newly created {@link Element} will have a last
     * access time equal to its create time.
     * 
     * @return the element's last access time in seconds
     */
    public long getLastAccessTime();

    /**
     * Updates the last access time.
     * 
     * @param time
     *            the new last access time
     * @param element
     *            the element for which the last access time is set
     */
    public void updateLastAccessTime(long time, Element element);

    /**
     * Resets the last access time.
     * 
     * @param element
     *            the element for which the last access time is set
     */
    public void resetLastAccessTime(Element element);

    /**
     * Creates a clone of the eviction data
     * 
     * @return a clone of the eviction data
     * @throws CloneNotSupportedException
     */
    public ElementEvictionData clone() throws CloneNotSupportedException;

    /**
     * Indicates whether the data of this element eviction instance can
     * participate in serialization of the element as a whole.
     * 
     * @return {@code true} when the data can participate in serialization; or
     *         {@code false} otherwise
     */
    public boolean canParticipateInSerialization();
}
