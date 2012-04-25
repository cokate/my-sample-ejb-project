package provaosgi;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import provaosgi.internal.MyThread;

public class Activator implements BundleActivator {

	private MyThread myThread;

	public void start(BundleContext context) throws Exception {
		System.out.println("Starting prova-osgi");
		myThread = new MyThread();
		myThread.start();
	}

	
	public void stop(BundleContext context) throws Exception {
		System.out.println("Stopping prova-osgi");
		myThread.stopThread();
		myThread.join();
		System.out.println("Stopped prova-osgi");
	}

}
