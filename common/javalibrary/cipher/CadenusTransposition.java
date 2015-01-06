package javalibrary.cipher;

import javalibrary.cipher.wip.Transposition;
import javalibrary.math.MathHelper;

/**
 * @author Alex Barter (10AS)
 */
public class CadenusTransposition {
	
	public static String decode(String cipherText, String key) {
		int keyLength = key.length();

		String columnSorted = Transposition.decodeRow(cipherText, key);
		
		//Creates grid
		char[][] start_grid = new char[25][keyLength];
		char[][] temp_grid = new char[25][keyLength];
		int index = 0;
		for(int x = 0; x < 25; x++) {
			for(int y = 0; y < keyLength; y++) {
				start_grid[x][y] = columnSorted.charAt(index);
				temp_grid[x][y] = columnSorted.charAt(index);
				index += 1;
			}
		}
				
		for(int i = 0; i < keyLength; i++) {
			int value = charValue(key.charAt(i));
			int move = MathHelper.wrap(25 - value, 0, 25);
			for(int j = 0; j < 25; j++) {
				int newIndex = MathHelper.wrap(j + move, 0, 25);
				temp_grid[newIndex][i] = start_grid[j][i];
			}
		}
		
		String plainText = "";
		
		for(int x = 0; x < 25; x++) {
			for(int y = 0; y < keyLength; y++) {
				plainText += temp_grid[x][y];
			}
		}
		
		return plainText;
	}
	
	public static int charValue(char character) {
		if(character < 'V')
			return character - 65;
		else if(character > 'W')
			return character - 66;
		else
			return 21;
	}
}
