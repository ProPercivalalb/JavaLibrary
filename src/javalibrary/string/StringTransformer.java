package javalibrary.string;

import javalibrary.language.ILanguage;
import javalibrary.lib.Alphabet;

/**
 * @author Alex Barter
 * @since 29 Oct 2013
 */
public class StringTransformer {

	//Convenience methods to make the code shorter in you main file
	public static String removeLetters(String target) { return removeGivenCharacters(target, Alphabet.getAllCharacters()); }
	public static String removeNumbers(String target) { return removeGivenCharacters(target, "0123456789"); }
	public static String removeSpaces(String target) { return removeGivenCharacters(target, " "); }
	
	/**
	 * Removes all the characters wanted from the given string
	 * @param target The string to strip certain characters from
	 * @param chars The characters that are being removed from the given string 
	 * @return A new string with no characters from the given string
	 */
	public static String removeGivenCharacters(String target, String chars) {
		//Creates an array in the order they were in the target string
		char[] charArray = target.toCharArray();
		
		StringBuilder newString = new StringBuilder();
		//Runs through all the characters from the array
		for(char ch : charArray) {
			//If the chars to remove string contains the current character iteration, continue to the next iteration
			if(chars.contains(String.valueOf(ch)))
				continue;
			newString.append(ch);
		}
		
		return newString.toString();
	}
	
	
	
	//Convenience methods to make the code shorter in you main file
	public static String removeEverythingButLetters(String target) { return removeEverythingButGivenCharacters(target, Alphabet.getAllCharacters()); }
	public static String removeEverythingButLettersAndSpaces(String target) { return removeEverythingButGivenCharacters(target, Alphabet.getAllCharacters() + " "); }
	public static String removeEverythingButNumbers(String target) { return removeEverythingButGivenCharacters(target, "0123456789"); }
	public static String removeEverythingButSpaces(String target) { return removeEverythingButGivenCharacters(target, " "); }
	
	
	/**
	 * Removes all characters accept the ones from the given string
	 * @param target The string to strip certain characters from
	 * @param chars The characters that are being preserved in the given string 
	 * @return A new string with just characters from the given string
	 */
	public static String removeEverythingButGivenCharacters(String target, String chars) {
		//Creates an array in the order they were in the target string
		char[] charArray = target.toCharArray();
		
		StringBuilder newString = new StringBuilder();
		//Runs through all the characters from the array
		for(char ch : charArray) {
			//If the chars to keep string does not contains the current character iteration, continue to the next iteration
			if(!chars.contains(String.valueOf(ch)))
				continue;
			newString.append(ch);
		}
		
		return newString.toString();
	}
	
	/**
	 * Reverses the string, last character becomes first character etc
	 * @param target The string to be reversed
	 * @return The reverse of the given string
	 */
	public static String reverseString(String target) {
		//Creates an array in the order they were in the target string
		char[] charArray = target.toCharArray();
		
		StringBuilder newString = new StringBuilder();
		//Runs through all the characters from the array
		for(char ch : charArray) {
			//Inserts the character at the start of the String
			newString.insert(0, ch);
		}
		
		return newString.toString();
	}
	
    public static String getEveryNthChar(String text, int start, int n) {
    	String accumulator = "";
        for(int i = 0; i < text.length(); ++i) {
            if((i % n) == start) {
                accumulator += text.charAt(i);
            }
        }
        return accumulator;
    }
    
    public static String rotateRight(String text, int n, ILanguage language) {
		int cuttingPoint = text.length() - (n % text.length());
	    return text.substring(cuttingPoint, text.length()) + text.substring(0, cuttingPoint);
	}
}
