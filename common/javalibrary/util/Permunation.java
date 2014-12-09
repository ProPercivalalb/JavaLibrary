package javalibrary.util;

import java.util.ArrayList;

/**
 * @author Alex Barter (10AS)
 */
public class Permunation {

	public static void permutation(String prefix, String str, ArrayList<String> list) {
	    int n = str.length();
	    if (n == 0) {
	    	list.add(prefix);
	    }
	    else {
	        for (int i = 0; i < n; i++)
	            permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i + 1, n), list);
	    }
	}
}
