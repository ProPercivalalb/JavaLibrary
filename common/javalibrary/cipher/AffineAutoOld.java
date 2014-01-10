package javalibrary.cipher;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javalibrary.string.LetterCount;
import javalibrary.string.StringAnalyzer;
import javalibrary.string.StringTransformer;

/**
 * @author Alex Barter
 * @since 27 Nov 2013
 */
public class AffineAutoOld {

	public static String tryDecode(String cipherText) {
		cipherText = StringTransformer.removeEverythingButLetters(cipherText).toLowerCase();
		String plainText = cipherText.toUpperCase();
		List<LetterCount> charFreqs = StringAnalyzer.countLettersInSizeOrder(cipherText);
		
		char possibleE = Character.toLowerCase(charFreqs.get(0).ch);
		char possibleT = Character.toLowerCase(charFreqs.get(1).ch);
		System.out.println("E: " + possibleE + " T: " + possibleT);
		
		Hashtable<String, Integer> triGraphFreq = StringAnalyzer.analyzeLetterCombination(cipherText, 3, 3);
		
		String possibleTHE = "THE";
		String possibleAND = "AND";
	  	int lastLargest = 0;
	  			
	  	Iterator<String> ite = triGraphFreq.keySet().iterator();
	  			
	  	while(ite.hasNext()) {
	  		String str = ite.next();
	  		if(lastLargest == 0) {
	  			possibleTHE = str;
	  			lastLargest = triGraphFreq.get(str);
	  		}
	  		else if(triGraphFreq.get(str) > lastLargest) {
	  			possibleTHE = str;
	  			lastLargest = triGraphFreq.get(str);
	  		}
	  	}
	  	
	  	triGraphFreq.remove(possibleTHE);	
	  	
	  	lastLargest = 0;
		ite = triGraphFreq.keySet().iterator();
			
	  	while(ite.hasNext()) {
	  		String str = ite.next();
	  		if(str.contains("" + possibleTHE.charAt(0)) 
	  		|| str.contains("" + possibleTHE.charAt(1)) 
	  		|| str.contains("" + possibleTHE.charAt(2))
	  		|| str.charAt(0) == str.charAt(1)
	  		|| str.charAt(0) == str.charAt(2)
	  		|| str.charAt(1) == str.charAt(2))
	  			continue;
	  		if(lastLargest == 0) {
	  			possibleAND = str;
	  			lastLargest = triGraphFreq.get(str);
	  		}
	  		else if(triGraphFreq.get(str) > lastLargest) {
	  			possibleAND = str;
	  			lastLargest = triGraphFreq.get(str);
	  		}
	  		else if(triGraphFreq.get(str) == lastLargest) {
	  			System.out.println(str);
	  		}
	  	}
		System.out.println("THE: " + possibleTHE);
		System.out.println("AND: " + possibleAND);
		
		if(possibleTHE.charAt(0) == possibleT && possibleTHE.charAt(2) == possibleE) {
			System.out.println("LOOKS LIKE 'THE' IS FOUND");
		}
		
		//THE
		plainText = plainText.replace(Character.toUpperCase(possibleE), 'e');
		plainText = plainText.replace(Character.toUpperCase(possibleT), 't');
		plainText = plainText.replace(Character.toUpperCase(possibleTHE.charAt(1)), 'h');
		//AND
		plainText = plainText.replace(Character.toUpperCase(possibleAND.charAt(0)), 'a');
		plainText = plainText.replace(Character.toUpperCase(possibleAND.charAt(1)), 'n');
		plainText = plainText.replace(Character.toUpperCase(possibleAND.charAt(2)), 'd');
		
		Hashtable<String, Integer> diaGraphFreq = StringAnalyzer.analyzeLetterCombination(cipherText, 2, 2);
		
		String possibleSS = "SS";
	  	lastLargest = 0;
	  			
	  	ite = diaGraphFreq.keySet().iterator();
	  			
	  	while(ite.hasNext()) {
	  		String str = ite.next();
	  		if(str.charAt(0) != str.charAt(1) 
	  		|| str.charAt(0) == possibleE && str.charAt(1) == possibleE 
	  		|| str.charAt(0) == possibleT && str.charAt(1) == possibleT 
	  		|| str.charAt(0) == possibleAND.charAt(0) && str.charAt(1) == possibleAND.charAt(0)
	  		|| str.charAt(0) == possibleAND.charAt(1) && str.charAt(1) == possibleAND.charAt(1)
	  		|| str.charAt(0) == possibleAND.charAt(2) && str.charAt(1) == possibleAND.charAt(2))
	  			continue;
	  		
	  		if(lastLargest == 0) {
	  			possibleSS = str;
	  			lastLargest = diaGraphFreq.get(str);
	  		}
	  		else if(diaGraphFreq.get(str) > lastLargest) {
	  			possibleSS = str;
	  			lastLargest = diaGraphFreq.get(str);
	  		}
	  	}
	  	
	  	System.out.println("SS: " + possibleSS);
		
	  	plainText = plainText.replace(Character.toUpperCase(possibleSS.charAt(0)), 's');
	  	
	  	System.out.println(String.format("ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
	  	System.out.println(String.format("%sBC%s%sFGHIJKLM%sOPQRS%sUVWXYZ", possibleAND.charAt(0), possibleAND.charAt(2), possibleE, possibleAND.charAt(1), possibleT));
		return plainText;
	}
}
