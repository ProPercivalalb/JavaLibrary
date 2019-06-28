package javalibrary.math;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javalibrary.util.ArrayUtil;

public class FermatsFactorisation {

    public static void main(String[] args) {
    	Collection<Integer> factors = fetchAllFactors(32145*7*2);
    	System.out.println("" + factors);
    	
        /**
        int min = Integer.MAX_VALUE;
        for(int i = 0; i < 40000; i++) {
            int n = 2146754366 + i;
            int c = process(n);

            if(c < min) {
                System.out.println("c: " + c + " n: " + n);
                min = c;
            }
        }**/
    }

    public static Collection<Integer> fetchAllFactors(int n) {
    	
    	Collection<Integer> primefactors = new HashSet<>();
    	Set<Integer> factors = new HashSet<>();
    	factors.add(n);
    	while(!factors.isEmpty()) {
    		int first = factors.iterator().next();
    		int[] result = processOdd(first);
    		if(result[0] != 1)
    		factors.add(result[0]);
    		if(result[1] != 1)
    		factors.add(result[1]);
    		
    		if(result[1] == 1) { // is prime
    			primefactors.add(result[0]);
    		}
    		factors.remove(first);
    			
    	}
    	
    	return primefactors;
    }
    
    public static int[] process(int n) {

        int c = 0;
        while(n % 2 == 0) {
            n /= 2;
            c++;
        }

        
        return ArrayUtil.concat(processOdd(n), new int[] {c});
    }
    
    
    public static int[] processOdd(int n) {
    	assert n % 2 == 1 : " must be odd";
	    int x = (int)Math.ceil(Math.sqrt(n)) - 1;
	    int r;
	
	    int count = 0;
	    do {
	        x = x + 1;
	        r = (int)Math.pow(x, 2) - n;
	        count++;
	    }
	    while(!isSquare(r));
	
	    int a = x + (int)Math.sqrt(r);
	    int b = x - (int)Math.sqrt(r);
	    //System.out.println("" + count);
	    //System.out.println("a: " + a + " b: " + b + " c: " + c);
	    return new int[] {a,b};
    }

    public static boolean isSquare(int n) {
        return Math.floor(Math.sqrt(n)) == Math.ceil(Math.sqrt(n));
    }
}
