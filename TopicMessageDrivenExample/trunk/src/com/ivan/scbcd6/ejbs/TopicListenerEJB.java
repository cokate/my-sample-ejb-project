package com.ivan.scbcd6.ejbs;

import java.util.concurrent.atomic.AtomicInteger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import com.ivan.scbcd6.entity.MyMessage;

/**
 * Message-Driven Bean implementation class for: TopicListenerEJB
 * 
 */
@MessageDriven(activationConfig = { @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic") }, mappedName = "jms/TopicDestination", name = "TopicListener1")
public class TopicListenerEJB implements MessageListener {
	/* Constant(s): */
	/* Class variable(s): */
	private static AtomicInteger mCurrentBeanNumber = new AtomicInteger(0);
	/* Instance variable(s): */
	private int mBeanNumber = mCurrentBeanNumber.incrementAndGet();

	/**
	 * Default constructor.
	 */
	public TopicListenerEJB() {
		System.out.println("*** TopicListenerEJB created: " + mBeanNumber);
	}

	/**
	 * @see MessageListener#onMessage(Message)
	 */
	@Override
	public void onMessage(Message inMessage) {
		System.out.println("*** Bean " + mBeanNumber + " received message: " + inMessage);
		extractMessagePayload(inMessage);
	}

	private void extractMessagePayload(Message inMessage) {
		/* Extract the message payload, if any. */
		if (inMessage instanceof ObjectMessage) {
			try {
				ObjectMessage theObjMsg = (ObjectMessage) inMessage;
				MyMessage theMsgPayload = (MyMessage) theObjMsg.getObject();
				System.out.println("Received message with number: " + theMsgPayload.getMessageNumber());
				System.out.println(" Message string: " + theMsgPayload.getMessageString());
				System.out.println(" Message time: " + theMsgPayload.getMessageTime());
			} catch (JMSException theException) {
				System.out.println("An error occurred retrieving message payload: " + theException);
			}
		}
	}

}
