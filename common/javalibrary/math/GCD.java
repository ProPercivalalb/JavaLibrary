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
}
