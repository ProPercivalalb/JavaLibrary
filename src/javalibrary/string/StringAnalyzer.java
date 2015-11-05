package javalibrary.string;

import java.util.ArrayList;
import java.util.Comparator;
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
	
	public static TreeMap<String, Integer> getEmbeddedStrings(String text, int minLength, int maxLength) {
		return getEmbeddedStrings(text, minLength, maxLength, true);
	}
	
	public static TreeMap<String, Integer> getEmbeddedStrings(String text, int minLength, int maxLength, boolean including) {
		TreeMap<String, Integer> map = new TreeMap<String, Integer>();

		if(text.length() >= minLength) {
			char[] characters = text.toCharArray();

			for(int length = minLength; length <= maxLength; ++length) {
				for(int k = 0; k < (text.length() - length + 1); k += (including ? 1 : length)) {
					String s = new String(characters, k, length);
					map.put(s, 1 + (map.containsKey(s) ? map.get(s) : 0));
				}
			}
		}
		
		return map;
	}
}
