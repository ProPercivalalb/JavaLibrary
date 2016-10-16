package javalibrary.dict;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Dictionary {

	public static List<String> words = new ArrayList<String>();
	
	public static boolean containsWord(String word) {
		return words.contains(word);
	}
	
	public static int wordCount() {
		return words.size();
	}
	
	public static void onLoad() {
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Dictionary.class.getResourceAsStream("/javalibrary/dict/english_words.txt")));
			while(true) {
				String line = bufferedReader.readLine();
				if(line == null) break;
				if(line.isEmpty() || line.startsWith("#")) continue;
	
				words.add(line.split(" ")[0].toUpperCase());
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}