package javalibrary.streams;

public class PrimTypeUtil {

	public static Character[] toCharacterArray(CharSequence str) {
	    Character[] chars = new Character[str.length()];
	    for(int i = 0; i < str.length(); i++) {
	        chars[i] = str.charAt(i);
	    }
	    
		return chars;
	}

	public static String toString(CharSequence characterArray) {
	    char[] builder = new char[characterArray.length()];
	    for(int i = 0; i < characterArray.length(); i++) {
	        builder[i] = characterArray.charAt(i);
        }
	    
		return new String(builder);
	}
	
	public static String toString(Character[] characterArray) {
        char[] builder = new char[characterArray.length];
        System.arraycopy(characterArray, 0, builder, 0, characterArray.length);
        return new String(builder);
    }
}
