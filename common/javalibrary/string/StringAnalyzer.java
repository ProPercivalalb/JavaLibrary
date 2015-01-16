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
	
	public static Hashtable<Character, LetterCount> countLetters(String text) {
		Hashtable<Character, LetterCount> letters = new Hashtable<Character, LetterCount>();
		
  		for(char cha : text.toCharArray()) {
  			
  			if(!letters.keySet().contains(cha))
  	  			letters.put(cha, new LetterCount(cha, 0));
  			
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

		orginalStr = StringTransformer.removeEverythingButLetters(orginalStr).toUpperCase();
		
		//If there are enough characters analyze the string using a recursing method
		if(orginalStr.length() >= minimumLength) {
			char[] characters = orginalStr.toCharArray();

			for(int length = minimumLength; length <= maximumLength; ++length) {
				for(int k = 0; k < (orginalStr.length() - length + 1); k++) {
					String s = new String(characters, k, length);
					
					if(table.containsKey(s)) {
						table.put(s, table.get(s) + 1);
					}
					else {
						table.put(s, 1);
					}
				}
			}
		}
		
		return table;
	}
}
