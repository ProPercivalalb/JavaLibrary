package javalibrary.language;

import java.util.HashMap;
import java.util.Map;

import javalibrary.fitness.NGramData;
import javalibrary.fitness.QuadgramStats;

/**
 * @author Alex Barter
 */
public class Finnish extends ILanguage {

	public static NGramData quadgramData;
	public static Map<Character, Double> frequencyMap;
	
	@Override
	public Map<Character, Double> getCharacterFrequency() {
		if(frequencyMap == null) {
			frequencyMap = new HashMap<Character, Double>();
			frequencyMap.put('A', 12.217D);
			frequencyMap.put('B', 0.281D);
			frequencyMap.put('C', 0.281D);
			frequencyMap.put('D', 1.043D);
			frequencyMap.put('E', 7.968D);
			frequencyMap.put('F', 0.194D);
			frequencyMap.put('G', 0.392D);
			frequencyMap.put('H', 1.851D);
			frequencyMap.put('I', 10.817D);
			frequencyMap.put('J', 2.042D);
			frequencyMap.put('K', 4.973D);
			frequencyMap.put('L', 5.761D);
			frequencyMap.put('M', 3.202D);
			frequencyMap.put('N', 8.826D);
			frequencyMap.put('O', 5.614D);
			frequencyMap.put('P', 1.842D);
			frequencyMap.put('Q', 0.013D);
			frequencyMap.put('R', 2.872D);
			frequencyMap.put('S', 7.862D);
			frequencyMap.put('T', 8.750D);
			frequencyMap.put('U', 5.008D);
			frequencyMap.put('V', 2.250D);
			frequencyMap.put('W', 0.094D);
			frequencyMap.put('X', 0.031D);
			frequencyMap.put('Y', 1.745D);
			frequencyMap.put('Z', 0.051D);	
		}
		return frequencyMap;
	}
	
	@Override
	public void loadNGramData() {
		quadgramData = QuadgramStats.loadFile("/javalibrary/fitness/finnish_quadgrams.txt");
	}
	
	@Override
	public NGramData getQuadgramData() {
		return quadgramData;
	}
	
	@Override
	public String getName() {
		return "Finnish";
	}
	
	@Override
	public String getImagePath() {
		return "/image/finnish_flag.png";
	}

	@Override
	public double getNormalCoincidence() {
		return 0;
	}
}