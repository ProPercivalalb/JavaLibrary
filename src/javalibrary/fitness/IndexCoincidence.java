package javalibrary.fitness;

import java.util.Hashtable;

import javalibrary.language.ILanguage;
import javalibrary.string.LetterCount;
import javalibrary.string.StringAnalyzer;

/**
 * @author Alex Barter (10AS)
 */
public class IndexCoincidence {

	public static double calculate(String text, ILanguage language) {
		Hashtable<Character, LetterCount> test = StringAnalyzer.countLetters(text);
		int length = text.length();
		
		
		double total = 0.0D;
		
		for(char ch : test.keySet()) {
			double count = test.get(ch).count;
			total += count * (count - 1);
		}

		return total / (0.0385D * length * (length - 1));
	}
}
