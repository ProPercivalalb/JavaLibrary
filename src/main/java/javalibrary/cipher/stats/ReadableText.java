package javalibrary.cipher.stats;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javalibrary.file.OpenResource;

public class ReadableText {
	
	public static Map<String, Double> 	MAPPING_MONOGRAM 	= new HashMap<String, Double>();
	public static Map<String, Double> 	MAPPING_DIGRAM 		= new HashMap<String, Double>();
	public static List<Double>			LIST_UNSEEN 		= new ArrayList<Double>();
	
	public static String parseText(String text) {
		return ReadableText.parseText(text.toCharArray());
	}
	
	public static String parseText(char[] textArray) {
		int textLength = textArray.length;
		int maxWordLength = Math.min(textLength, 20);
		
		double[][] scores = new double[textLength][maxWordLength];
		String[][] texts = new String[textLength][maxWordLength];
		String[][] words = new String[textLength][maxWordLength];
		
		for(int j = 0; j < maxWordLength; j++) {
			String s = new String(textArray, 0, j + 1);
			scores[0][j] = ReadableText.getMonogramLogProbability(s);
			texts[0][j] = s;
			words[0][j] = s;
		}
		
		for(int i = 1; i < textLength; i++) {
			for(int j = 0; j < maxWordLength; j++) {
				if(i + j + 1 > textLength) break;
				
				String bestTempWord = "";
				String bestTempText = "";
				double bestTempScore = Double.NEGATIVE_INFINITY;
				
				String s = new String(textArray, i, j + 1);
				
				for(int k = 0; k < Math.min(i, maxWordLength); k++) {
					String lastS = texts[i - k - 1][k];
					String lastWord = words[i - k - 1][k];
					
					double score = scores[i - k - 1][k] + ReadableText.getDigramLogProbability(s, lastWord);
					
					if(bestTempScore < score) {
						bestTempWord = s;
						bestTempScore = score;
						bestTempText = lastS + " " + s;
					}
				}
	
				scores[i][j] = bestTempScore;
				texts[i][j] = bestTempText;
				words[i][j] = bestTempWord;
			}
		}
		
		String bestTempText = "";
		double bestTempScore = Double.NEGATIVE_INFINITY;
		for(int i = 0; i < maxWordLength; i++) {
			double score = scores[scores.length - i - 1][i];
			if(bestTempScore < score) {
				bestTempScore = score;
				bestTempText = texts[texts.length - i - 1][i];
			}
		}
		
		return bestTempText;
	}
	
	public static double getMonogramLogProbability(String word) {
		return getDigramLogProbability(word, null);
	}
	
	public static double getDigramLogProbability(String word, String prevWord) {
		if(!ReadableText.MAPPING_MONOGRAM.containsKey(word))
			return ReadableText.LIST_UNSEEN.get(word.length());
		else if(prevWord == null || prevWord.isEmpty())
			return ReadableText.MAPPING_MONOGRAM.get(word);
		else {
			String combined = prevWord + " " + word;
			
			if(!ReadableText.MAPPING_DIGRAM.containsKey(combined))
				return ReadableText.MAPPING_MONOGRAM.get(word);
			
			return ReadableText.MAPPING_DIGRAM.get(combined);
		}
	}
	
	/**
	 * Loads the predefined files from googles trillion word list and calculates the log probability each word
	 */
	public static void loadFile() {
		double NO_TOKENS = 1024908267229.0D;
		double LOG_NO_TOKENS = Math.log10(NO_TOKENS);
		
		OpenResource openResource = new OpenResource("/files/count_1w.txt");
		
		String line;
		while((line = openResource.nextLine()) != null) {
			String[] parts = line.toUpperCase().split("\t");
					
			if(parts.length == 2)  {
				double count = Double.valueOf(parts[1]);
				ReadableText.MAPPING_MONOGRAM.put(parts[0], Math.log10(count) - LOG_NO_TOKENS);
			}
			else
				System.out.println(String.format("%s Format Error: %s", ReadableText.class.getSimpleName(), line));
		}
		
		openResource.close();
		
		openResource = new OpenResource("/files/count_2w.txt");

		while((line = openResource.nextLine()) != null) {
			String[] parts = line.toUpperCase().split("\t");
					
			if(parts.length == 2) {
				String[] bigram = parts[0].split(" ");
				double count = Double.valueOf(parts[1]);
				double log = Math.log10(count) - LOG_NO_TOKENS;
				
				if(ReadableText.MAPPING_MONOGRAM.containsKey(bigram[0]))
					log -= ReadableText.MAPPING_MONOGRAM.get(bigram[0]);
				
				ReadableText.MAPPING_DIGRAM.put(parts[0], log);
			}
			else
				System.out.println(String.format("%s Format Error: %s", ReadableText.class.getSimpleName(), line));
	
		}
		
		openResource.close();
		
		//Calculates the score given to words not in the trillion word list for various lengths
		for(int i = 0; i < 50; i++)
			ReadableText.LIST_UNSEEN.add(1 - i - LOG_NO_TOKENS);
	}
}