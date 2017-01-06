package javalibrary.fitness;

import java.util.Arrays;
import java.util.HashMap;

import javalibrary.math.MathUtil;

/**
 * @author Alex Barter (10AS)
 */
public class NGramData {

	public double[] valueMapping;
	public int nGram;
	public int[] powValues;
	public double floor;
	public double fitnessPerChar;
	public double maxValue;

	public NGramData(HashMap<String, Double> mapping, double floor, double fitnessPerChar, int nGram) {
		this.floor = floor;
		this.fitnessPerChar = fitnessPerChar;
		this.nGram = nGram;
		this.powValues = new int[nGram];
		for(int i = 0; i < this.powValues.length; i++)
			this.powValues[this.powValues.length - i - 1] = (int)Math.pow(26, i);
		this.maxValue = MathUtil.findSmallestDouble(mapping.values());
		
		this.valueMapping = new double[(int)Math.pow(26, nGram)];
		Arrays.fill(this.valueMapping, floor);
		
		for(String key : mapping.keySet()) {

			int value = 0;
			for(int i = 0; i < this.nGram; i++)
				value += (key.charAt(i) - 'A') * this.powValues[i];
			
			if(value < 0 || value > this.valueMapping.length - 1)
				continue;
		
			if(this.valueMapping[value] != this.floor)
				System.err.println("Mapping overlap ngramdata");
			else
				this.valueMapping[value] = mapping.get(key);
		}
	}
	
	public double getValue(char[] gram, int startIndex) {
		int intConversion = 0;
		for(int i = startIndex; i < startIndex + this.nGram; i++) {
			if(gram[i] < 'A' || gram[i] > 'Z') return this.floor;
			intConversion += (gram[i] - 'A') * this.powValues[i - startIndex];
		}
		
		return this.valueMapping[intConversion];
	}
	
	//Byte array version of method above
	public double getValue(byte[] gram, int startIndex) {
		int intConversion = 0;
		for(int i = startIndex; i < startIndex + this.nGram; i++) {
			if(gram[i] < 'A' || gram[i] > 'Z') return this.floor;
			intConversion += (gram[i] - 'A') * this.powValues[i - startIndex];
		}
		
		return this.valueMapping[intConversion];
	}
}
