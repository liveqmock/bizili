package com.vteba.cache.memcached.pool.sizeof;

import static com.vteba.cache.memcached.pool.sizeof.JvmInformation.CURRENT_JVM_INFORMATION;

/**
 * Primitive types in the VM type system and their sizes
 * @author Alex Snaps
 */
enum PrimitiveType {

    /**
     * boolean.class
     */
    BOOLEAN(boolean.class, 1),
    /**
     * byte.class
     */
    BYTE(byte.class, 1),
    /**
     * char.class
     */
    CHAR(char.class, 2),
    /**
     * short.class
     */
    SHORT(short.class, 2),
    /**
     * int.class
     */
    INT(int.class, 4),
    /**
     * float.class
     */
    FLOAT(float.class, 4),
    /**
     * double.class
     */
    DOUBLE(double.class, 8),
    /**
     * long.class
     */
    LONG(long.class, 8);

    private Class<?> type;
    private int size;


    private PrimitiveType(Class<?> type, int size) {
        this.type = type;
        this.size = size;
    }

    /**
     * Returns the size in memory this type occupies
     * @return size in bytes
     */
    public int getSize() {
        return size;
    }

    /**
     * The representing type
     * @return the type
     */
    public Class<?> getType() {
        return type;
    }

    /**
     * The size of a pointer
     * @return size in bytes
     */
    public static int getReferenceSize() {
        return CURRENT_JVM_INFORMATION.getJavaPointerSize();
    }

    /**
     * The size on an array
     * @return size in bytes
     */
    public static long getArraySize() {
        return CURRENT_JVM_INFORMATION.getObjectHeaderSize() + INT.getSize();
    }

    /**
     * Finds the matching PrimitiveType for a type
     * @param type the type to find the PrimitiveType for
     * @return the PrimitiveType instance or null if none found
     */
    public static PrimitiveType forType(final Class<?> type) {
        for (PrimitiveType primitiveType : values()) {
            if (primitiveType.getType() == type) {
                return primitiveType;
            }
        }
        return null;
    }
}
