package javalibrary.streams;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javalibrary.fitness.TextFitness;

public class FileReader {
	
	public static List<String> compileTextFromFile(String filePath) {
		return compileTextFromFile(new File(filePath));
	}
	
	public static List<String> compileTextFromFile(File file) {
		List<String> list = new ArrayList<String>();
		
		if(!file.exists()) return list;
		
		try {
			FileInputStream fIn = new FileInputStream(file);
			BufferedReader reader = new BufferedReader(new InputStreamReader(fIn));
			
			String line = "";
			while((line = reader.readLine()) != null)
				list.add(line);
			
			fIn.close();
		} 
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	public static List<String> compileTextFromResource(String resourcePath) {
		List<String> list = new ArrayList<String>();
		
		InputStream inputStream = TextFitness.class.getResourceAsStream(resourcePath);

		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			
		try {
			String line = "";
			while((line = reader.readLine()) != null)
				list.add(line);
		} 
		catch(Exception e) {
			e.printStackTrace();
		}
				
		
		return list;
	}

}
