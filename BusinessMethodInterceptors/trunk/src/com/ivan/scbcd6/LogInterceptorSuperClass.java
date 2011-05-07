package com.ivan.scbcd6;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Interceptor
public class LogInterceptorSuperClass {
	@AroundInvoke
	public Object logSuper(InvocationContext inInvocationContext) throws Exception {
		System.out.println(" LogInterceptorSuperclass intercepting: "
				+ inInvocationContext.getTarget().getClass().getSimpleName() + "." + inInvocationContext.getMethod().getName());
		return inInvocationContext.proceed();
	}
}
