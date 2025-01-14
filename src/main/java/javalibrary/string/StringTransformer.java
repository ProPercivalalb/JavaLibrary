package javalibrary.string;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	
    public static String getEveryNthChar(CharSequence text, int start, int n) {
    	String accumulator = "";
        for(int i = 0; i < text.length(); ++i) {
            if((i % n) == start) {
                accumulator += text.charAt(i);
            }
        }
        return accumulator;
    }
    
    public static String getEveryNthChar(char[] text, int start, int n) {
    	String accumulator = "";
        for(int i = 0; i < text.length; ++i) {
            if((i % n) == start) {
                accumulator += text[i];
            }
        }
        return accumulator;
    }
 
    
    public static String getEveryNthBlock(String text, int blockSize, int start, int n) {
    	String accumulator = "";
        for(int i = 0; i < text.length() / blockSize; i++) {
            if((i % n) == start) {
                accumulator += text.substring(i * blockSize, i * blockSize + blockSize);
            }
        }
        return accumulator;
    }
    
    public static String rotateRight(String text, int n) {
		int cuttingPoint = text.length() - (n % text.length());
	    return text.substring(cuttingPoint, text.length()) + text.substring(0, cuttingPoint);
	}
    
    public static <T> String joinWith(List<T> strings, String joiner) {
    	String total = "";
		for(int i = 0; i < strings.size(); ++i)
			total += (T)strings.get(i) + (i == strings.size() - 1 ? "" : joiner);
		
		return total;
    }
    
	public static String joinWith(int[] ints, String joiner) {
		String total = "";
		for(int i = 0; i < ints.length; ++i)
			total += ints[i] + "" + (i == ints.length - 1 ? "" : joiner);
		
		return total;
	}
	
	
	private static String joinWithBase(double[] ints, String joiner, boolean clean) {
		String total = "";
		for(int i = 0; i < ints.length; ++i)
			total += (clean ? ValueFormat.getNumber(ints[i]) : ints[i]) + "" + (i == ints.length - 1 ? "" : joiner);
		
		return total;
	}
	
	public static String joinWith(double[] ints, String joiner) {
		return joinWithBase(ints, joiner, false);
	}
	
	public static String joinWithClean(double[] ints, String joiner) {
		return joinWithBase(ints, joiner, true);
	}
    
    public static String repeat(String repeat, int times) {
    	String total = "";
    	for(int i = 0; i < times; i++)
    		total += repeat;
    	return total;
    }
    
    public static String[] splitInto(String text, int size) {
    	String[] list = new String[(int)Math.ceil(text.length() / (double)size)];
    	for(int i = 0; i < text.length(); i += size)
    		list[i / size] = text.substring(i, Math.min(i + size, text.length()));
    	return list;
    }
    
    public static int countDigitChars(String text) {
    	text = text.replaceAll("[^0-9]", "");
    	return text.length();
    }
    
    public static int countLetterChars(String text) {
    	text = text.replaceAll("[^a-zA-Z]+", "");
    	return text.length();
    }
    
    public static int countSpacesChars(String text) {
    	text = text.replaceAll("[^\\s+]+", "");
    	return text.length();
    }
    
	public static int countOtherChars(String text) {
		text = text.replaceAll("[0-9a-zA-Z\\s+]", "");
    	return text.length();
	}
	
	public static Set<Character> getUniqueCharSet(String inputText) {
		char[] chars = inputText.toCharArray();
		
		int end = chars.length;
		Set<Character> set = new HashSet<Character>();

		for(int i = 0; i < end; i++)
			set.add(chars[i]);
		
		return set;
	}
}
