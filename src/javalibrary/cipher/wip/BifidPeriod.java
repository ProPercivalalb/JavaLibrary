package javalibrary.cipher.wip;

import java.util.HashMap;

import javalibrary.math.MathHelper;

public class BifidPeriod {

	public static void calculatePeriod(String cipherText) {
		for(int step = 1; step < 20; step++) {
			HashMap<String, Integer> counts = new HashMap<String, Integer>();
			for(int i = 0; i < cipherText.length() - step; i++) {
				String s = cipherText.charAt(i) + "" + cipherText.charAt(i + step);
				counts.put(s, counts.containsKey(s) ? counts.get(s) + 1 : 1);
			}
			
			double average =  MathHelper.sum(counts.values()) / (double)counts.size();
		    
		    double totalDiff = 0.0D;
		    for(String s : counts.keySet()) {
		    	double diff = average - counts.get(s);
		    	totalDiff += Math.pow(diff, 2);
		    }
		    double variance = totalDiff / counts.size();
		    System.out.println("Variance: " + variance);
		    
		}
	}
}
