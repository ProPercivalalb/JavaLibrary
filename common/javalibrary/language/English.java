package javalibrary.language;

import javalibrary.fitness.NGramData;
import javalibrary.fitness.QuadgramStats;

/**
 * @author Alex Barter
 */
public class English implements ILanguage {

	public static NGramData quadgramData;
	
	@Override
	public double getFrequencyOfLetter(char character) {
		if(character == 'A') return 8.167D; if(character == 'B') return 1.492D;  if(character == 'C') return 2.782D; 
		if(character == 'D') return 4.253D; if(character == 'E') return 12.702D; if(character == 'F') return 2.228D; 
		if(character == 'G') return 2.015D; if(character == 'H') return 6.094D;  if(character == 'I') return 6.996D; 
		if(character == 'J') return 0.153D; if(character == 'K') return 0.772D;  if(character == 'L') return 4.025D;
		if(character == 'M') return 2.406D; if(character == 'N') return 6.749D;  if(character == 'O') return 7.507D; 
        if(character == 'P') return 1.929D; if(character == 'Q') return 0.095D;  if(character == 'R') return 5.987D; 
        if(character == 'S') return 6.327D; if(character == 'T') return 9.056D;  if(character == 'U') return 2.758D; 
        if(character == 'V') return 0.978D; if(character == 'W') return 2.360D;  if(character == 'X') return 0.150D; 
        if(character == 'Y') return 1.974D; if(character == 'Z') return 0.074D;
        return 0.0D;
	}

	@Override
	public double getMaxFrequency() {
		return 12.702D;
	}
	
	@Override
	public NGramData getQuadgramData() {
		if(quadgramData == null)
			quadgramData = QuadgramStats.loadFile("/javalibrary/fitness/english_quadgrams.txt");
		
		return quadgramData;
	}

	@Override
	public String getName() {
		return "English";
	}
}