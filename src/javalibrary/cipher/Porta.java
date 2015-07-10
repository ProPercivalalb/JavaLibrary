package javalibrary.cipher;

/**
 * @author Alex Barter (10AS)
 */
public class Porta {

	public static String encode(String plainText, String keyword) {
		return decode(plainText, keyword);
	}
	
	public static String decode(String cipherText, String keyword) {
		String plainText = "";
		
		for(int pos = 0; pos < cipherText.length(); pos++){
			int rowNo = (int)Math.floor((keyword.charAt(pos % keyword.length()) - 'A') / 2);
			String row = "";
			
			for(int j = 0; j < 13; j++)
				row += (char)((j + rowNo) % 13 + 'N');
			
			int inGrid = row.indexOf(cipherText.charAt(pos));
			if(inGrid >= 0)
				plainText += (char)(inGrid + 'A');
			else
				plainText += row.charAt(cipherText.charAt(pos) - 'A');
		}
	 
		return plainText;
	}
}
