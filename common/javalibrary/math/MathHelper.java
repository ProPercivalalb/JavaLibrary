package javalibrary.math;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alex Barter
 * @since 08 Oct 2013
 */
public class MathHelper {

	public static double PI =  Math.PI;
	public static double E  =  Math.E;
	
	/**
	 * Looks in the given list and returns the biggest value
	 * @param numberArray A array of integers
	 * @return The biggest number from the given array
	 */
	public static double findBiggest(double... numberArray) {
		if(numberArray.length <= 0)
			return 0;
		
		double currentBiggest = Double.MIN_VALUE;
		for(double number : numberArray) {
			if(currentBiggest < number) {
				currentBiggest = number;
			}
		}
		
		return currentBiggest;
	}
	
	public static double findSmallest(double... numberArray) {
		if(numberArray.length <= 0)
			return 0;
		
		double currentBiggest = Double.MAX_VALUE;
		for(double number : numberArray) {
			if(currentBiggest > number) {
				currentBiggest = number;
			}
		}
		
		return currentBiggest;
	}
	
	public static double findBiggest(List<Double> numberArray) {
		if(numberArray.size() <= 0)
			return 0;
		
		double currentBiggest = Double.MIN_VALUE;
		for(double number : numberArray) {
			if(currentBiggest < number) {
				currentBiggest = number;
			}
		}
		
		return currentBiggest;
	}
	
	public static double findSmallest(List<Double> numberArray) {
		if(numberArray.size() <= 0)
			return 0;
		
		double currentBiggest = Double.MAX_VALUE;
		for(double number : numberArray) {
			if(currentBiggest > number) {
				currentBiggest = number;
			}
		}
		
		return currentBiggest;
	}
	
	public static double round(double target, int decimalPlaces) {
		double power = Math.pow(10, decimalPlaces);
		return Math.round(target * power) / power;
	}
	
	public static int difference(int i, int j) {
		if(i == j)
			return 0;
		return j - i;
	}
	
	public static int wrap(int target, int lowerLimit, int upperLimit) {
		int difference = upperLimit - lowerLimit;
		
		while(target >= upperLimit)
			target -= difference;
		while(target < lowerLimit)
			target += difference;
		return target;
	}
	
	public static int roundDownToNearest(int target, int factor) {
		if(target % factor == 0)
			return target;
		return target - target % factor;
	}
	
	public static int roundUpToNearest(int target, int factor) {
		if(target % factor == 0)
			return target;
		return target + factor - target % factor;
	}
	
	public static int wrap(int target, int lowerLimit, int upperLimit, int amountToChangeBy) {
		while(target >= upperLimit)
			target -= amountToChangeBy;
		while(target < lowerLimit)
			target += amountToChangeBy;
		return target;
	}
	
	/**
	 * Discovers whether the number is a prime number
	 * @param target The number to that is being checked
	 * @return If the number is only divisible by 1 and it's self
	 */
	public static boolean isPrimeNumber(int target) {
		//Gets all the factors of the given number
		ArrayList<Integer> factors = getFactors(target);
		//If there are 2 or less factors it is deemed to be a prime number 
		return factors.size() <= 2;
	}
	
	public static ArrayList<Integer> getFactors(int target) {
		ArrayList<Integer> factors = new ArrayList<Integer>();
		for(int i = 1; i <= (target + 1) / 2; ++i) {
			int answer = target / i;
			if(answer * i == target) {
				if(!factors.contains(i))
					factors.add(i);
				if(!factors.contains(answer))
					factors.add(answer);
			}
		}
		return factors;
	}
	
	/**
	 * Finds the multiplicative factors of a given {@link Integer}
	 * @return A list containing {@link Integer[]} with a length of 2 containing the pairs of multiplicative factors
	 */
	public static ArrayList<Integer[]> getMultiplicativeFactors(int mod) {
		ArrayList<Integer[]> list = new ArrayList<Integer[]>();
		
		for(int a = 1; a < mod; ++a) {
			for(int b = 1; b < mod; ++b) {
				int wrapped = wrap(a * b, 0, mod);
				if(wrapped == 1) {
					list.add(new Integer[] {a, b});
				}
			}
		}
		return list;
	}
}
