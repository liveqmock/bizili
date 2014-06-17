package com.vteba.cache.hibernate;

import com.vteba.utils.cryption.CryptionUtils;

/**
 * SHA1 digest，hibernate second level cache the default key strategy
 * @author Ray Krueger
 */
public class Sha1KeyStrategy extends DigestKeyStrategy {
	
	protected String digest(String key) {
		return CryptionUtils.sha1Hex(key);
	}
}
