package com.vteba.cache.memcached.manager;

import com.vteba.util.cryption.CryptionUtils;

/**
 * 缓存值封转
 * @author yinlei
 * date 2012-1-15 下午9:23:54
 */
public class CacheValue extends AbstractCacheObject {

	private static final long serialVersionUID = -198446587522835888L;
	/**是否是不需要序列化的对象*/
	private boolean isNotNeedSerial;
	/**对象是否可序列化*/
	private boolean serializable;
	private Object value;
	
	public CacheValue( Object value ) {
		if (value != null) {
			isNotNeedSerial = isNotNeedSerial(value);
			if (isNotNeedSerial) {
				this.value = value;
			} else {
				DeepOrCloneCopyResult copyResult = deepOrCloneCopy(value);
				this.serializable = copyResult.serializable();
				this.value = copyResult.getValue();
			}
		}
	}
	
	public Object getValue (){
		if (value == null) {
			return value;
		}
		if (isNotNeedSerial) {
			return value;
		} else {
			if (serializable) {
				return CryptionUtils.fromBinaryToObject((byte[])value);
			} else {
				return value;
			}
		}
	}
}
