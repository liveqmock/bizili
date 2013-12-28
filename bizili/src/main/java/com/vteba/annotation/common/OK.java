package com.vteba.annotation.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标示方法经过了测试，可用于生产环境。
 * @author yinlei
 * date 2013-6-10 下午2:16:09
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.SOURCE)
public @interface OK {

}
