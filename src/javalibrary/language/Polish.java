package javalibrary.language;

import java.util.HashMap;
import java.util.Map;

import javalibrary.fitness.NGramData;
import javalibrary.fitness.TextFitness;

/**
 * @author Alex Barter
 */
public class Polish extends ILanguage {

	public static NGramData quadgramData;
	public static NGramData trigramData;
	public static Map<Character, Double> frequencyMap;
	
	@Override
	public Map<Character, Double> getCharacterFrequency() {
		if(frequencyMap == null) {
			frequencyMap = new HashMap<Character, Double>();
			frequencyMap.put('A', 10.503D);
			frequencyMap.put('B', 1.740D);
			frequencyMap.put('C', 3.895D);
			frequencyMap.put('D', 3.725D);
			frequencyMap.put('E', 7.352D);
			frequencyMap.put('F', 0.143D);
			frequencyMap.put('G', 1.731D);
			frequencyMap.put('H', 1.015D);
			frequencyMap.put('I', 8.328D);
			frequencyMap.put('J', 1.836D);
			frequencyMap.put('K', 2.753D);
			frequencyMap.put('L', 2.564D);
			frequencyMap.put('M', 2.515D);
			frequencyMap.put('N', 6.237D);
			frequencyMap.put('O', 6.667D);
			frequencyMap.put('P', 2.445D);
			frequencyMap.put('Q', 0.0D);
			frequencyMap.put('R', 5.243D);
			frequencyMap.put('S', 5.224D);
			frequencyMap.put('T', 2.475D);
			frequencyMap.put('U', 2.062D);
			frequencyMap.put('V', 0.012D);
			frequencyMap.put('W', 5.813D);
			frequencyMap.put('X', 0.004D);
			frequencyMap.put('Y', 3.206D);
			frequencyMap.put('Z', 4.852D);	
		}
		return frequencyMap;
	}
	
	@Override
	public void loadNGramData() {
		quadgramData = TextFitness.loadFile("/javalibrary/fitness/polish_quadgrams.txt");
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
		return "Polish";
	}
	
	@Override
	public String getImagePath() {
		return "/image/polish_flag.png";
	}

	@Override
	public double getNormalCoincidence() {
		return 0.0584D;
	}
}