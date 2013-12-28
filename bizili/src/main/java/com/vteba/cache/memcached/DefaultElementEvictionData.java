package com.vteba.cache.memcached;

/**
 * Default implementation of the element eviction data storage that just keeps
 * all the data in instance fields in the heap.
 * 
 * @author Geert Bevin
 * @version $Id: DefaultElementEvictionData.java 1219 2009-09-25 05:10:31Z
 *          gbevin $
 */
public class DefaultElementEvictionData implements ElementEvictionData {

    /**
     * The creation time.
     */
    private long creationTime;
    
    /**
     * The last access time.
     */
    private long lastAccessTime;

    /**
     * Default constructor initializing the field to their empty values
     */
    public DefaultElementEvictionData(long creationTime) {
        this.creationTime = creationTime;
    }
    
    /**
     * Constructor allowing custom values for the data fields.
     * 
     * @param lastAccessTime
     */
    public DefaultElementEvictionData(long creationTime, long lastAccessTime) {
        this.creationTime = creationTime;
        this.lastAccessTime = lastAccessTime;
    }

    /**
     * {@inheritDoc}
     */
    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;        
    }

    /**
     * {@inheritDoc}
     */
    public long getCreationTime() {
        return creationTime;        
    }

    /**
     * {@inheritDoc}
     */
    public long getLastAccessTime() {
        return lastAccessTime;        
    }

    /**
     * {@inheritDoc}
     */
    public void updateLastAccessTime(long time, Element element) {
        lastAccessTime = time;
    }

    /**
     * {@inheritDoc}
     */
    public void resetLastAccessTime(Element element) {
        lastAccessTime = System.currentTimeMillis();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final ElementEvictionData clone() throws CloneNotSupportedException {
        DefaultElementEvictionData result = (DefaultElementEvictionData)super.clone();
        result.creationTime = this.creationTime;
        result.lastAccessTime = this.lastAccessTime;
        return result;
    }

     /**
      * {@inheritDoc}
      */
    public boolean canParticipateInSerialization() {
        return true;
    }
}
