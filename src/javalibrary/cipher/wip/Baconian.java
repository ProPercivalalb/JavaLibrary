package javalibrary.cipher.wip;

public class Baconian {

	public static String encode(String plainText) {
		
		return "";
	}
	
	public static String decode(String cipherText) {
		String plainText = "";
		for(int i = 0; i < cipherText.length(); i ++) {
			char ch = cipherText.charAt(i);
			if(ch == 'A')
				plainText += 0;
			else if(ch == 'B')
				plainText += 1;
		}
		return plainText;
	}
}
