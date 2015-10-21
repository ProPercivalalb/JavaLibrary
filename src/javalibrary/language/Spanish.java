package javalibrary.language;

import java.util.HashMap;
import java.util.Map;

import javalibrary.fitness.NGramData;
import javalibrary.fitness.QuadgramStats;

/**
 * @author Alex Barter
 */
public class Spanish extends ILanguage {

	public static NGramData quadgramData;
	public static Map<Character, Double> frequencyMap;
	
	@Override
	public Map<Character, Double> getCharacterFrequency() {
		if(frequencyMap == null) {
			frequencyMap = new HashMap<Character, Double>();
			frequencyMap.put('A', 11.525D);
			frequencyMap.put('B', 2.215D);
			frequencyMap.put('C', 4.019D);
			frequencyMap.put('D', 5.010D);
			frequencyMap.put('E', 12.181D);
			frequencyMap.put('F', 0.692D);
			frequencyMap.put('G', 1.768D);
			frequencyMap.put('H', 0.703D);
			frequencyMap.put('I', 6.247D);
			frequencyMap.put('J', 0.493D);
			frequencyMap.put('K', 0.011D);
			frequencyMap.put('L', 4.967D);
			frequencyMap.put('M', 3.157D);
			frequencyMap.put('N', 6.712D);
			frequencyMap.put('O', 8.683D);
			frequencyMap.put('P', 2.510D);
			frequencyMap.put('Q', 0.877D);
			frequencyMap.put('R', 6.871D);
			frequencyMap.put('S', 7.977D);
			frequencyMap.put('T', 4.632D);
			frequencyMap.put('U', 2.927D);
			frequencyMap.put('V', 1.138D);
			frequencyMap.put('W', 0.017D);
			frequencyMap.put('X', 0.215D);
			frequencyMap.put('Y', 1.008D);
			frequencyMap.put('Z', 0.467D);	
		}
		return frequencyMap;
	}
	
	@Override
	public void loadNGramData() {
		quadgramData = QuadgramStats.loadFile("/javalibrary/fitness/spanish_quadgrams.txt");
	}
	
	@Override
	public NGramData getQuadgramData() {
		return quadgramData;
	}
	
	@Override
	public String getName() {
		return "Spanish";
	}
	
	@Override
	public String getImagePath() {
		return "/image/spanish_flag.png";
	}

	@Override
	public double getNormalCoincidence() {
		return 0.0770D;
	}
}