package com.vteba.cache.mybatis.memcached.spymemcached;

import net.spy.memcached.ConnectionFactory;
import net.spy.memcached.DefaultConnectionFactory;

/**
 * Setter from String to ConnectionFactory representation.
 *
 * @version $Id: ConnectionFactorySetter.java 5217 2012-04-23 19:59:07Z simonetripodi $
 */
final class ConnectionFactorySetter extends AbstractPropertySetter<ConnectionFactory> {

    /**
     * Instantiates a String to ConnectionFactory setter.
     */
    public ConnectionFactorySetter() {
        super("org.mybatis.caches.memcached.connectionfactory",
                "connectionFactory",
                new DefaultConnectionFactory());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ConnectionFactory convert(String property) throws Throwable {
        Class<?> clazz = Class.forName(property);
        if (!ConnectionFactory.class.isAssignableFrom(clazz)) {
            throw new IllegalArgumentException("Class '"
                    + clazz.getName()
                    + "' is not a valid '"
                    + ConnectionFactory.class.getName()
                    + "' implementation");
        }
        return (ConnectionFactory) clazz.newInstance();
    }

}
