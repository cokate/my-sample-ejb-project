package com.ivan.scbcd6;

import javax.ejb.Local;

@Local
public interface StatelessSession2Local {
	/**
	* Creates a greeting to the person with the supplied name.
	*
	* @param inName Name of person to greet.
	* @return Greeting.
	*/
	public String greeting(final String inName);

}
