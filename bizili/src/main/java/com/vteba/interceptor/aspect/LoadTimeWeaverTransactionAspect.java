package com.vteba.interceptor.aspect;

import org.aspectj.lang.annotation.AdviceName;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class LoadTimeWeaverTransactionAspect {
	
	@AdviceName("transactionAdvice")
	@Before(value="execution(* com.skmbw.*.service.impl.*.*(..))")
	public void executeTransactionMethod(){
		
	}
}
