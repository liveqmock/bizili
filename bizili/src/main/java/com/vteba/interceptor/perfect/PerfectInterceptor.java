package com.vteba.interceptor.perfect;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Named;

import org.perf4j.StopWatch;
import org.perf4j.slf4j.Slf4JStopWatch;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;

/**
 * Perf4j Interceptor。用来分析性能日志拦截器。
 * @author yinlei
 * date 2012-10-6 下午6:17:21
 */
@Named
public class PerfectInterceptor implements MethodBeforeAdvice, AfterReturningAdvice {
	/**
	 * grep perf4j publication.log > a
	 * java -jar perf4j-0.9.10.jar -g g.html a
	 */
	private Map<String, StopWatch> watches = new HashMap<String, StopWatch>();
	
	@Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
		String completeMethodName = getCompleteMethodName(target, method);
	       
        // 创建性能日志记录器
        StopWatch stopWatch;
        if (watches.containsKey(completeMethodName)) {
            stopWatch = watches.get(completeMethodName);
            stopWatch.start();
        } else {
            stopWatch = new Slf4JStopWatch(completeMethodName, Arrays.toString(args));
            watches.put(completeMethodName, stopWatch);
        }

    }
	
	@Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        String completeMethodName = getCompleteMethodName(target, method);

        // 记录性能
        if (watches.containsKey(completeMethodName)) {
            StopWatch stopWatch = watches.get(completeMethodName);
            stopWatch.stop();
        }      
    }

    /**
     * 根据目标对象与方法获取方法完整名称.
     * @param target 目标对象
     * @param method 方法
     * @return 方法完整名称
     */
    private String getCompleteMethodName(Object target, Method method) {
        String className = "";
        if (target != null) {
            className = target.toString();
            int loc = className.indexOf("@");
            if (loc >= 0) {
                className = className.substring(0, loc);
            }
        }
        return className + "." + method.getName();
    }

}
