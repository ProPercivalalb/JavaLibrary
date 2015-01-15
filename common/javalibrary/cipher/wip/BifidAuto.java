package javalibrary.cipher.wip;

import java.util.HashMap;
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
		cipherText = cipherText.toUpperCase();
		int step = minLength;
		double smallestSquaredDiff = -1;
		
		for(int length = minLength; length <= maxLength; ++length) {
			System.out.println("Period: " + length);
			
			
			HashMap<String, Integer> counts = new HashMap<String, Integer>();
			
			for(int i = 0; i < cipherText.length() - length - 1; ++i) {
				if(i + length >= cipherText.length())
					continue;
				
				String digram = "" + cipherText.charAt(i) + cipherText.charAt(i + length + 1);
				//System.out.println(digram);
				if(!counts.containsKey(digram))
					counts.put(digram, 1);
				else
					counts.put(digram, counts.get(digram) + 1);
			}
			
			float count = 0.0F;
			
			for(String str : counts.keySet())
				count += counts.get(str);
			
			float mean = count / counts.size();
			
			System.out.println("Average: " + mean);
			
			float total = 0.0F;
			for(String str : counts.keySet())
				total += (float)Math.pow(counts.get(str) - mean, 2);
			
			total = total / counts.size();
			
			System.out.println("Total: " + total);
			System.out.println("");
			
		}
		
		return step;
	}
}
