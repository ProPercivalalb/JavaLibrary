package javalibrary.language;

import javalibrary.fitness.NGramData;
import javalibrary.fitness.QuadgramStats;

/**
 * @author Alex Barter
 */
public class French implements ILanguage {

	public static NGramData quadgramData;
	
	@Override
	public double getFrequencyOfLetter(char character) {
		character = Character.toUpperCase(character);
		if(character == 'A') return 7.636D; if(character == 'B') return 0.901D;  if(character == 'C') return 3.260D; 
		if(character == 'D') return 3.669D; if(character == 'E') return 14.715D; if(character == 'F') return 1.066D; 
		if(character == 'G') return 0.866D; if(character == 'H') return 0.737D;  if(character == 'I') return 7.529D; 
		if(character == 'J') return 0.613D; if(character == 'K') return 0.049D;  if(character == 'L') return 5.456D;
		if(character == 'M') return 2.968D; if(character == 'N') return 7.095D;  if(character == 'O') return 5.598D; 
        if(character == 'P') return 2.521D; if(character == 'Q') return 1.362D;  if(character == 'R') return 6.693D; 
        if(character == 'S') return 7.948D; if(character == 'T') return 7.244D;  if(character == 'U') return 6.311D; 
        if(character == 'V') return 1.838D; if(character == 'W') return 0.074D;  if(character == 'X') return 0.427D; 
        if(character == 'Y') return 0.128D; if(character == 'Z') return 0.326D;
        return 0.0D;
	}

	@Override
	public double getMaxFrequency() {
		return 14.715D;
	}


	@Override
	public void loadNGramData() {
		quadgramData = QuadgramStats.loadFile("/javalibrary/fitness/french_quadgrams.txt");
	}
	
	@Override
	public NGramData getQuadgramData() {
		return quadgramData;
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