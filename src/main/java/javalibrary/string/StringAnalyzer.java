package javalibrary.string;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Alex Barter
 * @since 23 Oct 2013
 */
public class StringAnalyzer {

	public static ArrayList<LetterCount> sizeOrder(Hashtable<Character, LetterCount> letters) {
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
	
	public static ArrayList<LetterCount> countLettersInSizeOrder(String text) {
		return sizeOrder(countLetters(text));
	}
	
	public static ArrayList<LetterCount> alphabeticOrder(Hashtable<Character, LetterCount> letters) {
	
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
	
	public static ArrayList<LetterCount> countLettersInAlphabeticOrder(String text) {
		return alphabeticOrder(countLetters(text));
	}
	
	public static Hashtable<Character, LetterCount> countLetters(String text) {
		Hashtable<Character, LetterCount> letters = new Hashtable<Character, LetterCount>();
		
		for(char ch = 'A'; ch <= 'Z'; ch++)
  	  		letters.put(ch, new LetterCount(ch, 0));
		
  		for(char cha : text.toCharArray()) {
  			if(letters.containsKey(cha))
  				letters.get(cha).increment();
  		}
  		
  		return letters;
	}
	
	public static class SortStringInteger implements Comparator<String> {
		
		private Map<String, Integer> data;
		
	    public SortStringInteger(Map<String, Integer> data) {
	        this.data = data;
	    }
	    
	    @Override
	    public int compare(String key1, String key2) {
	    	Integer e1 = this.data.get(key1);
	    	Integer e2 = this.data.get(key2);
	    	int comp = e1.compareTo(e2);
	        return comp == 0 ? String.CASE_INSENSITIVE_ORDER.compare(key1, key2) : comp;
	    }
	}
	
	// Todo
	public static List<String> orderByCount(Map<String, Integer> map) {
		List<String> sortedItems = new ArrayList<String>();
		
  		while(map.size() > 0) {
  			int largest = Integer.MIN_VALUE;
  			String largestLetter = "";
  			
  			Iterator<String> ite = map.keySet().iterator();
  			
  			while(ite.hasNext()) {
  				String cha = ite.next();
  				if(largest < map.get(cha)) {
  					largestLetter = cha;
  					largest = map.get(cha);
  				}
  				else if(largest == map.get(cha) && String.CASE_INSENSITIVE_ORDER.compare(largestLetter, cha) > 0) {
  					largestLetter = cha;
  					largest = map.get(cha);
  				}
  			}
  			
  			sortedItems.add(largestLetter);
  			map.remove(largestLetter);	
  		}
  		
  		return sortedItems;
	}
	
	public static Map<String, Integer> getEmbeddedStrings(CharSequence text, int minLength, int maxLength) {
		return getEmbeddedStrings(text, minLength, maxLength, true);
	}
	
	public static Map<String, Integer> getEmbeddedStrings(CharSequence text, int minLength, int maxLength, boolean overlap) {
		TreeMap<String, Integer> map = new TreeMap<String, Integer>();

		if(text.length() >= minLength) {
			for(int length = minLength; length <= maxLength; ++length) {
				for(int k = 0; k < (text.length() - length + 1); k += (overlap ? 1 : length)) {
					String s = text.subSequence(k, k + length).toString();
					map.put(s, map.containsKey(s) ? map.get(s) + 1 : 1);
				}
			}
		}
		
		return map;
	}
	
	public static Map<Character, Integer> getCharacterCount(CharSequence text) {
        HashMap<Character, Integer> map = new HashMap<Character, Integer>();
        for(int k = 0; k < text.length(); k++) {
            map.put(text.charAt(k), map.getOrDefault(text.charAt(k), 0) + 1);
        }

        return map;
    }
	
	//A more direct and optimised character counter
	public static Map<Character, Integer> getCharacterCount(char[] text) {
		HashMap<Character, Integer> map = new HashMap<Character, Integer>();
		for(int k = 0; k < text.length; k++)
			map.put(text[k], map.containsKey(text[k]) ? map.get(text[k]) + 1 : 1);

		return map;
	}
	
	public static Map<Byte, Integer> getCharacterCount(byte[] text) {
		Map<Byte, Integer> map = new HashMap<Byte, Integer>();
		for(int k = 0; k < text.length; k++)
			map.put(text[k], map.containsKey(text[k]) ? map.get(text[k]) + 1 : 1);

		return map;
	}
	
	//Returns list of ordered character count in text from largest to smallest, counts of equal value are placed in ASCII value order
	public static List<Character> getOrderedCharacterCount(Map<Character, Integer> map) {
		List<Character> sorted = new ArrayList<Character>(map.keySet());
		Collections.sort(sorted, new SortCharacter(map));			
		return sorted;
	}
	
	public static List<Character> getOrderedCharacterCount(char[] text) {			
		return getOrderedCharacterCount(getCharacterCount(text));
	}
	
	public static List<Character> getOrderedCharacterCount(CharSequence text) {          
        return getOrderedCharacterCount(getCharacterCount(text));
    }
	
	public static class SortCharacter implements Comparator<Character> {
		
		private Map<Character, Integer> data;
		
	    public SortCharacter(Map<Character, Integer> data) {
	        this.data = data;
	    }
	    
	    @Override
	    public int compare(Character x, Character y) {
	    	int comp = this.data.get(y).compareTo(this.data.get(x));
	        return comp == 0 ? Character.compare(x, y) : comp;
	    }
	}
	
}
