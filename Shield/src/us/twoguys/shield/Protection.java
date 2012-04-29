package us.twoguys.shield;

/**
 * The main API
 * 
 */
public interface Protection {

	/**
	 * Checks if the protection plugin is enabled
	 * @return 
	 */
	public boolean isEnabled();

	/**
	 * 
	 * @return the name of of protection plugin
	 */
	public String getName();
	
}
