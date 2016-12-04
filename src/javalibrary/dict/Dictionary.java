package javalibrary.dict;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Dictionary {

	public static List<String> WORDS = new ArrayList<String>();
	public static List<char[]> WORDS_CHAR = new ArrayList<char[]>();
	
	public static boolean containsWord(String word) {
		return WORDS.contains(word);
	}
	
	public static int wordCount() {
		return WORDS.size();
	}
	
	public static void onLoad() {
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Dictionary.class.getResourceAsStream("/javalibrary/dict/english_words.txt")));
			while(true) {
				String line = bufferedReader.readLine();
				if(line == null) break;
				if(line.isEmpty() || line.startsWith("#")) continue;
	
				String word = line.split(" ")[0].toUpperCase();
				WORDS.add(word);
				WORDS_CHAR.add(word.toCharArray());
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}