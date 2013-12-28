package com.vteba.cache.memcached.util;

/**
 * Misc. VM utilities.
 * 
 * @author Ludovic Orban
 */
public class VmUtils {

    private static boolean inGoogleAppEngine;

    static {
        try {
            Class.forName("com.google.apphosting.api.DeadlineExceededException");
            inGoogleAppEngine = true;
        } catch (ClassNotFoundException cnfe) {
            inGoogleAppEngine = false;
        }
    }

    /**
     * @return true if the code is being executed by Google's App Engine, false otherwise.
     */
    public static boolean isInGoogleAppEngine() {
        return inGoogleAppEngine;
    }
}
