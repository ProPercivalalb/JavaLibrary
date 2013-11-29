package javalibrary.cipher;

import java.util.ArrayList;
import java.util.List;

import javalibrary.string.LetterCount;
import javalibrary.string.StringAnalyzer;
import javalibrary.string.StringTransformer;

/**
 * @author Alex Barter
 * @since 26 Nov 2013
 */
public class VigenereAuto {

	public static String tryDecode(String cipherText) {
		int minKeywordLength = 1;
		int maxKeywordLength = 15;
		//Removes all characters except letters
		cipherText = StringTransformer.removeEverythingButLetters(cipherText).toLowerCase();
		int keyLength = findKeywordLength(cipherText, minKeywordLength, maxKeywordLength);
		System.out.println(keyLength);
		
		String keyword = "";
        for(int i = 0; i < keyLength; ++i) {
        	String temp = StringTransformer.getEveryNthChar(cipherText, i, keyLength);
            int shift = findBestCaesarShift(temp);
            if(shift == 0)
            	keyword += 'A';
            else
            	keyword += (char)('A' + (26 - shift));
        }
        System.out.println("Keyword: " + keyword);
		
		return Vigenere.decode(cipherText, keyword);
	}
	
	public static int findKeywordLength(String text, int minLength, int maxLength) {
		int keyLength = minLength;
	    ArrayList<Double> kappas = new ArrayList<Double>();
	    //Put the kappa value for each codeword length tested into an array.
	    for(int i = minLength; i <= maxLength; ++i) {
	       String temp = rotateRight(text, i);
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
	
	public static String rotateRight(String text, int n) {
		int cuttingPoint = text.length() - (n % text.length());
	    return text.substring(cuttingPoint, text.length()) + text.substring(0, cuttingPoint);
	}

    public static int findBestCaesarShift(String text) {
        int best = 0;
        double smallestSum = -1;
        for(int shift = 0; shift < 26; ++shift) {
        	
            String encodedText = Caesar.encode(text, shift);
            List<LetterCount> charFreqs = StringAnalyzer.countLettersInSizeOrder(encodedText);
            List<LetterCount> scaled = scale(encodedText, charFreqs);
            double currentSum = sumResidualsSquared(encodedText, scaled);
            if(smallestSum == -1)
                smallestSum = currentSum;
            if(currentSum < smallestSum) {
                best = shift;
                smallestSum = currentSum;
            }
        }
        return best;
    }
    
    public static List<LetterCount> scale(String text, List<LetterCount> letterCount) {
        double maxValue = letterCount.get(0).count;
        double scalingFactor = 12.702D / maxValue;
        for(LetterCount count : letterCount)
            count.count = count.count * scalingFactor;
        return letterCount;
    }
    
    public static double sumResidualsSquared(String text, List<LetterCount> letterCount) {
        double sum = 0;
        for(LetterCount count : letterCount)
        	sum += Math.pow((count.count - getDefaultCharFrequency(count.ch)), 2);
        return sum;
    }
    
    public static double getDefaultCharFrequency(char ch) {
    	ch = Character.toUpperCase(ch);
    	if(ch == 'A') return 8.167D; if(ch == 'B') return 1.492D; if(ch == 'C') return 2.782D; if(ch == 'D') return 4.253D; if(ch == 'E') return 12.702D; 
        if(ch == 'F') return 2.228D; if(ch == 'G') return 2.015D; if(ch == 'H') return 6.094D; if(ch == 'I') return 6.996D; if(ch == 'J') return 0.153D; 
        if(ch == 'K') return 0.772D; if(ch == 'L') return 4.025D; if(ch == 'M') return 2.406D; if(ch == 'N') return 6.749D; if(ch == 'O') return 7.507D; 
        if(ch == 'P') return 1.929D; if(ch == 'Q') return 0.095D; if(ch == 'R') return 5.987D; if(ch == 'S') return 6.327D; if(ch == 'T') return 9.056D; 
        if(ch == 'U') return 2.758D; if(ch == 'V') return 0.978D; if(ch == 'W') return 2.360D; if(ch == 'X') return 0.150D; if(ch == 'Y') return 1.974D; 
        if(ch == 'Z') return 0.074D;
        return 0.0D;
    }
}
