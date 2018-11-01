package javalibrary.lib;

/**
 * @author Alex Barter
 * @since 29 Oct 2013
 */
public class Alphabet {

	private static final String alphabetLowerCase = "abcdefghijklmnopqrstuvwxyz";
	private static final String alphabetUpperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	/**
	 * A method to get the English alphabet, for length, passing etc
	 * @return The English alphabet all in lower case
	 */
	public static String getLowerCase() {
		return alphabetLowerCase;
	}
	
	/**
	 * A method to get the English alphabet, for length, passing etc
	 * @return The English alphabet all in upper case
	 */
	public static String getUpperCase() {
		return alphabetUpperCase;
	}
	
	/**
	 * Gets all the characters of the English alphabet in their lower and upper form
	 * @return A string of {@link #alphabetLowerCase} joined with {@link #alphabetUpperCase}
	 */
	public static String getAllCharacters() {
		return getLowerCase() + getUpperCase();
	}
}
