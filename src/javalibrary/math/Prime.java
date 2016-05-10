package javalibrary.math;

import java.math.BigInteger;

import javalibrary.lib.Timer;
import javalibrary.util.RandomUtil;

public class Prime {

	public static void main(String[] args) {
		Timer timer = new Timer();
		int number = 421412412;
		System.out.println(number + " " + isPrime(number));
		timer.displayTime();
		BigInteger.ZERO.isProbablePrime(1);
	}
	
	public static boolean isPrime(int number) {
		int s = number - 1;
		int t = 0;
		
		while(s % 2 == 0) {
			s = (int)Math.floor(s / 2.0D);
			t++;
		}
		System.out.println(s + " " + t);
		
		for(int trial = 0; trial < 5; trial++) {
			int a = RandomUtil.pickRandomInt(2, number - 2);
			int v = (int)(Math.pow(a, s)) % number;
			if(v != 1) {
				int i = 0;
				System.out.println(v);
				while(v != number - 1) {
					if(i == t - 1)
						return false;
					else {
						i++;
						v = (int)(Math.pow(v, 2)) % number;
					}
				}
			}
		}
		
		return true;
	}
	
}
