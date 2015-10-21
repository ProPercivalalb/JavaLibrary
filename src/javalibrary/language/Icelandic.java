package javalibrary.language;

import java.util.HashMap;
import java.util.Map;

import javalibrary.fitness.NGramData;
import javalibrary.fitness.QuadgramStats;

/**
 * @author Alex Barter
 */
public class Icelandic extends ILanguage {

	public static NGramData quadgramData;
	public static Map<Character, Double> frequencyMap;
	
	@Override
	public Map<Character, Double> getCharacterFrequency() {
		if(frequencyMap == null) {
			frequencyMap = new HashMap<Character, Double>();
			frequencyMap.put('A', 10.110D);
			frequencyMap.put('B', 1.043D);
			frequencyMap.put('C', 0.0D);
			frequencyMap.put('D', 1.575D);
			frequencyMap.put('E', 6.418D);
			frequencyMap.put('F', 3.013D);
			frequencyMap.put('G', 4.241D);
			frequencyMap.put('H', 1.871D);
			frequencyMap.put('I', 7.578D);
			frequencyMap.put('J', 1.144D);
			frequencyMap.put('K', 3.314D);
			frequencyMap.put('L', 4.532D);
			frequencyMap.put('M', 4.041D);
			frequencyMap.put('N', 7.711D);
			frequencyMap.put('O', 2.166D);
			frequencyMap.put('P', 0.789D);
			frequencyMap.put('Q', 0.0D);
			frequencyMap.put('R', 8.581D);
			frequencyMap.put('S', 5.630D);
			frequencyMap.put('T', 4.953D);
			frequencyMap.put('U', 4.562D);
			frequencyMap.put('V', 2.437D);
			frequencyMap.put('W', 0.0D);
			frequencyMap.put('X', 0.046D);
			frequencyMap.put('Y', 0.900D);
			frequencyMap.put('Z', 0.0D);	
		}
		return frequencyMap;
	}
	
	@Override
	public void loadNGramData() {
		quadgramData = QuadgramStats.loadFile("/javalibrary/fitness/icelandic_quadgrams.txt");
	}
	
	@Override
	public NGramData getQuadgramData() {
		return quadgramData;
	}

	@Override
	public String getName() {
		return "Icelandic";
	}
	
	@Override
	public String getImagePath() {
		return "/image/icelandic_flag.png";
	}

	@Override
	public double getNormalCoincidence() {
		return 0;
	}
}