package com.ivan.scbcd6;

import javax.interceptor.AroundInvoke;
import javax.interceptor.AroundTimeout;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Interceptor
public class LogInterceptor extends LogInterceptorSuperClass {
	public LogInterceptor() {
		System.out.println("LogInterceptor - Constructor");
	}

	@AroundTimeout
	public Object logTimeout(InvocationContext inInvocationContext) throws Exception {
		System.out.println(" LogInterceptor - Entering timeout: " + inInvocationContext.getMethod().getName());
		Object theResult = inInvocationContext.proceed();
		System.out.println(" LogInterceptor - Exiting timeout: " + inInvocationContext.getMethod().getName());
		return theResult;
	}

	@AroundInvoke
	public Object logMethodEntryExit(InvocationContext inInvocationContext) throws Exception {
		System.out.println(" LogInterceptor - Entering method: " + inInvocationContext.getMethod().getName());
		/* Invoke the intercepted method on the EJB and save the result. */
		Object theResult = inInvocationContext.proceed();
		System.out.println(" LogInterceptor - result: " + theResult);
		System.out.println(" LogInterceptor - Exiting method: " + inInvocationContext.getMethod().getName());
		/* Return the result from the intercepted method. */
		return theResult;
	}
}