package com.ivan.scbcd6;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;

/**
 * This class implements the second singleton session bean of this example
 * program.
 */

@Singleton
@LocalBean
public class SingletonSessionBeanB {
	private final static String BEAN_NAME = "SingletonSessionBeanB";

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
		return "Message from " + BEAN_NAME + " - " + theCurrentTime;
	}

}
