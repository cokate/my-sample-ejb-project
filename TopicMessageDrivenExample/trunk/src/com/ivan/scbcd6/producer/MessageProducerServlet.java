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
import javax.jms.Session;
import javax.jms.Topic;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ivan.scbcd6.entity.MyMessage;

/**
 * Servlet implementation class MessageProducerServlet
 */
@WebServlet(name = "MessageProducerServlet", urlPatterns = "/sendmsg.do")
public class MessageProducerServlet extends HttpServlet {
	/* Constant(s): */
	private static final long serialVersionUID = 1647640647915937983L;
	/* Instance variable(s): */
	/** Connection factory for topic. */
	@Resource(mappedName = "jms/TopicConnectionFactory")
	private ConnectionFactory mTopicConnectionFactory;
	/** Topic destination. */
	@Resource(mappedName = "jms/TopicDestination")
	private Topic mTopicDestination;
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
		sendJmsMessage();
		PrintWriter theResponseWriter = inResponse.getWriter();
		theResponseWriter.println("A message was sent at " + new Date());
	}

	private void sendJmsMessage() {
		MessageProducer theJMSMessageProducer = null;
		Connection theJMSConnection = null;
		try {
			/* Retrieve a JMS connection from the topic connection factory. */
			theJMSConnection = mTopicConnectionFactory.createConnection();
			/*
			 * Create the JMS session; not transacted and with auto-
			 * acknowledge.
			 */
			Session theJMSSession = theJMSConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			/* Create a JMS message producer for the topic destination. */
			theJMSMessageProducer = theJMSSession.createProducer(mTopicDestination);
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
		} catch (JMSException theException) {
			theException.printStackTrace();
		} finally {
			if (theJMSMessageProducer != null) {
				try {
					theJMSMessageProducer.close();
				} catch (JMSException theException) {
					// Ignore exceptions.
				}
			}
			if (theJMSConnection != null) {
				try {
					theJMSConnection.close();
				} catch (JMSException theException) {
					// Ignore exceptions.
				}
			}
		}
	}

}
