package com.vteba.annotation.json;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * struts2 json支持的注解。
 * @author yinlei
 * 2014-4-24 下午5:14:34
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Json {
	/**
	 * 要反序列化（从Json到对象）的目标类
	 * @return 反序列化的目标类
	 */
	Class<?> target() default Void.class;
	
	/**
	 * 是否序列化（从对象到Json），默认true
	 * @return
	 */
	boolean ser() default true;
	
	/**
	 * 是否反序列化（从Json到对象），如果为true，请设置name属性。默认false
	 * @return
	 */
	boolean deser() default false;
	
	/**
	 * 要序列化的对象，在action中的属性名
	 * @return
	 */
	String name() default "";
	
	/**
	 * 要排除不序列化的属性名。
	 * @return
	 */
	String[] exclude() default {};
}
