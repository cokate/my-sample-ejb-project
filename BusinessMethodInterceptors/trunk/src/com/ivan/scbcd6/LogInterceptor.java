package com.ivan.scbcd6;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Interceptor
public class LogInterceptor {
	public LogInterceptor() {
		System.out.println("LogInterceptor - Constructor");
	}

	@AroundInvoke
	public Object logMethodEntryExit(InvocationContext inInvocationContext)
			throws Exception {
		System.out.println(" LogInterceptor - Entering method: "
				+ inInvocationContext.getMethod().getName());
		/* Invoke the intercepted method on the EJB and save the result. */
		Object theResult = inInvocationContext.proceed();
		System.out.println(" LogInterceptor - result: "
				+ theResult);
		System.out.println(" LogInterceptor - Exiting method: "
				+ inInvocationContext.getMethod().getName());
		/* Return the result from the intercepted method. */
		return theResult;
	}
}