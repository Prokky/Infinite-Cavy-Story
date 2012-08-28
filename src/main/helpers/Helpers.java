package main.helpers;

/**
 * Helper class, containing useful functions
 * 
 * @author prokk
 * 
 */
public class Helpers {

	/**
	 * Set first letter of string to capital
	 * 
	 * @param input
	 *            string
	 * @return string with capital first letter
	 */
	public static String capitalizeString(String input) {
		return input.substring(0, 1).toUpperCase() + input.substring(1);
	}
}
