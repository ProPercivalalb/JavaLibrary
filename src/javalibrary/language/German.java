package javalibrary.language;

import javalibrary.fitness.NGramData;
import javalibrary.fitness.QuadgramStats;

/**
 * @author Alex Barter
 */
public class German implements ILanguage {
	
	public static NGramData quadgramData;
	
	@Override
	public double getFrequencyOfLetter(char character) {
		character = Character.toUpperCase(character);
		if(character == 'A') return 6.516D; if(character == 'B') return 1.886D;  if(character == 'C') return 2.732D; 
		if(character == 'D') return 5.076D; if(character == 'E') return 16.396D; if(character == 'F') return 1.656D; 
		if(character == 'G') return 3.009D; if(character == 'H') return 4.577D;  if(character == 'I') return 6.550D; 
		if(character == 'J') return 0.268D; if(character == 'K') return 1.417D;  if(character == 'L') return 3.437D;
		if(character == 'M') return 2.534D; if(character == 'N') return 9.776D;  if(character == 'O') return 2.594D; 
        if(character == 'P') return 0.670D; if(character == 'Q') return 0.018D;  if(character == 'R') return 7.003D; 
        if(character == 'S') return 7.273D; if(character == 'T') return 6.154D;  if(character == 'U') return 4.166D; 
        if(character == 'V') return 0.846D; if(character == 'W') return 1.921D;  if(character == 'X') return 0.034D; 
        if(character == 'Y') return 0.039D; if(character == 'Z') return 1.134D;
        return 0.0D;
	}

	@Override
	public double getMaxFrequency() {
		return 16.396D;
	}
	
	@Override
	public NGramData getQuadgramData() {
		if(quadgramData == null)
			quadgramData = QuadgramStats.loadFile("/javalibrary/fitness/german_quadgrams.txt");
		
		return quadgramData;
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