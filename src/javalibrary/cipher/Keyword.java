package javalibrary.cipher;

public class Keyword {

	public static String encode(String plainText, String keyword) {
		char[] charArray = plainText.toCharArray();
		
		String cipherText = "";
		
		for(char ch : charArray)
			cipherText += keyword.charAt(ch - 'A');
		
		return cipherText;
	}

	public static char[] decode(char[] cipherText, String keyword) {
		
		char[] plainText = new char[cipherText.length];
		
		for(int i = 0; i < cipherText.length; i++)
			plainText[i] = (char)(keyword.indexOf(cipherText[i]) + 'A');

		return plainText;
	}
}
