package com.vteba.cache.hibernate;

import com.vteba.utils.cryption.CryptUtils;

/**
 * SHA1 digest，hibernate second level cache the default key strategy
 * @author Ray Krueger
 */
public class Sha1KeyStrategy extends DigestKeyStrategy {
	
	protected String digest(String key) {
		return CryptUtils.sha1Hex(key);
	}
}
