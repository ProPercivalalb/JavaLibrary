package javalibrary.fitness;

import java.math.BigInteger;
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
		NGramData quadgramData = language.getQuadgramData();
		
		double fitness = 0.0D;
		for(int k = 0; k < (text.length - 4 + 1); k++)
				
			fitness += scoreWord(text, k, quadgramData);

		return fitness;
	}
	
	public static double scoreFitness(char[] text, NGramData ngramData) {

		double fitness = 0.0D;
		for(int k = 0; k < (text.length - ngramData.nGram + 1); k++)
				
			fitness += scoreWord(text, k, ngramData);

		return fitness;
	}
	
	/** Dynamicly checking if it better score
	public static double scoreFitnessQuadgrams(char[] text, ILanguage language, double bestScore) {
		NGramData quadgramData = language.getQuadgramData();
		
		double fitness = 0.0D;
		for(int k = 0; k < (text.length - 4 + 1); k++) {
			fitness += scoreWord(text, k, quadgramData);
			if(fitness < bestScore)
				return bestScore - 1D;
		}

		return fitness;
	}**/
	
	
	public static double scoreWord(char[] s, int startIndex, NGramData nGramData) {
		return nGramData.getValue(s, startIndex);
	}
	
	public static double getEstimatedFitness(String text, ILanguage language) {
		return getEstimatedFitness(text.length(), language.getQuadgramData());
	}
	
	public static double getEstimatedFitness(int length, NGramData nGramData) {
		return nGramData.fitnessPerChar * Math.max(0, length - nGramData.nGram + 1);
	}
	
	public static NGramData loadFile(String resourcePath) {
		HashMap<String, Double> mapping = new HashMap<String, Double>();
		double floor = 0.0D;
		BigInteger total = BigInteger.ZERO;
		double fitnessPerChar = 0.0D;
		
		List<String> list = FileReader.compileTextFromResource(resourcePath);
		int length = -1;
		
		for(String line : list) {
			String[] str = line.split(" ");
					
			if(str.length < 2) continue;
					
			int count = Integer.valueOf(str[1]);
			total = total.add(BigInteger.valueOf(count));
			mapping.put(str[0], (double)count);
			if(length == -1)
				length = str[0].length();
		}
			
		floor = Math.log10(0.01D / total.doubleValue());
			
		for(String gram : mapping.keySet()) {
			double count = mapping.get(gram);
			double log = Math.log10(count / total.doubleValue());

			mapping.put(gram, log);
			fitnessPerChar += (count / total.doubleValue()) * log;
		}

		
		return new NGramData(mapping, floor, fitnessPerChar, length);
	}
}
