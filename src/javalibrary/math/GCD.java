package javalibrary.math;

public class GCD {

	public static int hcf(int no1, int no2) {
		int x = no1;
		int y = no2;
		while(true) {
			int mutliplier = (int)Math.floor(x/y);
			int rem = x - y * mutliplier;
			if(rem == 0) {
				System.out.println(no1 + " " + no2 + " " + y);
				return y;
			}
			x = y;
			y = rem;
		}
	}
	
	public static double hcf(double no1, double no2) {
		double x = no1;
		double y = no2;
		while(true) {
			int mutliplier = (int)Math.floor(x/y);
			double rem = x - y * mutliplier;
			if(rem == 0) {
				
				return y;
			}
			x = y;
			y = rem;
		}
	}
	
	public static int gcd(int a, int b) {
		if(b == 0) return a;
		return gcd(b, a % b);
	}
	
	public static int gcd(int[] input) {
		int result = input[0];
	    for(int i = 1; i < input.length; i++) 
	    	result = gcd(result, input[i]);
	    return result;
	}
	
	//Lowest Common Multiple
	public static int lcm(int a, int b) {
	    return (a * b) / gcd(a, b);
	}
	
	public static int lcm(int... input) {
	    int result = input[0];
	    for(int i = 1; i < input.length; i++) 
	    	result = lcm(result, input[i]);
	    return result;
	}
}
