package javalibrary.math;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alex Barter
 * @since 08 Oct 2013
 */
public class MathHelper {

	public static double PI =  Math.PI;
	public static double E  =  Math.E;
	
	public static int findLargestInt(Iterable<Integer> numberArray) {	
		int largest = Integer.MIN_VALUE;
		
		for(int number : numberArray)
			largest = Math.max(largest, number);
		
		return largest;
	}

	public static double findLargestDouble(Iterable<Double> numberArray) {	
		double largest = Double.MIN_VALUE;
		
		for(double number : numberArray)
			largest = Math.max(largest, number);
		
		return largest;
	}

	public static int findSmallestInt(Iterable<Integer> numberArray) {
		int smallest = Integer.MAX_VALUE;
		
		for(int number : numberArray)
			smallest = Math.min(smallest, number);
		
		return smallest;
	}
	
	public static double findSmallestDouble(Iterable<Double> numberArray) {
		double smallest = Double.MAX_VALUE;
		
		for(double number : numberArray)
			smallest = Math.min(smallest, number);
		
		return smallest;
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
	
	public static float wrap(float target, float lowerLimit, float upperLimit) {
		float difference = upperLimit - lowerLimit;
		
		while(target >= upperLimit)
			target -= difference;
		while(target < lowerLimit)
			target += difference;
		return target;
	}
	
	public static double wrap(double target, double lowerLimit, double upperLimit) {
		double difference = upperLimit - lowerLimit;
		
		while(target >= upperLimit)
			target -= difference;
		while(target < lowerLimit)
			target += difference;
		return target;
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
		List<Integer> factors = getFactors(target);
		//If there are 2 or less factors it is deemed to be a prime number 
		return factors.size() <= 2;
	}
	
	public static List<Integer> getSquareFactors(int target) {
		//Gets all the factors of the given number
		List<Integer> factors = getFactors(target);
		List<Integer> squareFactors = new ArrayList<Integer>();
		
		for(Integer factor : factors) {
			int n = (int)Math.floor(Math.sqrt(factor));
			if(Math.pow(n, 2) == factor)
				squareFactors.add(factor);
		}
		return squareFactors;
	}
	
	public static List<Integer> getOddFactors(int target) {
		//Gets all the factors of the given number
		List<Integer> factors = getFactors(target);
		List<Integer> oddFactors = new ArrayList<Integer>();
		
		for(Integer factor : factors) {
			if(factor % 2 == 1)
				oddFactors.add(factor);
		}
		return oddFactors;
	}
	
	public static List<Integer> getFactors(int target) {
		List<Integer> factors = new ArrayList<Integer>();
		int incrementer = 1;
        if((target & 1) == 1) incrementer = 2;

        for(int i = 1; i <= target / 2; i += incrementer)
            if(target % i == 0)
            	factors.add(i);

        factors.add(target);
		return factors;
	}
	
	/**
	 * Finds the multiplicative factors of a given {@link Integer}
	 * @return A list containing {@link Integer[]} with a length of 2 containing the pairs of multiplicative factors
	 */
	public static ArrayList<Integer[]> getMultiplicativeFactors(int mod) {
		ArrayList<Integer[]> list = new ArrayList<Integer[]>();
		
		for(int a = 1; a < mod; ++a)
			for(int b = 1; b < mod; ++b)
				if((a * b) % mod == 1)
					list.add(new Integer[] {a, b});

		return list;
	}
	
	public static int factorial(int n) {
		int fact = 1; 
	    for(int i = 1; i <= n; i++) 
	    	fact *= i;
	    return fact;
	}
	
	public static BigInteger factorialBig(int n) {
		BigInteger fact = BigInteger.ONE;
	    for(int i = 1; i <= n; i++) 
	    	fact = fact.multiply(BigInteger.valueOf(i));
	    return fact;
	}

	public static int sumInt(Iterable<Integer> values) {
		int sum = 0;
		for(int i : values)
			sum += i;
		return sum;
	}
	
	public static double sumDouble(Iterable<Double> values) {
		double sum = 0;
		for(double i : values)
			sum += i;
		return sum;
	}
	
	public static int product(Iterable<Integer> values) {
		int product = 0;
		for(int i : values)
			product *= i;
		return product;
	}
	
	public static int mod(int number, int mod) {
		return ((number % mod) + mod) % mod;
	}
}
