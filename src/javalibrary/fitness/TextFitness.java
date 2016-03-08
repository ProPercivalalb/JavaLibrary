package javalibrary.fitness;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;

import javalibrary.language.ILanguage;
import javalibrary.streams.FileReader;

/**
 * @author Alex Barter (10AS)
 */
public class TextFitness {
	
	public static double scoreFitnessQuadgrams(String text, ILanguage language) {
		return scoreFitnessQuadgrams(text.toCharArray(), language);
	}
	
	public static double scoreFitnessQuadgrams(char[] text, ILanguage language) {
		double fitness = 0.0D;
		NGramData quadgramData = language.getQuadgramData();
		
		
		for(int k = 0; k < (text.length - 4 + 1); k++) {
			String s = new String(text, k, 4);
				
			fitness += scoreWord(s, quadgramData);
		}

		return fitness;
	}
	
	public static double scoreFitnessQuadgrams(char[] text, ILanguage language, double currentLowest) {
		double fitness = 0.0D;
		NGramData quadgramData = language.getQuadgramData();
		
		
		for(int k = 0; k < (text.length - 4 + 1); k++) {
			String s = new String(text, k, 4);
				
			fitness += scoreWord(s, quadgramData);
			if(fitness < currentLowest)
				return currentLowest;
		}

		return fitness;
	}
	
	
	public static double scoreWord(char[] s, NGramData quadgramData) {
		return scoreWord(new String(s), quadgramData);
	}
	
	public static double scoreWord(String s, NGramData quadgramData) {
		if(quadgramData.mapping.containsKey(s))
			return quadgramData.mapping.get(s);
	
		return quadgramData.floor;
	}
	
	public static double scoreFitnessDiagrams(String text, ILanguage language) {
		double fitness = 0.0D;
		char[] characters = text.toCharArray();

		for(int k = 0; k < (text.length() - 2 + 1); k++) {
			String s = new String(characters, k, 2);
			NGramData trigramData = language.getDiagramData();
			
			if(trigramData.mapping.containsKey(s))
				fitness += trigramData.mapping.get(s);
			else
				fitness += trigramData.floor;
		}

		return fitness;
	}
	
	public static double getEstimatedFitness(String text, ILanguage language) {
		return getEstimatedFitness(text.length(), language);
	}
	
	public static double getEstimatedFitness(int length, ILanguage language) {
		NGramData quadgramData = language.getQuadgramData();
		return quadgramData.fitnessPerChar * Math.max(0, length - 3);
	}
	
	public static NGramData loadFile(String resourcePath) {
		HashMap<String, Double> mapping = new HashMap<String, Double>();
		double floor = 0.0D;
		double total = 0.0D;
		double fitnessPerChar = 0.0D;
		
		List<String> list = FileReader.compileTextFromResource(resourcePath);

		for(String line : list) {
			String[] str = line.split(" ");
					
			if(str.length < 2) continue;
					
			int count = Integer.valueOf(str[1]);
			total += count;
			mapping.put(str[0], (double)count);
		}
			
		floor = Math.log10(0.01F / total);
			
		for(String gram : mapping.keySet()) {
			double count = mapping.get(gram);
			double log = Math.log10(count / total);

			mapping.put(gram, log);
			fitnessPerChar += (count / total) * log;
		}

		
		return new NGramData(mapping, floor, fitnessPerChar);
	}
}
