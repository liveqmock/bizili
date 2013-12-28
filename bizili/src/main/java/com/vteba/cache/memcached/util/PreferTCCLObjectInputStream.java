package com.vteba.cache.memcached.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

/**
 * ObjectInputStream that first uses the thread context classloader (TCCL) when resolving classes with fallback to the regular rerializtion
 * loader semantics (which will use this class's loader to resolve classes)
 *
 * @author teck
 */
public class PreferTCCLObjectInputStream extends ObjectInputStream {

    /**
     * Constructor
     *
     * @param in
     * @throws IOException
     */
    public PreferTCCLObjectInputStream(InputStream in) throws IOException {
        super(in);
    }

    @Override
    protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
        return ClassLoaderUtil.loadClass(desc.getName());
    }

}
