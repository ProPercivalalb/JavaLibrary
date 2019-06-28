package javalibrary.streams;

public class PrimTypeUtil {

	public static Character[] toCharacterArray(String str) {
	    Character[] chars = new Character[str.length()];
	    for(int i = 0; i < str.length(); i++) {
	        chars[i] = str.charAt(i);
	    }
	    
		return chars;
	}

	public static String toString(Character[] characterArray) {
	    StringBuilder builder = new StringBuilder(characterArray.length);
	    for(int i = 0; i < characterArray.length; i++) {
	        builder.append((char)characterArray[i]);
        }
	    
		return builder.toString();
	}
}
