package javalibrary.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author Alex Barter (10AS)
 */
public class RawInputReader {

	/**
	 * Asks the user for input from the Console
	 * @param promptText A String to imform the user that input is required
	 * @param sameLine Whether the user types on the same line as the promptText
	 * @return What the user typed in
	 */
	public static String rawInput(String promptText, boolean sameLine) {
	    if(sameLine) 
	    	System.out.print(promptText);
	    else 
	    	System.out.println(promptText);
	    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	    String rawInput = "";
	    try {
	    	rawInput = br.readLine();
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    return rawInput;
	}
}
