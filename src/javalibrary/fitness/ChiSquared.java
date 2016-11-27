package javalibrary.fitness;

import java.util.Map;
import java.util.TreeMap;

import javalibrary.language.ILanguage;
import javalibrary.string.StringAnalyzer;
import javalibrary.util.ArrayUtil;

/**
 * @author Alex Barter (10AS)
 */
public class ChiSquared {

	public static double calculate(byte[] text, ILanguage language) {
		Map<Byte, Integer> letters = StringAnalyzer.getCharacterCount(text);
		
		double total = 0.0D;
		
		for(char letter : language.getCharacterFrequency().keySet()) {
			double count = letters.containsKey((byte)letter) ? letters.get((byte)letter) : 0;
			double expectedCount = language.getFrequencyOfLetter(letter) * text.length / 100;
			
			double sum = Math.pow(count - expectedCount, 2) / expectedCount;
			total += sum;
		}
		
		return total;
	}
	
	public static double calculate(char[] text, ILanguage language) {
		Map<Character, Integer> letters = StringAnalyzer.getCharacterCount(text);
		
		double total = 0.0D;
		
		for(char letter : language.getCharacterFrequency().keySet()) {
			double count = letters.containsKey(letter) ? letters.get(letter) : 0;
			double expectedCount = language.getFrequencyOfLetter(letter) * text.length / 100;
			
			double sum = Math.pow(count - expectedCount, 2) / expectedCount;
			total += sum;
		}
		
		return total;
	}
}
