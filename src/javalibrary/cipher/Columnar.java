package javalibrary.cipher;

import javalibrary.string.StringTransformer;

/**
 * @author Alex Barter (10AS)
 */
public class Columnar {

	public static String encode(String plainText, String key) {
		key = key.toUpperCase();
		int[] order = new int[key.length()];
		
		int p = 0;
		for(char ch = 'A'; ch <= 'Z'; ++ch) {
			int keyindex = key.indexOf(ch);
			if(keyindex != -1)
				order[p++] = keyindex;
		}
		
		return encode(plainText, order);
	}
	
	public static String encode(String plainText, int[] order) {
		String add = "";
		for(int column = 0; column < order.length; column++) {
			int trueColumn = order[column];
			String temp = StringTransformer.getEveryNthChar(plainText, trueColumn, order.length);
			System.out.println(temp);
			add += temp;
		}
		return add;
	}
	
	public static String decode(String cipherText, String key) {
		key = key.toUpperCase();
		int[] order = new int[key.length()];
		
		int p = 0;
		for(char ch = 'A'; ch <= 'Z'; ++ch) {
			int keyindex = key.indexOf(ch);
			if(keyindex != -1)
				order[p++] = keyindex;
		}
		
		return decode(cipherText, order);
	}
	
	public static String decode(String cipherText, int[] order) {
		int index = 0;
		int no_column = order.length;
		
		int[] reversedOrder = new int[no_column];
		for(int i = 0; i < no_column; i++)
			reversedOrder[order[i]] = i;
		
		int[] size = calcualteTotalSize(cipherText, no_column);
		
		char[] grid = new char[cipherText.length()];

		for(int column = 0; column < no_column; column++) {
			for(int row = 0; row < size[0]; row++) {
				
				int trueColumn = reversedOrder[column];
				if(charactersBefore(false, no_column, row, trueColumn) >= cipherText.length())
					break;
					
				if(index >= cipherText.length())
					break;

				char character = cipherText.charAt(index);

				grid[row * no_column + trueColumn] = character;
					
				index += 1;
			}
		}

		return new String(grid);
	}
	
	/**
	 * @param cipherText The cipher text that is trying to be decrypted
	 * @return The number of rows ranging from 1 - INFINATE
	 */
	public static int[] calcualteTotalSize(String cipherText, int no_columns) {
		int row = 0;
		int column = 0;
		while(true) {
			int cb = charactersBefore(true, no_columns, row, column);
			if(cipherText.length() <= cb)
				return new int[] {row + 1, column + 1};
			
			column += 1;
			if(column >= no_columns)  {
				row += 1;
				column = 1;
			}
		}
	}
	
	public static int charactersBefore(boolean including, int no_columns, int row, int column) {
		int total = 0;
		for(int i = 0; i <= row; i++) {
			for(int k = 0; k < no_columns; k++) {
				if(i == row && k == column + (including ? 1 : 0))
					break;
				
				total += 1;
			}
		}
		
		return total;
	}
}
