package javalibrary.cipher.wip;

import java.util.Hashtable;
import java.util.List;
import java.util.Random;

import javalibrary.cipher.VigenereAuto;
import javalibrary.language.ILanguage;
import javalibrary.string.LetterCount;
import javalibrary.string.StringAnalyzer;
import javalibrary.string.StringTransformer;

/**
 * @author Alex Barter (10AS)
 */
public class BifidAuto {

	private static Random rand = new Random();
	
	public static String tryDecode(String cipherText, ILanguage language) {
		rand.setSeed(System.currentTimeMillis());
		
		String plainText = "";
		cipherText = StringTransformer.removeEverythingButLetters(cipherText).toUpperCase();
		int period = findPeriod(cipherText, 0, 15);
		System.out.println("Period: " + period);
		String keySquare = lookForBestKeySquare(cipherText, period, language);
		return plainText;
	}
	
	public static String lookForBestKeySquare(String cipherText, int period, ILanguage language) {
		String parentKeySquare = "ABCDEFGHIKLMNOPQRSTUVWXYZ";
		String lastKeySquare = "";
		String bestFit = "";
		
	    int best = 0;
	    double smallestSum = -1;
		
		boolean running = true;
		while(running) {
			lastKeySquare = KeySquareManipulation.exchange2letters(parentKeySquare);
			String deciphered = Bifid.decode(cipherText, lastKeySquare, period);
			
			List<LetterCount> charFreqs = StringAnalyzer.countLettersInSizeOrder(deciphered);
            List<LetterCount> scaled = VigenereAuto.scale(deciphered, charFreqs, language);
            double currentSum = VigenereAuto.sumResidualsSquared(deciphered, scaled, language);
            if(smallestSum == -1)
                smallestSum = currentSum;
            else if(currentSum < smallestSum) {
            	parentKeySquare = lastKeySquare;
            	System.out.println(lastKeySquare);
            }
            else {
            	lastKeySquare = parentKeySquare;
            }
		}
		
		return bestFit;
	}
	
	public static int findPeriod(String cipherText, int minLength, int maxLength) {
		int step = minLength;
		double smallestSquaredDiff = -1;
		
		for(int length = minLength; length < maxLength; ++length) {
			Hashtable<String, Integer> counts = new Hashtable<String, Integer>();
			
			for(int i = 0; i < cipherText.length(); ++i) {
				if(i + length >= cipherText.length())
					continue;
				
				String digram = "" + cipherText.charAt(i) + cipherText.charAt(i + length);
				if(!counts.keySet().contains(digram))
					counts.put(digram, 1);
				else
					counts.put(digram, counts.get(digram) + 1);
			}
			
			double average = 0.0D;
			int count = 0;
			
			for(String str : counts.keySet()) {
				count += counts.get(str);
			}
			
			average = count / counts.size();
			System.out.println("Average: " + average);
			
			double total = 0.0D;
			for(String str : counts.keySet()) {
				double currentSquaredDiff = Math.pow((average - counts.get(str)), 2);
				total += currentSquaredDiff;
			}
			System.out.println("Total: " + total);
			if(smallestSquaredDiff == -1)
				smallestSquaredDiff = total;
			if(total > smallestSquaredDiff) {
				step = length;
			    smallestSquaredDiff = total;
			}
			
		}
		
		return step * 2;
	}
}
