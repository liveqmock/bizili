/**
 * Copyright 2014 Cenobit Technologies Inc. http://cenobit.es/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */
package com.vteba.struts2.json.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
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
