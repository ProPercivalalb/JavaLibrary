package javalibrary.dict;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javalibrary.streams.PrimTypeUtil;
import javalibrary.util.RandomUtil;

public class Dictionary {

	public static List<String> WORDS = new ArrayList<>(10000);
	public static List<Character[]> WORDS_CHAR = new ArrayList<>();
	
	public static boolean containsWord(String word) {
		return WORDS.contains(word);
	}
	
	public static int wordCount() {
		return WORDS.size();
	}
	
	public static void onLoad() {
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Dictionary.class.getResourceAsStream("/files/english_words.txt")));
			while(true) {
				String line = bufferedReader.readLine();
				if(line == null) break;
				if(line.isEmpty() || line.startsWith("#")) continue;
	
				String word = line.split(" ")[0].toUpperCase();
				WORDS.add(word);
				WORDS_CHAR.add(PrimTypeUtil.toCharacterArray(word));
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Generates a string of words
	 * @return
	 */
	public static String generateRandomText(int numWords) {
	    StringBuilder builder = new StringBuilder();
	    for(int i = 0; i < numWords; i++) {
	        int index = RandomUtil.pickRandomInt(WORDS.size());
	        builder.append(WORDS.get(index));
	    }
	    
	    return builder.toString();
	}
}