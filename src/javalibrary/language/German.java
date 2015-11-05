package javalibrary.language;

import java.util.HashMap;
import java.util.Map;

import javalibrary.fitness.NGramData;
import javalibrary.fitness.TextFitness;

/**
 * @author Alex Barter
 */
public class German extends ILanguage {
	
	public static NGramData quadgramData;
	public static NGramData trigramData;
	public static Map<Character, Double> frequencyMap;
	
	@Override
	public Map<Character, Double> getCharacterFrequency() {
		if(frequencyMap == null) {
			frequencyMap = new HashMap<Character, Double>();
			frequencyMap.put('A', 6.516D);
			frequencyMap.put('B', 1.886D);
			frequencyMap.put('C', 2.732D);
			frequencyMap.put('D', 5.076D);
			frequencyMap.put('E', 16.396D);
			frequencyMap.put('F', 1.656D);
			frequencyMap.put('G', 3.009D);
			frequencyMap.put('H', 4.577D);
			frequencyMap.put('I', 6.550D);
			frequencyMap.put('J', 0.268D);
			frequencyMap.put('K', 1.417D);
			frequencyMap.put('L', 3.437D);
			frequencyMap.put('M', 2.534D);
			frequencyMap.put('N', 9.776D);
			frequencyMap.put('O', 2.594D);
			frequencyMap.put('P', 0.670D);
			frequencyMap.put('Q', 0.018D);
			frequencyMap.put('R', 7.003D);
			frequencyMap.put('S', 7.270D);
			frequencyMap.put('T', 6.154D);
			frequencyMap.put('U', 4.166D);
			frequencyMap.put('V', 0.846D);
			frequencyMap.put('W', 1.921D);
			frequencyMap.put('X', 0.034D);
			frequencyMap.put('Y', 0.039D);
			frequencyMap.put('Z', 1.134D);	
		}
		return frequencyMap;
	}
	
	@Override
	public void loadNGramData() {
		quadgramData = TextFitness.loadFile("/javalibrary/fitness/german_quadgrams.txt");
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
		return "German";
	}
	
	@Override
	public String getImagePath() {
		return "/image/german_flag.png";
	}

	@Override
	public double getNormalCoincidence() {
		return 0.0762D;
	}
}