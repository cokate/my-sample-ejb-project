package com.ivan.scbcd6.client;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.ivan.scbcd6.entity.MyMessage;

public class MessageDrivenBeanSEClient {
	/* Constant(s): */
	private final static String JMS_CONNECTIONFACTORY_JNDI = "jms/QueueConnectionFactory";
	private final static String JMS_JMS_QUEUEDESTINATION_JNDI = "jms/QueueDestination";
	/* Instance variable(s): */
	/** Connection factory for queue. */
	private ConnectionFactory mQueueConnectionFactory;
	/** Queue destination. */
	private Queue mQueueDestination;
	/** MyMessage number counter. */
	private AtomicLong mMessageNumber = new AtomicLong(0);

	/**
	 * Looks up the JMS resources required by the client to send JMS messages.
	 * 
	 * @throws NamingException
	 *             If error occurs during lookup.
	 */
	private void lookupJmsResources() throws NamingException {
		InitialContext theContext = new InitialContext();
		System.out.println("*** Starting JMS Resource Lookup...");
		mQueueConnectionFactory = (ConnectionFactory) theContext.lookup(JMS_CONNECTIONFACTORY_JNDI);
		mQueueDestination = (Queue) theContext.lookup(JMS_JMS_QUEUEDESTINATION_JNDI);
		System.out.println(" JMS Resource Loopup Finished.");
	}

	/**
	 * Sends a JMS message with the next message number and increments the
	 * message counter.
	 * 
	 * @throws JMSException
	 *             If error occurred sending message.
	 */
	private void sendJmsMessage() throws JMSException {
		MessageProducer theJMSMessageProducer = null;
		Connection theJMSConnection = null;
		try {
			/* Retrieve a JMS connection from the queue connection factory. */
			theJMSConnection = mQueueConnectionFactory.createConnection();
			/*
			 * Create the JMS session; not transacted and with auto-
			 * acknowledge.
			 */
			Session theJMSSession = theJMSConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			/* Create a JMS message producer for the queue destination. */
			theJMSMessageProducer = theJMSSession.createProducer(mQueueDestination);
			/* Create the object to be sent in the message created above. */
			MyMessage theObjectToSend = new MyMessage();
			theObjectToSend.setMessageNumber(mMessageNumber.incrementAndGet());
			theObjectToSend.setMessageString("Hello Message Driven Beans");
			theObjectToSend.setMessageTime(new Date());
			/* Create message used to send a Java object. */
			ObjectMessage theJmsObjectMessage = theJMSSession.createObjectMessage();
			theJmsObjectMessage.setObject(theObjectToSend);
			/* Send the message. */
			theJMSMessageProducer.send(theJmsObjectMessage);
		} finally {
			closeJmsResources(theJMSMessageProducer, theJMSConnection);
		}
	}

	/**
	 * Closes the supplied JMS resources if they are not null. If a supplied
	 * resource is null, then do nothing.
	 * 
	 * @param inJMSMessageProducer
	 *            JMS message producer to close.
	 * @param inJMSConnection
	 *            JMS connection to close.
	 */
	private void closeJmsResources(MessageProducer inJMSMessageProducer, Connection inJMSConnection) {
		if (inJMSMessageProducer != null) {
			try {
				inJMSMessageProducer.close();
			} catch (JMSException theException) {
				// Ignore exceptions.
			}
		}
		if (inJMSConnection != null) {
			try {
				inJMSConnection.close();
			} catch (JMSException theException) {
				// Ignore exceptions.
			}
		}
	}

	/**
	 * Main entry point of the Java SE message driven bean example program.
	 * 
	 * @param args
	 *            Command line arguments.
	 */
	public static void main(String[] args) {
		MessageDrivenBeanSEClient theClient = new MessageDrivenBeanSEClient();
		try {
			theClient.lookupJmsResources();
			for (int i = 0; i < 10; i++) {
				theClient.sendJmsMessage();
				System.out.println("### Sent message: " + (i + 1));
			}
		} catch (Exception theException) {
			theException.printStackTrace();
		}
		System.out.println("*** Java SE JMS Client finished.");
		System.exit(0);
	}

}
