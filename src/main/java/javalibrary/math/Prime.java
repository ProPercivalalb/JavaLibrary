package javalibrary.math;

import java.math.BigInteger;

import javalibrary.lib.Timer;
import javalibrary.util.RandomUtil;

public class Prime {

	public static void main(String[] args) {
		Timer timer = new Timer();
		
		for(int number = 0; number < 100; number++)
			System.out.println(number + " " + isPrime(number));
		int number = 421412412;
		timer.displayTime();
		BigInteger.ZERO.isProbablePrime(1);
	}
	
	//Trial division
	public static boolean isPrime(int number) {
		if(number % 2 == 0 || number % 3 == 0)
			return false;
		
		int i = 6;
		
		do {
			if((int)(number / (i - 1)) * (i - 1) == number || (int)(number / (i + 1)) * (i + 1) == number)
				return false;
			i += 6;
		}
		while(i < Math.sqrt(number));

		
		return true;
	}
	
	//Miller-Rabin test
	public static boolean isPrimeRqab(int number) {
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
