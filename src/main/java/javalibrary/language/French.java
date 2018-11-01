package javalibrary.language;

import java.util.HashMap;
import java.util.Map;

import javalibrary.fitness.NGramData;
import javalibrary.fitness.TextFitness;

/**
 * @author Alex Barter
 */
public class French extends ILanguage {

	public static NGramData quadgramData;
	public static NGramData trigramData;
	public static Map<Character, Double> frequencyMap;
	
	@Override
	public Map<Character, Double> getCharacterFrequency() {
		if(frequencyMap == null) {
			frequencyMap = new HashMap<Character, Double>();
			frequencyMap.put('A', 7.636D);
			frequencyMap.put('B', 0.901D);
			frequencyMap.put('C', 3.260D);
			frequencyMap.put('D', 3.669D);
			frequencyMap.put('E', 14.715D);
			frequencyMap.put('F', 1.066D);
			frequencyMap.put('G', 0.866D);
			frequencyMap.put('H', 0.737D);
			frequencyMap.put('I', 7.529D);
			frequencyMap.put('J', 0.613D);
			frequencyMap.put('K', 0.049D);
			frequencyMap.put('L', 5.456D);
			frequencyMap.put('M', 2.968D);
			frequencyMap.put('N', 7.095D);
			frequencyMap.put('O', 5.796D);
			frequencyMap.put('P', 2.521D);
			frequencyMap.put('Q', 1.362D);
			frequencyMap.put('R', 6.693D);
			frequencyMap.put('S', 7.948D);
			frequencyMap.put('T', 7.244D);
			frequencyMap.put('U', 6.311D);
			frequencyMap.put('V', 1.838D);
			frequencyMap.put('W', 0.074D);
			frequencyMap.put('X', 0.427D);
			frequencyMap.put('Y', 0.128D);
			frequencyMap.put('Z', 0.326D);	
		}
		return frequencyMap;
	}

	@Override
	public void loadNGramData() {
		quadgramData = TextFitness.loadFile("/files/french_quadgrams.txt");
	}
	
	@Override
	public NGramData getQuadgramData() {
		return quadgramData;
	}
	
	@Override
	public NGramData getDiagramData() {
		return trigramData;
	}
	
	@Override
	public String getName() {
		return "French";
	}
	
	@Override
	public String getImagePath() {
		return "/image/french_flag.png";
	}

	@Override
	public double getNormalCoincidence() {
		return 0.0778D;
	}
}