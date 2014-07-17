package com.vteba.cache.hibernate;

import com.vteba.utils.cryption.CryptUtils;

public class Md5KeyStrategy extends DigestKeyStrategy {
	
	protected String digest(String key) {
		return CryptUtils.md5Hex(key);
	}
}
