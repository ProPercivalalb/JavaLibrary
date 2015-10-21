package javalibrary.language;

import java.util.HashMap;
import java.util.Map;

import javalibrary.fitness.NGramData;
import javalibrary.fitness.QuadgramStats;

/**
 * @author Alex Barter
 */
public class Swedish extends ILanguage {

	public static NGramData quadgramData;
	public static Map<Character, Double> frequencyMap;
	
	@Override
	public Map<Character, Double> getCharacterFrequency() {
		if(frequencyMap == null) {
			frequencyMap = new HashMap<Character, Double>();
			frequencyMap.put('A', 9.383D);
			frequencyMap.put('B', 1.535D);
			frequencyMap.put('C', 1.486D);
			frequencyMap.put('D', 4.702D);
			frequencyMap.put('E', 10.149D);
			frequencyMap.put('F', 2.027D);
			frequencyMap.put('G', 2.862D);
			frequencyMap.put('H', 2.090D);
			frequencyMap.put('I', 5.817D);
			frequencyMap.put('J', 0.614D);
			frequencyMap.put('K', 3.140D);
			frequencyMap.put('L', 5.275D);
			frequencyMap.put('M', 3.471D);
			frequencyMap.put('N', 8.542D);
			frequencyMap.put('O', 4.482D);
			frequencyMap.put('P', 1.839D);
			frequencyMap.put('Q', 0.020D);
			frequencyMap.put('R', 8.431D);
			frequencyMap.put('S', 6.590D);
			frequencyMap.put('T', 7.691D);
			frequencyMap.put('U', 1.919D);
			frequencyMap.put('V', 2.415D);
			frequencyMap.put('W', 0.142D);
			frequencyMap.put('X', 0.159D);
			frequencyMap.put('Y', 0.708D);
			frequencyMap.put('Z', 0.070D);	
		}
		return frequencyMap;
	}
	
	@Override
	public void loadNGramData() {
		quadgramData = QuadgramStats.loadFile("/javalibrary/fitness/swedish_quadgrams.txt");
	}
	
	@Override
	public NGramData getQuadgramData() {
		return quadgramData;
	}

	@Override
	public String getName() {
		return "Swedish";
	}
	
	@Override
	public String getImagePath() {
		return "/image/swedish_flag.png";
	}

	@Override
	public double getNormalCoincidence() {
		return 0;
	}
}