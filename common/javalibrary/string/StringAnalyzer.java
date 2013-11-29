package javalibrary.string;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

import javalibrary.math.Bound;

/**
 * @author Alex Barter
 * @since 23 Oct 2013
 */
public class StringAnalyzer {

	public static ArrayList<LetterCount> countLettersInSizeOrder(String text) {
		Hashtable<Character, LetterCount> letters = countLetters(text);
		
		ArrayList<LetterCount> sortedItems = new ArrayList<LetterCount>();
		
  		while(letters.size() > 0) {
  			LetterCount largest = null;
  			char largestLetter = 0;
  			
  			Iterator<Character> ite = letters.keySet().iterator();
  			
  			while(ite.hasNext()) {
  				char cha = ite.next();
  				if(largest == null) {
  					largestLetter = cha;
  					largest = letters.get(cha);
  				}
  				else if(largest.count < letters.get(cha).count) {
  					largestLetter = cha;
  					largest = letters.get(cha);
  				}
  				else if(largest.count == letters.get(cha).count && largest.ch > letters.get(cha).ch) {
  					largestLetter = cha;
  					largest = letters.get(cha);
  				}
  			}
  			
  			sortedItems.add(largest);
  			letters.remove(largestLetter);	
  		}
  		
  		return sortedItems;
	}
	
	public static ArrayList<LetterCount> countLettersInAlphabeticOrder(String text) {
		Hashtable<Character, LetterCount> letters = countLetters(text);
	
		ArrayList<LetterCount> sortedItems = new ArrayList<LetterCount>();
  		
  		while(letters.size() > 0) {
  			LetterCount largest = null;
  			char largestLetter = 0;
  			
  			Iterator<Character> ite = letters.keySet().iterator();
  			
  			while(ite.hasNext()) {
  				char cha = ite.next();
  				if(largest == null) {
  					largestLetter = cha;
  					largest = letters.get(cha);
  				}
  				else if(largest.ch > letters.get(cha).ch) {
  					largestLetter = cha;
  					largest = letters.get(cha);
  				}
  			}
  			
  			sortedItems.add(largest);
  			letters.remove(largestLetter);	
  		}
  		
  		return sortedItems;
	}
	
	private static Hashtable<Character, LetterCount> countLetters(String text) {
		Hashtable<Character, LetterCount> letters = new Hashtable<Character, LetterCount>();
		
  		for(int i = 0; i < text.length(); ++i) {
  			//Converts the character to uppercase
  			char cha = Character.toUpperCase(text.charAt(i));
  			
  			//If it is not a letter continue the loop
  			if(!Character.isAlphabetic(cha))
  				continue;
  			
  			if(!letters.keySet().contains(cha))
  	  			letters.put(cha, new LetterCount(cha, 1));
  			
  			letters.get(cha).increment();
  		}
  		
  		return letters;
	}
	
	/**
	 * Analyzes the string and adds what it finds to the table that is returned
	 * @param orginalStr The string that is about to be analyzed
	 * @param minimumLength The minimum length of a string to add to the {@link Hashtable}
	 * @param maximumLength The maximum length of a string to add to the {@link Hashtable}
	 * @return The {@link Hashtable} that will contain all the different string variations
	 */
	public static Hashtable<String, Integer> analyzeLetterCombination(String orginalStr, int minimumLength, int maximumLength) {
		Hashtable<String, Integer> table = new Hashtable<String, Integer>();
		
		//Bounds the min and max length relative to 0 and the min value
		minimumLength = Bound.bound(minimumLength, 1, Integer.MAX_VALUE);
		maximumLength = Bound.bound(maximumLength, minimumLength, Integer.MAX_VALUE);
		
		//Removes all characters except letters
		orginalStr = StringTransformer.removeEverythingButLetters(orginalStr).toLowerCase();
		
		//If there are enough characters analyze the string using a recursing method
		if(orginalStr.length() >= minimumLength)
			analyzeLetterCombination(table, orginalStr, minimumLength, maximumLength, 0, minimumLength);
		
		return table;
	}
	
	/**
	 * This is a recursive method, do not call directly. Use {@link #analyzeLetterCombination(String, int, int)} to avoid errors
	 * @param table The {@link Hashtable} that will contain all the different string variations
	 * @param orginalStr The string that is about to be analyzed
	 * @param minimumLength The minimum length of a string to add to the {@link Hashtable}
	 * @param maximumLength The maximum length of a string to add to the {@link Hashtable}
	 * @param currentCount The start index of the next string
	 * @param currentLength The length of the next string
	 */
	private static void analyzeLetterCombination(Hashtable<String, Integer> table, String orginalStr, int minimumLength, int maximumLength, int currentCount, int currentLength) {
		//Gets the substring for this iteration
		String subString = orginalStr.substring(currentCount, currentCount + currentLength);
		currentLength += 1; //Plus one the the length of the next substring
		
		//Tries to add the substring to the table
		if(subString != null && subString.length() >= minimumLength) {
			if(table.keySet().contains(subString)) {
				table.put(subString, table.get(subString) + 1);
			}
			else {
				table.put(subString, 1);
			}
		}
		
		//If the length means in total it exceeds the end of the string OR
		//If the length is greater than 16 because we don't need to look at letter combinations greater than 16
		//THEN increase the base count, reset the length to the minimum length and check to see whether it is complete
		if(orginalStr.length() < currentCount + currentLength || currentLength > maximumLength) {
			currentCount += 1; //Plus one the the index of the of the next substring
			currentLength = minimumLength;
			
			//Checks to see whether the count is nearly the length of the original text and if so terminates it recursive method
			if(currentCount > orginalStr.length() - minimumLength)
				return;
		
		}
		
		//Runs another iteration with slightly different variables
		analyzeLetterCombination(table, orginalStr, minimumLength, maximumLength, currentCount, currentLength);
	}
	
}
