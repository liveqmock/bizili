package com.vteba.cache.hibernate;

import com.vteba.utils.cryption.CryptionUtils;

public class Md5KeyStrategy extends DigestKeyStrategy {
	
	protected String digest(String key) {
		return CryptionUtils.md5Hex(key);
	}
}
