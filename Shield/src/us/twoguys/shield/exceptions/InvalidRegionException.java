package us.twoguys.shield.exceptions;

/**
 * This exception is thrown when the ShieldRegion object passed in cannot find a region by that name and plugin.
 * <p>
 * In the catch block, you should set the default action to take when this occurs.
 * 
 * @author Malikk
 */
public class InvalidRegionException extends Exception{

	private static final long serialVersionUID = 1755159885247872128L;

	public InvalidRegionException(){
		super();
	}
}
