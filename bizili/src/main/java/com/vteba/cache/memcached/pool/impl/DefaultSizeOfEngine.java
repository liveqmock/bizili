package com.vteba.cache.memcached.pool.impl;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vteba.cache.hibernate.CacheException;
import com.vteba.cache.memcached.pool.Size;
import com.vteba.cache.memcached.pool.SizeOfEngine;
import com.vteba.cache.memcached.pool.sizeof.AgentSizeOf;
import com.vteba.cache.memcached.pool.sizeof.ReflectionSizeOf;
import com.vteba.cache.memcached.pool.sizeof.SizeOf;
import com.vteba.cache.memcached.pool.sizeof.UnsafeSizeOf;
import com.vteba.cache.memcached.pool.sizeof.filter.AnnotationSizeOfFilter;
import com.vteba.cache.memcached.pool.sizeof.filter.CombinationSizeOfFilter;
import com.vteba.cache.memcached.pool.sizeof.filter.ResourceSizeOfFilter;
import com.vteba.cache.memcached.pool.sizeof.filter.SizeOfFilter;
import com.vteba.cache.memcached.util.ClassLoaderUtil;

/**
 * @author Alex Snaps
 */
public class DefaultSizeOfEngine implements SizeOfEngine {

    /**
     * System property defining a user specific resource based size-of filter.
     * <p>
     * The resource pointed to by this property must be a list of fully qualified
     * field or class names, one per line:
     * <pre>
     * # This is a comment
     * org.mycompany.domain.MyType
     * org.mycompany.domain.MyOtherType.myField
     * </pre>
     * Fields or types matching against lines in this resource will be ignored
     * when calculating the size of the object graph.
     */
    public static final String USER_FILTER_RESOURCE = "net.sf.ehcache.sizeof.filter";
    
    private static final Logger LOG = LoggerFactory.getLogger(DefaultSizeOfEngine.class.getName());
    private static final String VERBOSE_DEBUG_LOGGING = "net.sf.ehcache.sizeof.verboseDebugLogging";

    private static final SizeOfFilter DEFAULT_FILTER;
    private static final boolean USE_VERBOSE_DEBUG_LOGGING;

    static {
        Collection<SizeOfFilter> filters = new ArrayList<SizeOfFilter>();
        filters.add(new AnnotationSizeOfFilter());
        try {
            filters.add(new ResourceSizeOfFilter(SizeOfEngine.class.getResource("builtin-sizeof.filter")));
        } catch (IOException e) {
            LOG.warn("Built-in sizeof filter could not be loaded: {}", e);
        }
        SizeOfFilter userFilter = getUserFilter();
        if (userFilter != null) {
            filters.add(userFilter);
        }
        DEFAULT_FILTER = new CombinationSizeOfFilter(filters.toArray(new SizeOfFilter[filters.size()]));

        USE_VERBOSE_DEBUG_LOGGING = getVerboseSizeOfDebugLogging();
    }

    private final SizeOf sizeOf;
    private final int maxDepth;
    private final boolean abortWhenMaxDepthExceeded;

    /**
     * Creates a default size of engine using the best available sizing algorithm.
     * @param maxDepth the max object graph that will be traversed.
     * @param abortWhenMaxDepthExceeded true if the object traversal should be aborted when the max depth is exceeded
     */
    public DefaultSizeOfEngine(int maxDepth, boolean abortWhenMaxDepthExceeded) {
        this.maxDepth = maxDepth;
        this.abortWhenMaxDepthExceeded = abortWhenMaxDepthExceeded;
        SizeOf bestSizeOf;
        try {
            bestSizeOf = new AgentSizeOf(DEFAULT_FILTER);
            LOG.info("using Agent sizeof engine");
        } catch (UnsupportedOperationException e) {
            try {
                bestSizeOf = new UnsafeSizeOf(DEFAULT_FILTER);
                LOG.info("using Unsafe sizeof engine");
            } catch (UnsupportedOperationException f) {
                try {
                    bestSizeOf = new ReflectionSizeOf(DEFAULT_FILTER);
                    LOG.info("using Reflection sizeof engine");
                } catch (UnsupportedOperationException g) {
                    throw new CacheException("A suitable SizeOf engine could not be loaded: " + e + ", " + f + ", " + g);
                }
            }
        }

        this.sizeOf = bestSizeOf;
    }

    private DefaultSizeOfEngine(DefaultSizeOfEngine defaultSizeOfEngine, int maxDepth, boolean abortWhenMaxDepthExceeded) {
        this.sizeOf = defaultSizeOfEngine.sizeOf;
        this.maxDepth = maxDepth;
        this.abortWhenMaxDepthExceeded = abortWhenMaxDepthExceeded;
    }

    /**
     * {@inheritDoc}
     */
    public SizeOfEngine copyWith(int maxDepth, boolean abortWhenMaxDepthExceeded) {
        return new DefaultSizeOfEngine(this, maxDepth, abortWhenMaxDepthExceeded);
    }

    private static SizeOfFilter getUserFilter() {
        String userFilterProperty = System.getProperty(USER_FILTER_RESOURCE);

        if (userFilterProperty != null) {
            List<URL> filterUrls = new ArrayList<URL>();
            try {
                filterUrls.add(new URL(userFilterProperty));
            } catch (MalformedURLException e) {
                LOG.debug("MalformedURLException using {} as a URL", userFilterProperty);
            }
            try {
                filterUrls.add(new File(userFilterProperty).toURI().toURL());
            } catch (MalformedURLException e) {
                LOG.debug("MalformedURLException using {} as a file URL", userFilterProperty);
            }
            filterUrls.add(ClassLoaderUtil.getStandardClassLoader().getResource(userFilterProperty));
            for (URL filterUrl : filterUrls) {
                SizeOfFilter filter;
                try {
                    filter = new ResourceSizeOfFilter(filterUrl);
                    LOG.info("Using user supplied filter @ {}", filterUrl);
                    return filter;
                } catch (IOException e) {
                    LOG.debug("IOException while loading user size-of filter resource", e);
                }
            }
        }
        return null;
    }

    private static boolean getVerboseSizeOfDebugLogging() {

        String verboseString = System.getProperty(VERBOSE_DEBUG_LOGGING, "false");

        return verboseString.equals("true");
    }

    /**
     * {@inheritDoc}
     */
    public Size sizeOf(final Object key, final Object value, final Object container) {
        Size size = sizeOf.deepSizeOf(maxDepth, abortWhenMaxDepthExceeded, key, value, container);

        if (USE_VERBOSE_DEBUG_LOGGING && LOG.isDebugEnabled()) {
            LOG.debug("size of {}/{}/{} -> {}", new Object[]{key, value, container, size.getCalculated()});
        }
        return size;
    }
}
