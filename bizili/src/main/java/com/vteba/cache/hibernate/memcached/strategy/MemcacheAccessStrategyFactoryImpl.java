package com.vteba.cache.hibernate.memcached.strategy;

import org.hibernate.cache.spi.access.AccessType;
import org.hibernate.cache.spi.access.CollectionRegionAccessStrategy;
import org.hibernate.cache.spi.access.EntityRegionAccessStrategy;
import org.hibernate.cache.spi.access.NaturalIdRegionAccessStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vteba.cache.hibernate.memcached.regions.MemcacheCollectionRegion;
import com.vteba.cache.hibernate.memcached.regions.MemcacheEntityRegion;
import com.vteba.cache.hibernate.memcached.regions.MemcacheNaturalIdRegion;

/**
 * 访问策略工厂实现 {@link MemcacheAccessStrategyFactory}
 */
public class MemcacheAccessStrategyFactoryImpl implements MemcacheAccessStrategyFactory {

    private static final Logger LOG = LoggerFactory.getLogger(MemcacheAccessStrategyFactoryImpl.class);

    /**
     * {@inheritDoc}
     */
    public EntityRegionAccessStrategy createEntityRegionAccessStrategy(MemcacheEntityRegion entityRegion, AccessType accessType) {
        switch ( accessType ) {
            case READ_ONLY:
                if ( entityRegion.getCacheDataDescription().isMutable() ) {
                    LOG.info("readOnlyCacheConfiguredForMutableEntity" + entityRegion.getName() );
                }
                return new ReadOnlyMemcacheEntityRegionAccessStrategy( entityRegion, entityRegion.getSettings() );
            case READ_WRITE:
                return new ReadWriteMemcacheEntityRegionAccessStrategy( entityRegion, entityRegion.getSettings() );

            case NONSTRICT_READ_WRITE:
                return new NonStrictReadWriteMemcacheEntityRegionAccessStrategy(
                        entityRegion,
                        entityRegion.getSettings()
                );

            case TRANSACTIONAL:
                return new TransactionalMemcacheEntityRegionAccessStrategy(
                        entityRegion,
                        entityRegion.getCache(),
                        entityRegion.getSettings()
                );
            default:
                throw new IllegalArgumentException( "unrecognized access strategy type [" + accessType + "]" );

        }

    }

	/**
	 * {@inheritDoc}
	 */
	public CollectionRegionAccessStrategy createCollectionRegionAccessStrategy(
			MemcacheCollectionRegion collectionRegion, AccessType accessType) {
        switch ( accessType ) {
            case READ_ONLY:
                if ( collectionRegion.getCacheDataDescription().isMutable() ) {
                    LOG.info("readOnlyCacheConfiguredForMutableEntity" + collectionRegion.getName() );
                }
                return new ReadOnlyMemcacheCollectionRegionAccessStrategy(
                        collectionRegion,
                        collectionRegion.getSettings()
                );
            case READ_WRITE:
                return new ReadWriteMemcacheCollectionRegionAccessStrategy(
                        collectionRegion,
                        collectionRegion.getSettings()
                );
            case NONSTRICT_READ_WRITE:
                return new NonStrictReadWriteMemcacheCollectionRegionAccessStrategy(
                        collectionRegion,
                        collectionRegion.getSettings()
                );
            case TRANSACTIONAL:
                return new TransactionalMemcacheCollectionRegionAccessStrategy(
                        collectionRegion, collectionRegion.getCache(), collectionRegion
                        .getSettings()
                );
            default:
                throw new IllegalArgumentException( "unrecognized access strategy type [" + accessType + "]" );
        }
    }

	@Override
	public NaturalIdRegionAccessStrategy createNaturalIdRegionAccessStrategy(MemcacheNaturalIdRegion naturalIdRegion,
			AccessType accessType) {
        switch ( accessType ) {
        case READ_ONLY:
            if ( naturalIdRegion.getCacheDataDescription().isMutable() ) {
                LOG.info("readOnlyCacheConfiguredForMutableEntity" + naturalIdRegion.getName() );
            }
            return new ReadOnlyMemcacheNaturalIdRegionAccessStrategy(
                    naturalIdRegion,
                    naturalIdRegion.getSettings()
            );
        case READ_WRITE:
            return new ReadWriteMemcacheNaturalIdRegionAccessStrategy(
                    naturalIdRegion,
                    naturalIdRegion.getSettings()
            );
        case NONSTRICT_READ_WRITE:
            return new NonStrictReadWriteMemcacheNaturalIdRegionAccessStrategy(
                    naturalIdRegion,
                    naturalIdRegion.getSettings()
            );
        case TRANSACTIONAL:
            return new TransactionalMemcacheNaturalIdRegionAccessStrategy(
                    naturalIdRegion, naturalIdRegion.getCache(), naturalIdRegion
                    .getSettings()
            );
        default:
            throw new IllegalArgumentException( "unrecognized access strategy type [" + accessType + "]" );
    }
	}

    
}
