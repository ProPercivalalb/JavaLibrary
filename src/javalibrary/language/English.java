package javalibrary.language;

import java.util.HashMap;
import java.util.Map;

import javalibrary.fitness.NGramData;
import javalibrary.fitness.TextFitness;

/**
 * @author Alex Barter
 */
public class English extends ILanguage {

	public static NGramData quadgramData;
	public static NGramData diagramData;
	public static Map<Character, Double> frequencyMap;
	
	@Override
	public Map<Character, Double> getCharacterFrequency() {
		if(frequencyMap == null) {
			frequencyMap = new HashMap<Character, Double>();
			frequencyMap.put('A', 8.167D);
			frequencyMap.put('B', 1.492D);
			frequencyMap.put('C', 2.782D);
			frequencyMap.put('D', 4.253D);
			frequencyMap.put('E', 12.702D);
			frequencyMap.put('F', 2.228D);
			frequencyMap.put('G', 2.015D);
			frequencyMap.put('H', 6.094D);
			frequencyMap.put('I', 6.996D);
			frequencyMap.put('J', 0.153D);
			frequencyMap.put('K', 0.772D);
			frequencyMap.put('L', 4.025D);
			frequencyMap.put('M', 2.406D);
			frequencyMap.put('N', 6.749D);
			frequencyMap.put('O', 7.507D);
			frequencyMap.put('P', 1.929D);
			frequencyMap.put('Q', 0.095D);
			frequencyMap.put('R', 5.987D);
			frequencyMap.put('S', 6.327D);
			frequencyMap.put('T', 9.056D);
			frequencyMap.put('U', 2.758D);
			frequencyMap.put('V', 0.978D);
			frequencyMap.put('W', 2.360D);
			frequencyMap.put('X', 0.150D);
			frequencyMap.put('Y', 1.974D);
			frequencyMap.put('Z', 0.074D);	
		}
		return frequencyMap;
	}
	
	@Override
	public void loadNGramData() {
		quadgramData = TextFitness.loadFile("/javalibrary/fitness/english_quadgrams.txt");
		//diagramData = TextFitness.loadFile("/javalibrary/fitness/english_bigrams_1.txt");
	}
	
	@Override
	public NGramData getQuadgramData() {
		return quadgramData;
	}
	
	@Override
	public NGramData getDiagramData() {
		return diagramData;
	}
	
	@Override
	public String getName() {
		return "English";
	}
	
	@Override
	public String getImagePath() {
		return "/image/english_flag.png";
	}

	@Override
	public double getNormalCoincidence() {
		return 0.0667D;
	}
}