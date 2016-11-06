package javalibrary.cipher.stats;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javalibrary.streams.FileReader;

public class WordSplit {
	
	public static List<Double> mappingUnseen = new ArrayList<Double>();
	public static HashMap<String, Double> mappingWords1 = new HashMap<String, Double>();
	public static HashMap<String, Double> mappingWords2 = new HashMap<String, Double>();
	
	public static String splitText(String text) {
		return splitText(text.toCharArray());
	}
	
	public static String splitText(char[] charArray) {
		
		int maxwordLength = Math.min(charArray.length, 20);
		
		double[][] scores = new double[charArray.length][maxwordLength];
		String[][] texts = new String[charArray.length][maxwordLength];
		String[][] words = new String[charArray.length][maxwordLength];
		
		for(int j = 0; j < maxwordLength; j++) {
			String s = new String(charArray, 0, j + 1);
			scores[0][j] = scoreWord(s);
			texts[0][j] = s;
			words[0][j] = s;
		}
		
		for(int i = 1; i < charArray.length; i++) {
			for(int j = 0; j < maxwordLength; j++) {
				if(i + j + 1 > charArray.length) break;
				
				String bestTempWord = "";
				String bestTempText = "";
				double bestTempScore = Double.NEGATIVE_INFINITY;
				
				String s = new String(charArray, i, j + 1);
				
				for(int k = 0; k < Math.min(i, maxwordLength); k++) {
					String lastS = texts[i - k - 1][k];
					String lastWord = words[i - k - 1][k];
					
					double score = scores[i - k - 1][k] + scoreWordDuo(s, lastWord);
					
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
		for(int i = 0; i < maxwordLength; i++) {
			double score = scores[scores.length - i - 1][i];
			if(bestTempScore < score) {
				bestTempScore = score;
				bestTempText = texts[texts.length - i - 1][i];
			}
		}
		lastScore = bestTempScore;
		//System.out.println("" + bestTempScore);
		return bestTempText;
	}
	
	public static double lastScore = Double.NEGATIVE_INFINITY;
	
	public static double scoreWord(String word) {
		return scoreWordDuo(word, "");
	}
	
	public static double scoreWordDuo(String word, String prevWord) {
		if(!mappingWords1.containsKey(word))
			return mappingUnseen.get(word.length());
		else if(prevWord == "" || !mappingWords2.keySet().contains(prevWord + " " + word)) 
			return mappingWords1.get(word);
		else
			return mappingWords2.get(prevWord + " " + word);
	}
	
	public static void loadFile() {
		double noTokens = 1024908267229.0D;
		
		List<String> list = FileReader.compileTextFromResource("/javalibrary/dict/count_1w.txt");

		for(String line : list) {
			String[] str = line.toUpperCase().split("\t");
					
			if(str.length < 2) continue;
					
			double count = Double.valueOf(str[1]);
			mappingWords1.put(str[0], Math.log10(count / noTokens));
		}
		
		
		list = FileReader.compileTextFromResource("/javalibrary/dict/count_2w.txt");

		for(String line : list) {
			String[] str = line.toUpperCase().split("\t");
					
			if(str.length < 2) continue;
					
			String[] words = str[0].split(" ");
			double count = Double.valueOf(str[1]);
			double log = Math.log10(count / noTokens);
			
			if(mappingWords1.containsKey(words[0]))
				log -= mappingWords1.get(words[0]);
			
			mappingWords2.put(str[0], log);
	
		}
		
		for(int i = 0; i < 50; i++)
			mappingUnseen.add(Math.log10(10.0D / (noTokens * Math.pow(10, i))));
	}
}