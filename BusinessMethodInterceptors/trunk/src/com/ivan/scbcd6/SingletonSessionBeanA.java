package com.ivan.scbcd6;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.interceptor.Interceptors;

@Singleton
@LocalBean
@Interceptors(LogInterceptor.class)
public class SingletonSessionBeanA {
	private final static String BEAN_NAME = "SingletonSessionBeanA";
	/* Instance variable(s): */
	private String mStoredMessage = "[no message set]";

	@PostConstruct
	public void intialize() {
		System.out.println("*** " + BEAN_NAME + " - Initialized");
	}

	@PreDestroy
	public void cleanUp() {
		System.out.println("*** " + BEAN_NAME + " - Destroyed");
	}

	public String retrieveMessage() {
		Date theCurrentTime = new Date();
		return "Message from " + BEAN_NAME + " - " + mStoredMessage + " "
				+ theCurrentTime;
	}

	public void storeMessage(final String inStoredMessage) {
		mStoredMessage = inStoredMessage;
	}
}
