package javalibrary.fitness;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;

import javalibrary.string.StringAnalyzer;

/**
 * @author Alex Barter (10AS)
 */
public class QuadgramsStats {

	public static final Hashtable<String, Double> QUADGRAMS_STATS = new Hashtable<String, Double>();
	public static double TOTAL_QUADGRAMS = 0.0D;
	public static double FLOOR = 0.0D;
	
	public static double scoreFitness(String text) {
		Hashtable<String, Integer> quadgrams = StringAnalyzer.analyzeLetterCombination(text, 4, 4);
		
		double fitness = 0.0D;
		
		for(String gram : quadgrams.keySet()) {
			
			int occurrence = quadgrams.get(gram);
		
			//If the quadgram is in the language 
			if(QUADGRAMS_STATS.containsKey(gram))
				fitness += QUADGRAMS_STATS.get(gram) * occurrence;
			else
				fitness += FLOOR * occurrence;
		}
		
		return fitness;
	}
	
	static {
		InputStream inputStream = QuadgramsStats.class.getResourceAsStream("/javalibrary/fitness/englishQuadgrams.txt");
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		
		double total = 0.0F;
		
		try {
			String currentLine = "";
			while((currentLine = reader.readLine()) != null) {
				if(currentLine.isEmpty()) 
					continue;
				String[] str = currentLine.split(" ");
				if(str.length < 2)
					continue;
				int count = Integer.valueOf(str[1]);
				total += count;
				QUADGRAMS_STATS.put(str[0].toLowerCase(), (double)count);
			}
			
			TOTAL_QUADGRAMS = total;
			FLOOR = Math.log10(0.01F / total);
			
			for(String gram : QUADGRAMS_STATS.keySet()) {
				double count = QUADGRAMS_STATS.get(gram);
				QUADGRAMS_STATS.put(gram, (double) Math.log10(count / TOTAL_QUADGRAMS));
			}
		} 
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}
