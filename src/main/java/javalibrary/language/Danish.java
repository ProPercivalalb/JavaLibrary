package javalibrary.language;

import java.util.HashMap;
import java.util.Map;

import javalibrary.fitness.NGramData;
import javalibrary.fitness.TextFitness;

/**
 * @author Alex Barter
 */
public class Danish extends ILanguage {

	public static NGramData quadgramData;
	public static NGramData trigramData;
	public static Map<Character, Double> frequencyMap;
	
	@Override
	public Map<Character, Double> getCharacterFrequency() {
		if(frequencyMap == null) {
			frequencyMap = new HashMap<Character, Double>();
			frequencyMap.put('A', 6.025D);
			frequencyMap.put('B', 2.000D);
			frequencyMap.put('C', 0.565D);
			frequencyMap.put('D', 5.858D);
			frequencyMap.put('E', 15.453D);
			frequencyMap.put('F', 2.406D);
			frequencyMap.put('G', 4.077D);
			frequencyMap.put('H', 1.621D);
			frequencyMap.put('I', 6.000D);
			frequencyMap.put('J', 0.730D);
			frequencyMap.put('K', 3.395D);
			frequencyMap.put('L', 5.229D);
			frequencyMap.put('M', 3.237D);
			frequencyMap.put('N', 7.240D);
			frequencyMap.put('O', 4.636D);
			frequencyMap.put('P', 1.756D);
			frequencyMap.put('Q', 0.007D);
			frequencyMap.put('R', 8.956D);
			frequencyMap.put('S', 5.805D);
			frequencyMap.put('T', 6.862D);
			frequencyMap.put('U', 1.979D);
			frequencyMap.put('V', 2.332D);
			frequencyMap.put('W', 0.069D);
			frequencyMap.put('X', 0.028D);
			frequencyMap.put('Y', 0.698D);
			frequencyMap.put('Z', 0.034D);	
		}
		return frequencyMap;
	}
	
	@Override
	public void loadNGramData() {
		quadgramData = TextFitness.loadFile("/files/danish_quadgrams.txt");
		//trigramData = TextFitness.loadFile("/files/danish_trigrams.txt");
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
		return "Danish";
	}

	@Override
	public String getImagePath() {
		return "/image/danish_flag.png";
	}

	@Override
	public double getNormalCoincidence() {
		return 0.0672D;
	}
}