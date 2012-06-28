package com.malikk.shield.exceptions;

/**
 * This exception is thrown only when both the flag and region are valid, but the flag is not set for the region.
 * <p>
 * In the catch block, you should set the default action to take when this occurs.
 * @author Malikk
 */
public class FlagNotFoundException extends Exception{

	private static final long serialVersionUID = -563104023059278705L;

	public FlagNotFoundException(){
		super();
	}
}
