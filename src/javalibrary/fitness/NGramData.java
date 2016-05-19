package javalibrary.fitness;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * @author Alex Barter (10AS)
 */
public class NGramData {

	//public HashMap<String, Double> mapping;
	public double[] valueMapping;
	public double floor;
	public double fitnessPerChar;

	public NGramData(HashMap<String, Double> mapping, double floor, double fitnessPerChar) {
		//this.mapping = mapping;
		this.floor = floor;
		this.fitnessPerChar = fitnessPerChar;
		this.valueMapping = new double[456976];
		Arrays.fill(this.valueMapping, floor);
		
		for(String key : mapping.keySet()) {

			int value = (key.charAt(0) - 'A') * 17576 + (key.charAt(1) - 'A') * 676 + (key.charAt(2) - 'A') * 26 + key.charAt(3) - 'A';
			if(value < 0 || value > this.valueMapping.length - 1)
				continue;
		
			if(this.valueMapping[value] != this.floor)
				System.err.println("Mapping overlap ngramdata");
			else
				this.valueMapping[value] = mapping.get(key);
		}
	}
	

	
	public double getValue(char[] gram, int startIndex) {
		int intConversion = (gram[startIndex] - 65) * 17576 + (gram[startIndex + 1] - 65) * 676 + (gram[startIndex + 2] - 65) * 26 + gram[startIndex + 3] - 65;
		
		if(intConversion < 0 || intConversion > this.valueMapping.length - 1)
			return this.floor;
		return this.valueMapping[intConversion];
	}
	
	//public double getValue(String gram) {
	//	return valueMapping.get((gram.charAt(0) - 65) * 17576 + (gram.charAt(1) - 65) * 676 + (gram.charAt(2) - 65) * 26 + gram.charAt(3) - 65);
	//}
}
