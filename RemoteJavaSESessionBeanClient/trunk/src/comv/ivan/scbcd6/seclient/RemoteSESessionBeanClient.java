package comv.ivan.scbcd6.seclient;

import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.ivan.scbcd6.StatefulSession1Remote;

/**
 * Remote Java SE EJB client that uses JNDI lookup to obtain references to EJBs.
 * 
 * Note that the following JAR must be included on the client's class path:
 * GlassFish 3: $GLASSFISH_HOME/modules/gf-client.jar
 */

public class RemoteSESessionBeanClient {
	/* Constant(s): */
	private final static String REMOTE_EJB_JNDI = "java:global/RemoteSessionBean/StatefulSession1Bean";
	/* Instance variable(s): */
	/** Remotely accessed EJB. */
	private StatefulSession1Remote mRemoteSessionBean;

	/**
	 * Looks up the remote EJB using its JNDI name.
	 * 
	 * @throws NamingException
	 *             If error occurs during lookup.
	 */
	private void lookupEJB() throws NamingException {
		InitialContext theContext = new InitialContext();
		System.out.println("*** Starting Remote EJB Lookup...");
		mRemoteSessionBean = (StatefulSession1Remote) theContext.lookup(REMOTE_EJB_JNDI);
		System.out.println(" Remote EJB Lookup Finished.");
	}

	/**
	 * Invokes the remote EJB and outputs results.
	 */
	private void invokedRemoteEJB() {
		String theResponse = mRemoteSessionBean.greeting("Java SE");
		System.out.println("*** Response from the EJB: " + theResponse);
		/* Process a list to examine parameter passing semantics. */
		List<String> theList = new ArrayList<String>();
		theList.add("string 1");
		theList.add("string 2");
		theList.add("last string");
		mRemoteSessionBean.processList(theList);
		/* Output list after EJB invocation. */
		String theListStrings = "";
		for (String theString : theList) {
			theListStrings += theString + ", ";
		}
		System.out.println("*** List after having invoked EJB processList: [" + theListStrings + "]");
	}

	public static void main(String[] args) {
		RemoteSESessionBeanClient theClient = new RemoteSESessionBeanClient();
		try {
			theClient.lookupEJB();
			theClient.invokedRemoteEJB();
		} catch (Exception theException) {
			theException.printStackTrace();
		}
	}

}
