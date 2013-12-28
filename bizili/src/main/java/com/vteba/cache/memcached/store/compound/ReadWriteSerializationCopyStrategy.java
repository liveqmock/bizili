package com.vteba.cache.memcached.store.compound;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.vteba.cache.hibernate.CacheException;
import com.vteba.cache.memcached.Element;
import com.vteba.cache.memcached.util.PreferTCCLObjectInputStream;

/**
 * A copy strategy that can use partial (if both copy on read and copy on write are set) or full Serialization to copy the object graph
 *
 * @author Alex Snaps
 * @author Ludovic Orban
 */
public class ReadWriteSerializationCopyStrategy implements ReadWriteCopyStrategy<Element> {

	private static final long serialVersionUID = -8835430949081568972L;

	public ReadWriteSerializationCopyStrategy(){
		
	}
    /**
     * @inheritDoc
     */
    public Element copyForWrite(Element value) {
        if (value == null) {
            return null;
        } else {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            ObjectOutputStream oos = null;

            if (value.getObjectValue() == null) {
                return duplicateElementWithNewValue(value, null);
            }

            try {
                oos = new ObjectOutputStream(bout);
                oos.writeObject(value.getObjectValue());
            } catch (Exception e) {
                throw new CacheException("When configured copyOnRead or copyOnWrite, a Store will only accept Serializable values", e);
            } finally {
                try {
                    if (oos != null) {
                        oos.close();
                    }
                } catch (Exception e) {
                    //
                }
            }

            return duplicateElementWithNewValue(value, bout.toByteArray());
        }
    }

    /**
     * @inheritDoc
     */
    public Element copyForRead(Element storedValue) {
        if (storedValue == null) {
            return null;
        } else {
            if (storedValue.getObjectValue() == null) {
                return duplicateElementWithNewValue(storedValue, null);
            }

            ByteArrayInputStream bin = new ByteArrayInputStream((byte[]) storedValue.getObjectValue());
            ObjectInputStream ois = null;
            try {
                ois = new PreferTCCLObjectInputStream(bin);
                return duplicateElementWithNewValue(storedValue, ois.readObject());
            } catch (Exception e) {
                throw new CacheException("When configured copyOnRead or copyOnWrite, a Store will only accept Serializable values", e);
            } finally {
                try {
                    if (ois != null) {
                        ois.close();
                    }
                } catch (Exception e) {
                    //
                }
            }
        }
    }

    /**
     * Make a duplicate of an element but using the specified value
     *
     * @param element  the element to duplicate
     * @param newValue the new element's value
     * @return the duplicated element
     */
    public Element duplicateElementWithNewValue(final Element element, final Object newValue) {
        return new Element(element.getObjectKey(), newValue, element.getVersion(),
                element.getCreationTime(), element.getLastAccessTime(), element.getHitCount(), element.usesCacheDefaultLifespan(),
                element.getTimeToLive(), element.getTimeToIdle(), element.getLastUpdateTime());
    }

}
