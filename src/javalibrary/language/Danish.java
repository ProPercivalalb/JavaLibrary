package javalibrary.language;

import javalibrary.fitness.NGramData;
import javalibrary.fitness.QuadgramStats;

/**
 * @author Alex Barter
 */
public class Danish implements ILanguage {

	public static NGramData quadgramData;
	
	@Override
	public double getFrequencyOfLetter(char character) {
		if(character == 'A') return 6.025D; if(character == 'B') return 2.000D;  if(character == 'C') return 0.565D; 
		if(character == 'D') return 5.858D; if(character == 'E') return 15.453D; if(character == 'F') return 2.406D; 
		if(character == 'G') return 4.077D; if(character == 'H') return 1.621D;  if(character == 'I') return 6.000D; 
		if(character == 'J') return 0.730D; if(character == 'K') return 3.395D;  if(character == 'L') return 5.229D;
		if(character == 'M') return 3.237D; if(character == 'N') return 7.240D;  if(character == 'O') return 4.636D; 
        if(character == 'P') return 1.756D; if(character == 'Q') return 0.007D;  if(character == 'R') return 8.956D; 
        if(character == 'S') return 5.805D; if(character == 'T') return 6.862D;  if(character == 'U') return 1.979D; 
        if(character == 'V') return 2.332D; if(character == 'W') return 0.069D;  if(character == 'X') return 0.028D; 
        if(character == 'Y') return 0.698D; if(character == 'Z') return 0.034D;
        return 0.0D;
	}

	@Override
	public double getMaxFrequency() {
		return 15.453D;
	}
	
	@Override
	public void loadNGramData() {
		quadgramData = QuadgramStats.loadFile("/javalibrary/fitness/danish_quadgrams.txt");
	}
	
	@Override
	public NGramData getQuadgramData() {
		return quadgramData;
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
		return 0;
	}
}