package javalibrary.cipher.stats;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javalibrary.streams.FileReader;
import javalibrary.util.ArrayUtil;

public class WordSplit {
	
	public static List<Double> mappingUnseen = new ArrayList<Double>();
	public static HashMap<String, Double> mappingWords1 = new HashMap<String, Double>();
	public static HashMap<String, Double> mappingWords2 = new HashMap<String, Double>();
	public static HashMap<ArrayList<Integer>, Double> mappingPattern1 = new HashMap<ArrayList<Integer>, Double>();
	public static HashMap<ArrayList<Integer>, Double> mappingPattern2 = new HashMap<ArrayList<Integer>, Double>();
	
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
	
	public static String splitTextWithPattern(char[] charArray) {
		
		int maxwordLength = Math.min(charArray.length, 20);
		
		double[][] scores = new double[charArray.length][maxwordLength];
		String[][] texts = new String[charArray.length][maxwordLength];
		String[][] words = new String[charArray.length][maxwordLength];
		
		for(int j = 0; j < maxwordLength; j++) {
			String s = new String(charArray, 0, j + 1);
			scores[0][j] = scorePattern(s);
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
					
					double score = scores[i - k - 1][k] + scorePatternDuo(s, lastWord);
					
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
			System.out.println(Arrays.toString(texts[i]));
			System.out.println(Arrays.toString(scores[i]));
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
	
	public static double scorePattern(String word) {
		return scorePatternDuo(word, "");
	}
	
	public static double scorePatternDuo(String word, String prevWord) {
		ArrayList<Integer> pattern = getPatternFromWord(word);
		ArrayList<Integer> pattern2 = getPatternFromWord(prevWord + " " + word);
		if(!mappingPattern1.containsKey(pattern))
			return mappingUnseen.get(word.length());
		else if(prevWord == "" || !mappingPattern2.keySet().contains(pattern2)) 
			return mappingPattern1.get(pattern);
		else
			return mappingPattern2.get(pattern2);
	}
	
	public static ArrayList<Integer> getPatternFromWord(String word) {
		char[] chars = word.toCharArray();
		
		int end = chars.length;
		List<Character> list = new ArrayList<Character>();

		for(int i = 0; i < end; i++)
			if(!list.contains(chars[i]))
				list.add(chars[i]);
		
		
		ArrayList<Integer> pattern = new ArrayList<Integer>();
	
		for(int i = 0; i < end; i++) {
			if(chars[i] == ' ')
				pattern.add(-1);
			else
				pattern.add(list.indexOf(chars[i]));
		}
	
		
		//System.out.println(Arrays.toString(pattern));
		return pattern;
	}
	
	public static void loadFile() {
		double noTokens = 1024908267229.0D;
		
		List<String> list = FileReader.compileTextFromResource("/javalibrary/dict/count_1w.txt");

		for(String line : list) {
			String[] str = line.toUpperCase().split("\t");
					
			if(str.length < 2) continue;
					
			double count = Double.valueOf(str[1]);
			mappingWords1.put(str[0], Math.log10(count / noTokens));
			
			ArrayList<Integer> pattern = getPatternFromWord(str[0]);
			if(mappingPattern1.containsKey(pattern))
				mappingPattern1.put(pattern, mappingPattern1.get(pattern) + count);
			else
				mappingPattern1.put(pattern, count);
		}
		
		for(ArrayList<Integer> pattern : mappingPattern1.keySet()) {
			mappingPattern1.put(pattern, Math.log10(mappingPattern1.get(pattern) / noTokens));
			//System.out.println(Arrays.toString(pattern) + " " + mappingPattern1.get(pattern));
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
			
			
			ArrayList<Integer> pattern = getPatternFromWord(str[0]);
			if(mappingPattern2.containsKey(pattern))
				mappingPattern2.put(pattern, mappingPattern2.get(pattern) + count);
			else
				mappingPattern2.put(pattern, count);
		}
		
		for(ArrayList<Integer> pattern : mappingPattern2.keySet()) {
			List<Integer> cutPattern = (List<Integer>) pattern.subList(0, pattern.indexOf((Integer)(-1)));
			
			double log = Math.log10(mappingPattern2.get(pattern) / noTokens);

			
			if(mappingPattern1.containsKey(cutPattern)) {
				//System.out.println("YUUP");
				log -= mappingPattern1.get(cutPattern);
			}
			
			mappingPattern2.put(pattern, log);
			
			
			//System.out.println(Arrays.toString(pattern) + " "  + Arrays.toString(cutPattern) + " "+ mappingPattern2.get(pattern));
		}
		
		for(int i = 0; i < 50; i++)
			mappingUnseen.add(Math.log10(10.0D / (noTokens * Math.pow(10, i))));
	}
}