package us.twoguys.shield.exceptions;

/**
 * This exception is thrown when the flag name being checked is not valid. 
 * <p>
 * In the catch block, you should set the default action to take when this occurs.
 * @author Malikk
 */
public class InvalidFlagException extends Exception{

	private static final long serialVersionUID = 6511988686703659482L;

	public InvalidFlagException(){
		super();
	}
}
