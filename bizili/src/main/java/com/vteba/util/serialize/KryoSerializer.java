package com.vteba.util.serialize;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

import org.objenesis.strategy.StdInstantiatorStrategy;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

/**
 * 基于kryo的序列化转换器。
 * @author yinlei
 * date 2013-4-6 下午2:50:09
 */
public class KryoSerializer {
	private ThreadLocal<Kryo> serializeThreadLocal = new ThreadLocal<Kryo>();

	private static KryoSerializer instance = new KryoSerializer();

	public static KryoSerializer getInstance() {
		return instance;
	}

	private KryoSerializer() {

	}

	/**
	 * 获得当前线程的Kryo
	 * @return Kryo实例
	 * @author yinlei
	 * date 2013-4-6 下午2:50:52
	 */
	public Kryo getSerializer() {
		Kryo kryo = serializeThreadLocal.get();

		if (kryo == null) {
			kryo = new Kryo();
			kryo.setRegistrationRequired(true);
			kryo.setReferences(true);
			kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());

			kryo.register(Class.class);
			
			kryo.register(HashMap.class);
			kryo.register(TreeMap.class);
			kryo.register(LinkedHashMap.class);
			kryo.register(ConcurrentHashMap.class);
			
			kryo.register(HashSet.class);
			kryo.register(TreeSet.class);
			kryo.register(LinkedHashSet.class);
			kryo.register(CopyOnWriteArraySet.class);
			
			kryo.register(ArrayList.class);
			kryo.register(LinkedList.class);
			kryo.register(CopyOnWriteArrayList.class);
			
			kryo.register(BigInteger.class);
			kryo.register(BigDecimal.class);
			
			kryo.register(Date.class);
			kryo.register(java.sql.Date.class);

			serializeThreadLocal.set(kryo);
		}
		return kryo;
	}

	/**
	 * 将对象序列化为byte[]，对象要注册
	 * @param t 要转换的对象
	 * @return 字节数组
	 * @author yinlei
	 * date 2013-4-6 下午2:51:28
	 */
	public byte[] toBytes(Object t) {
		Kryo converter = getSerializer();
		Output output = new Output(1024, 4 * 1024);
		converter.writeObject(output, t);

		return output.toBytes();
	}

	/**
	 * 将字节数组反序列化为对象，对象要注册
	 * @param bytes 被转换的字节数组
	 * @param clazz 要转换的对象类型
	 * @return 要转换的对象实例
	 * @author yinlei
	 * date 2013-4-6 下午2:52:11
	 */
	public <T> T fromBytes(byte[] bytes, Class<T> clazz) {
		Kryo converter = getSerializer();
		Input input = new Input(bytes);
		return converter.readObject(input, clazz);
	}

	/**
	 * 将对象序列化为字节数组，对象无需注册，性能低
	 * @param object 要被序列化的对象
	 * @return 字节数组
	 * @author yinlei
	 * date 2013-4-7 下午9:52:43
	 */
	public byte[] serialize(Object object) {
		if (object == null) {
			throw new NullPointerException("Can't serialize null Object!");
		}
		Kryo kryo = new Kryo();
		Output out = new Output(2 * 1024, 128 * 1024);
		kryo.writeClassAndObject(out, object);
		return out.toBytes();
	}

	/**
	 * 将字节数组反序列化为对象，对象无需注册，性能低
	 * @param in 要反序列化的字节数组
	 * @return 反序列化后的对象
	 * @author yinlei
	 * date 2013-4-7 下午9:54:00
	 */
	public Object deserialize(byte[] in) {
		Object rv = null;
		Kryo kryo = new Kryo();
		Input input = new Input(in);
		rv = kryo.readClassAndObject(input);
		return rv;
	}
}
