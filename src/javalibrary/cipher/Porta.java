package javalibrary.cipher;

import javalibrary.math.MathHelper;

/**
 * @author Alex Barter (10AS)
 */
public class Porta {

	public static String encode(String plainText, String keyword, boolean move) {
		return decode(plainText, keyword, move);
	}
	
	/**
	 * @param move 'true' for rotates right, 'false' for rotates left
	 * 	   true           false
	 * NOPQRSTUVWXYZ  NOPQRSTUVWXYZ
	 * ZNOPQRSTUVWXY  OPQRSTUVWXYZN
	 * YZNOPQRSTUVWX  PQRSTUVWXYZNO
	 */
	public static String decode(String cipherText, String keyword, boolean move) {
		String plainText = "";
		
		for(int pos = 0; pos < cipherText.length(); pos++){
			int rowNo = (int)Math.floor((keyword.charAt(pos % keyword.length()) - 'A') / 2);
			String row = "";
			
			for(int j = 0; j < 13; j++)
				row += (char)(MathHelper.mod(j + (move ? -1 : 1) * rowNo, 13) + 'N');
			
			int inGrid = row.indexOf(cipherText.charAt(pos));
			if(inGrid >= 0)
				plainText += (char)(inGrid + 'A');
			else
				plainText += row.charAt(cipherText.charAt(pos) - 'A');
		}
	 
		return plainText;
	}
}
