package javalibrary.language;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javalibrary.fitness.NGramData;
import javalibrary.math.MathUtil;
import javalibrary.util.MapHelper;

/**
 * @author Alex Barter
 */
public abstract class ILanguage {
	
	public abstract Map<Character, Double> getCharacterFrequency();
	
	public double getFrequencyOfLetter(char character) {
		return this.getCharacterFrequency().get(character);
	}
	
	public char[] getFrequencyLargest() {
		return new char[0];
	}
	
	public List<Character> getLetterLargestFirst() {
		Map<Character, Double> order = MapHelper.sortMapByValue(this.getCharacterFrequency(), false);
		return new ArrayList<Character>(order.keySet());
	}
	
	public List<Double> getFrequencyLargestFirst() {
		List<Double> list = new ArrayList<Double>(this.getCharacterFrequency().values());
		Collections.sort(list, Collections.reverseOrder());
		return list;
	}
	
	public List<Double> getFrequencySmallestFirst() {
		List<Double> list = new ArrayList<Double>(this.getCharacterFrequency().values());
		Collections.sort(list);
		return list;
	}

	public double getMaxFrequency() {
		return MathUtil.findLargestDouble(this.getCharacterFrequency().values());
	}
	
	public double getMinFrequency() {
		return MathUtil.findSmallestDouble(this.getCharacterFrequency().values());
	}
	
	public abstract void loadNGramData();
	
	public abstract NGramData getQuadgramData();
	
	public abstract NGramData getDiagramData();
	
	public NGramData getTrigramData() {
		return null;
	}
	
	public abstract double getNormalCoincidence();
	
	public abstract String getName();
	
	public abstract String getImagePath();
}