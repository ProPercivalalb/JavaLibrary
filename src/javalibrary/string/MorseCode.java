package javalibrary.string;

import java.util.HashMap;

public class MorseCode {

	public static HashMap<String, Character> morseMap = new HashMap<String, Character>();
	public static HashMap<Character, String> charMap = new HashMap<Character, String>();
	
	public static String getMorseEquivalent(String text) {
		String morseText = "";
		for(char character : text.toCharArray()) {
			morseText += charMap.get(character) + "X";
		}
		return morseText.substring(0, morseText.length() - 1);
	}
	
	public static char getCharFromMorse(String morse) throws NullPointerException {
		return morseMap.get(morse);
	}
	
	public static void putCharacter(char character, String morse) {
		morseMap.put(morse, character);
		charMap.put(character, morse);
	}
	
	static {
		/**
		A  .-    N  -.    .  .-.-.-  1  .----
		B  -...  O  ---   ,  --..--  2  ..---
		C  -.-.  P  .--.  :  ---...  3  ...--
		D  -..   Q  --.-  "  .-..-.  4  ....-
		E  .     R  .-.   '  .----.  5  .....
		F  ..-.  S  ...   !  -.-.--  6  -....
		G  --.   T  -     ?  ..--..  7  --...
		H  ....  U  ..-   @  .--.-.  8  ---..
		I  ..    V  ...-  -  -....-  9  ----.
		J  .---  W  .--   ;  -.-.-.  0  -----
		K  -.-   X  -..-  (  -.--.           
		L  .-..  Y  -.--  )  -.--.-          
		M  --    Z  --..  =  -...-   
		**/
		putCharacter(' ', "");
		putCharacter('A', ".-");
		putCharacter('B', "-...");
		putCharacter('C', "-.-.");
		putCharacter('D', "-..");
		putCharacter('E', ".");
		putCharacter('F', "..-.");
		putCharacter('G', "--.");
		putCharacter('H', "....");
		putCharacter('I', "..");
		putCharacter('J', ".---");
		putCharacter('K', "-.-");
		putCharacter('L', ".-..");
		putCharacter('M', "--");
		putCharacter('N', "-.");
		putCharacter('O', "---");
		putCharacter('P', ".--.");
		putCharacter('Q', "--.-");
		putCharacter('R', ".-.");
		putCharacter('S', "...");
		putCharacter('T', "-");
		putCharacter('U', "..-");
		putCharacter('V', "...-");
		putCharacter('W', ".--");
		putCharacter('X', "-..-");
		putCharacter('Y', "-.--");
		putCharacter('Z', "--..");
		
		putCharacter('1', ".----");
		putCharacter('2', "..---");
		putCharacter('3', "...--");
		putCharacter('4', "....-");
		putCharacter('5', ".....");
		putCharacter('6', "-....");
		putCharacter('7', "--...");
		putCharacter('8', "---..");
		putCharacter('9', "----.");
		putCharacter('0', "-----");
		putCharacter('.', ".-.-.-");
		putCharacter(',', "--..--");
		putCharacter(':', "---...");
		putCharacter('"', ".-..-.");
		putCharacter('’', ".----.");
		putCharacter('\'', ".----.");
		putCharacter('!', "-.-.--");
		putCharacter('?', "..--..");
		putCharacter('@', ".--.-.");
		putCharacter('-', "-....-");
		putCharacter(';', "-.-.-.");
		putCharacter('(', "-.--.");
		putCharacter(')', "-.--.-");
		putCharacter('=', "-...-");
		putCharacter('=', "-...-");
		
		//putCharacter('', "");
		
	}
}
