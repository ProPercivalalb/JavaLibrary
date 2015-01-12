package javalibrary.cipher;

import java.util.ArrayList;
import java.util.List;

import javalibrary.language.ILanguage;
import javalibrary.string.LetterCount;
import javalibrary.string.StringAnalyzer;
import javalibrary.string.StringTransformer;

/**
 * @author Alex Barter
 * @since 26 Nov 2013
 */
public class VigenereAuto {

	public static String tryDecode(String cipherText, ILanguage language) {
		int minKeywordLength = 7;
		int maxKeywordLength = 7;
		//Removes all characters except letters
		cipherText = StringTransformer.removeEverythingButLetters(cipherText).toUpperCase();
		int keyLength = findKeywordLength(cipherText, minKeywordLength, maxKeywordLength, language);
		System.out.println(keyLength);
		
		String keyword = "";
        for(int i = 0; i < keyLength; ++i) {
        	String temp = StringTransformer.getEveryNthChar(cipherText, i, keyLength);
            int shift = findBestCaesarShift(temp, language);
            if(shift == 0)
            	keyword += 'A';
            else
            	keyword += (char)('A' + (26 - shift));
        }
        System.out.println("Keyword: " + keyword);
		
		return Vigenere.decode(cipherText, keyword);
	}
	
	public static int findKeywordLength(String text, int minLength, int maxLength, ILanguage language) {
		int keyLength = minLength;
	    ArrayList<Double> kappas = new ArrayList<Double>();
	    //Put the kappa value for each codeword length tested into an array.
	    for(int i = minLength; i <= maxLength; ++i) {
	       String temp = rotateRight(text, i, language);
	       int coincidences = 0;
	       for(int j = 0; j < text.length(); ++j) {
	    	   if(temp.charAt(j) == text.charAt(j)) {
	    		   coincidences += 1;
	           }
	       }
           kappas.add((double)coincidences / text.length());
	    }

	    double smallestSquaredDiff = -1;
	    for(int i = minLength; i <= maxLength; ++i) {
	    	double currentSquaredDiff = Math.pow((0.0667D - kappas.get(i - minLength)), 2);
	        if(smallestSquaredDiff == -1)
	            smallestSquaredDiff = currentSquaredDiff;
	        if(currentSquaredDiff < smallestSquaredDiff) {
	        	keyLength = i;
	            smallestSquaredDiff = currentSquaredDiff;
	        }
	    }
	    return keyLength;
	}
	
	public static String rotateRight(String text, int n, ILanguage language) {
		int cuttingPoint = text.length() - (n % text.length());
	    return text.substring(cuttingPoint, text.length()) + text.substring(0, cuttingPoint);
	}

    public static int findBestCaesarShift(String text, ILanguage language) {
        int best = 0;
        double smallestSum = -1;
        for(int shift = 0; shift < 26; ++shift) {
        	
            String encodedText = Caesar.encode(text, shift);
            List<LetterCount> charFreqs = StringAnalyzer.countLettersInSizeOrder(encodedText);
            List<LetterCount> scaled = scale(encodedText, charFreqs, language);
            double currentSum = sumResidualsSquared(encodedText, scaled, language);
            if(smallestSum == -1)
                smallestSum = currentSum;
            if(currentSum < smallestSum) {
                best = shift;
                smallestSum = currentSum;
            }
        }
        return best;
    }
    
    public static List<LetterCount> scale(String text, List<LetterCount> letterCount, ILanguage language) {
        double maxValue = letterCount.get(0).count;
        double scalingFactor = 12.702D / maxValue;
        for(LetterCount count : letterCount)
            count.count = count.count * scalingFactor;
        return letterCount;
    }
    
    public static double sumResidualsSquared(String text, List<LetterCount> letterCount, ILanguage language) {
        double sum = 0;
        for(LetterCount count : letterCount)
        	sum += Math.pow((count.count - language.getFrequencyOfLetter(count.ch)), 2);
        return sum;
    }
}
