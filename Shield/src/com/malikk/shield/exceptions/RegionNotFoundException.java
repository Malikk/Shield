package us.twoguys.shield.exceptions;

/**
 * This exception is thrown when a valid ShieldRegion could not be found for the desired name and/or plugin.
 * <p>
 * In the catch block, you should set the default action to take when this occurs.
 * @author Malikk
 *
 */
public class RegionNotFoundException extends Exception{

	private static final long serialVersionUID = -5506011525478825730L;

	public RegionNotFoundException(){
		super();
	}
}
