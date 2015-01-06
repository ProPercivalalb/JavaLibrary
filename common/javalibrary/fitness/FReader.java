package javalibrary.fitness;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;

import javalibrary.string.StringAnalyzer;

/**
 * @author Alex Barter (10AS)
 */
public class FReader {

	public static final ArrayList<String> MAP = new ArrayList<String>();
	
	
	static {
		InputStream inputStream = FReader.class.getResourceAsStream("/javalibrary/fitness/test.txt");
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		
		double total = 0.0F;
		
		try {
			String currentLine = "";
			while((currentLine = reader.readLine()) != null) {
				if(currentLine.isEmpty()) 
					continue;
				
				MAP.add(currentLine);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}
