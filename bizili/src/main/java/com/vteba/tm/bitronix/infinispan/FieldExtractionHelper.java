package com.vteba.tm.bitronix.infinispan;

import org.infinispan.Cache;
import org.infinispan.factories.ComponentRegistry;

import java.lang.reflect.Field;

public class FieldExtractionHelper {

    public static Object extractComponent(Cache<?, ?> cache, Class<?> componentType) {
        ComponentRegistry cr = (ComponentRegistry) extractField(cache.getClass(), cache, "componentRegistry");
        return cr.getComponent(componentType);
    }

    public static Object extractField(Class<?> type, Object target, String fieldName) {
        Field field;
        try {
            field = type.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(target);
        } catch (Exception e) {
            if (type.equals(Object.class)) {
                e.printStackTrace();
                return null;
            } else {
                // try with superclass
                return extractField(type.getSuperclass(), target, fieldName);
            }
        }
    }

}
