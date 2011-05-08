package com.ivan.scbcd6.producer;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ivan.scbcd6.entity.MyMessage;

@WebServlet(name = "MessageProducerServlet", urlPatterns = "/sendmsg.do")
public class MessageProducerServlet extends HttpServlet {
	/* Constant(s): */
	private static final long serialVersionUID = -4364474814559146703L;
	/* Instance variable(s): */
	/** Connection factory for queue. */
	@Resource(mappedName = "jms/QueueConnectionFactory")
	private ConnectionFactory mQueueConnectionFactory;
	/** Queue destination. */
	@Resource(mappedName = "jms/QueueDestination")
	private Queue mQueueDestination;
	/** MyMessage number counter. */
	private AtomicLong mMessageNumber = new AtomicLong(0);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MessageProducerServlet() {
		super();
		System.out.println("*** MessageProducerServlet created");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest inRequest, HttpServletResponse inResponse) throws ServletException, IOException {
		PrintWriter theResponseWriter = inResponse.getWriter();
		try {
			sendJmsMessage();
			theResponseWriter.println("A message was sent at " + new Date());
		} catch (JMSException theException) {
			theResponseWriter.println("An error occurred sending message: " + theException);
		}
	}

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
}
