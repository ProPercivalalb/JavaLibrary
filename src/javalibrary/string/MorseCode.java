package javalibrary.string;

import java.util.Arrays;
import java.util.HashMap;

public class MorseCode {

	//Defines the size of 
	private final static int MAX_MORSE_LENGTH = 6;
	
	public static HashMap<String, Character> morseMap = new HashMap<String, Character>();
	public static HashMap<Character, String> charMap = new HashMap<Character, String>();
	
	public static char[] morseCharMap = new char[(int)Math.pow(3, MAX_MORSE_LENGTH)];
	
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
	
	/**
	 * @param morse An array of morse characters (. - x)
	 * @param index The index that the specifed morse character starts
	 * @param length The length of the morse character
	 * @return The character associated with the string of morse characters, ' ' if morse in not recognised
	 */
	public static char getCharFromMorse(char[] morse, int index, int length) {
		int lookup = 0;
		for(int i = 0; i < length; i++) {
			char ch = morse[index + i];
			
			if(ch == '-') lookup += Math.pow(3, i);
			else if(ch != '.') return ' ';
		}
		
		for(int i = length; i < MAX_MORSE_LENGTH; i++)
			lookup += 2 * Math.pow(3, i);

		if(lookup < 0 || lookup >= morseCharMap.length)
			return ' ';
		
		return morseCharMap[lookup];
	}
	
	public static void putCharacter(char character, String morse) {
		morseMap.put(morse, character);
		charMap.put(character, morse);
		
		int lookup = 0;
		for(int i = 0; i < morse.length(); i++) {
			int value = -1;
			char ch = morse.charAt(i);
			if(ch == '.') value = 0;
			else if(ch == '-') value = 1;
			
			lookup += value * Math.pow(3, i);
		}
		for(int i = morse.length(); i < MAX_MORSE_LENGTH; i++)
			lookup += 2 * Math.pow(3, i);
		
		System.out.println(lookup + " " + morse + " " + character);
		if(lookup != -1)
			morseCharMap[lookup] = character;
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
		
		Arrays.fill(morseCharMap, ' ');
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
		//putCharacter('�', ".----.");
		putCharacter('\'', ".----.");
		putCharacter('!', "-.-.--");
		putCharacter('?', "..--..");
		putCharacter('@', ".--.-.");
		putCharacter('-', "-....-");
		putCharacter(';', "-.-.-.");
		putCharacter('(', "-.--.");
		putCharacter(')', "-.--.-");
		putCharacter('=', "-...-");
		
		//putCharacter('', "");
		
	}
}
