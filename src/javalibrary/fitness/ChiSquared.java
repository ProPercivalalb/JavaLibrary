package javalibrary.fitness;

import java.util.TreeMap;

import javalibrary.language.ILanguage;
import javalibrary.string.StringAnalyzer;
import javalibrary.util.ArrayUtil;

/**
 * @author Alex Barter (10AS)
 */
public class ChiSquared {

	public static double calculate(byte[] text, ILanguage language) {
		return calculate(ArrayUtil.convertCharType(text), language);
	}
	
	public static double calculate(char[] text, ILanguage language) {
		TreeMap<String, Integer> letters = StringAnalyzer.getEmbeddedStrings(text, 1, 1);
		
		double total = 0.0D;
		
		for(String letter : letters.keySet()) {
			double count = letters.get(letter);
			double expectedCount = language.getFrequencyOfLetter(letter.charAt(0)) * text.length / 100;
			
			double sum = Math.pow(count - expectedCount, 2) / expectedCount;
			total += sum;
		}
		
		return total;
	}
}
