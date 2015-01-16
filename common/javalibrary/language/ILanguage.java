package javalibrary.language;

import javalibrary.fitness.NGramData;

/**
 * @author Alex Barter
 */
public interface ILanguage {
	
	public double getFrequencyOfLetter(char character);

	public double getMaxFrequency();
	
	public NGramData getQuadgramData();
	
	public String getName();
}