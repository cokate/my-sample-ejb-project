package com.ivan.scbcd6;

import javax.interceptor.InvocationContext;

public class MyDefaultInterceptor {
	public Object aroundInvoke(InvocationContext inInvocationContext) throws Exception {
		System.out.println(" MyDefaultInterceptor intercepting: " + inInvocationContext.getTarget().getClass().getSimpleName()
				+ "." + inInvocationContext.getMethod().getName());
		return inInvocationContext.proceed();
	}
}
