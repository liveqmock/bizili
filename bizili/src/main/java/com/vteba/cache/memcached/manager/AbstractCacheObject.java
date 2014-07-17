package com.vteba.cache.memcached.manager;

import java.io.Serializable;

import org.apache.commons.collections.functors.CloneTransformer;

import com.vteba.utils.serialize.NativeSerializerUtils;

/**
 * 抽象缓存对象封装
 * @author yinlei
 * date 2012-1-15 下午9:24:44
 */
public class AbstractCacheObject implements Serializable {

	private static final long serialVersionUID = 973419786850312705L;
	
	/**
	 * 是否是不需要序列化的对象类型
	 * @param value
	 * @return
	 */
	protected boolean isNotNeedSerial(Object value) {
		Class<?> clazz = value.getClass();
		if (clazz.isArray()) {
			clazz = clazz.getComponentType();
		}
		boolean isSerial = (clazz == String.class) || (clazz.isPrimitive());
		return isSerial;
	}
	
	/**
	 * 克隆或者深拷贝对象
	 * @param src 需要克隆或者深拷贝的对象
	 * @return DeepOrCloneCopyResult
	 * @author yinlei
	 * date 2012-1-15 下午9:27:48
	 */
	protected DeepOrCloneCopyResult deepOrCloneCopy(Object src) {
		if(src == null) {
			return new DeepOrCloneCopyResult((src instanceof Serializable), null);
		}
		boolean isSerializable = (src instanceof Serializable);
		Object dist = null;
		if(isSerializable) {
			try {
				dist = NativeSerializerUtils.serialize(src);
			} catch (Exception e) {
				isSerializable = false;
			}
		}
		if(dist == null) {
			if (src instanceof Cloneable) {
				try {
					dist = CloneTransformer.getInstance().transform(src);
				} catch (Exception e) {
					throw new UnsupportedOperationException(String.format("Class[%s] not supported Object Caching.", src.getClass().getName()), e);
				}
			} else {
				throw new UnsupportedOperationException(String.format("Class[%s] not supported Object Caching.", src.getClass().getName()));
			}
		}
		return new DeepOrCloneCopyResult(isSerializable, dist);
	}
	
	/**
	 * 克隆或者深拷贝对象结果封装
	 * @author yinlei
	 * date 2012-1-15 下午9:30:04
	 */
	class DeepOrCloneCopyResult {
		private boolean serializable;
		private Object value;
		
		DeepOrCloneCopyResult(boolean serializable, Object value) {
			this.serializable = serializable;
			this.value = value;
		}
		
		/**
		 * 对象是否可序列化
		 * @author yinlei
		 * date 2012-1-15 下午9:19:22
		 */
		public boolean serializable() {
			return this.serializable;
		}
		
		/**
		 * 获得克隆或者深拷贝对象的值
		 * @author yinlei
		 * date 2012-1-15 下午9:29:14
		 */
		public Object getValue() {
			return this.value;
		}
	}
}
