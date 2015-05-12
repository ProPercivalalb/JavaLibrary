package javalibrary.fitness;

import java.util.Hashtable;

import javalibrary.language.ILanguage;
import javalibrary.string.LetterCount;
import javalibrary.string.StringAnalyzer;

/**
 * @author Alex Barter (10AS)
 */
public class ChiSquared {

	public static double calculate(String text, ILanguage language) {
		Hashtable<Character, LetterCount> test = StringAnalyzer.countLetters(text);
		
		double total = 0.0D;
		
		for(char ch : test.keySet()) {
			double count = test.get(ch).count;
			double expectedCount = language.getFrequencyOfLetter(ch) * text.length() / 100;
			
			double sum = Math.pow(count - expectedCount, 2) / expectedCount;
			total += sum;
		}
		
		return total;
	}
}
