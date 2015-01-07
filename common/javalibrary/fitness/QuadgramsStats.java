package javalibrary.fitness;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Hashtable;

/**
 * @author Alex Barter (10AS)
 */
public class QuadgramsStats {

	public static final Hashtable<String, Double> QUADGRAMS_STATS = new Hashtable<String, Double>();
	public static double TOTAL_QUADGRAMS = 0.0D;
	public static double FLOOR = 0.0D;
	
	public static double scoreFitness(String text) {
		
		double fitness = 0.0D;
		char[] characters = text.toCharArray();

		for(int k = 0; k < (text.length() - 4 + 1); k++) {
			String s = new String(characters, k, 4);
			if(QUADGRAMS_STATS.containsKey(s))
				fitness += QUADGRAMS_STATS.get(s);
			else
				fitness += FLOOR;
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
				QUADGRAMS_STATS.put(str[0], (double)count);
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
