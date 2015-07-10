package javalibrary.fitness;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javalibrary.cipher.stats.TraverseTree;
import javalibrary.file.FileCompression;
import javalibrary.language.ILanguage;

/**
 * @author Alex Barter (10AS)
 */
public class QuadgramStats {
	
	public static double scoreFitness(String text, ILanguage language) {
		double fitness = 0.0D;
		char[] characters = text.toCharArray();

		for(int k = 0; k < (text.length() - 4 + 1); k++) {
			String s = new String(characters, k, 4);
			NGramData quadgramData = language.getQuadgramData();
			
			if(quadgramData.mapping.containsKey(s))
				fitness += quadgramData.mapping.get(s);
			else
				fitness += quadgramData.floor;
		}

		
		return fitness;
	}
	
	public static double getEstimatedFitness(String text, ILanguage language) {
		NGramData quadgramData = language.getQuadgramData();
		return quadgramData.fitnessPerChar * text.length();
	}
	
	public static NGramData loadFile(String file) {
		HashMap<String, Double> mapping = new HashMap<String, Double>();
		double floor = 0.0D;
		double total = 0.0D;
		double fitnessPerChar = 0.0D;
		
		InputStream inputStream = QuadgramStats.class.getResourceAsStream(file);

		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			
		try {
				
			String line = "";
			while((line = reader.readLine()) != null) {
				if(line.isEmpty()) 
					continue;
					
				String[] str = line.split(" ");
					
				if(str.length < 2)
					continue;
					
				int count = Integer.valueOf(str[1]);
				total += count;
				mapping.put(str[0], (double)count);
			}
				
			floor = Math.log10(0.01F / total);
				
			for(String gram : mapping.keySet()) {
				double count = mapping.get(gram);
				mapping.put(gram, Math.log10(count / total));
				fitnessPerChar += (count / total) * Math.log10(count / total);
			}
		} 
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return new NGramData(mapping, floor, fitnessPerChar);
	}
}
