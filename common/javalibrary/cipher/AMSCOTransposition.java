package javalibrary.cipher;

/**
 * @author Alex Barter (10AS)
 */
public class AMSCOTransposition {

	public static String decode(String cipherText, String keyword) {
		keyword = keyword.toUpperCase();
		int[] order = new int[keyword.length()];
		
		int i = 0;
		for(char ch = 'A'; ch <= 'Z'; ++ch) {
			String str = String.valueOf(ch);
			if(keyword.contains(str)) {
				order[keyword.indexOf(str)] = i;
				i++;
			}
		}
		
		return decode(cipherText, order);
	}
	
	public static String decode(String cipherText, int[] order) {
		String plainText = "";
		int index = 0;
		int no_column = order.length;
		
		int[] reversedOrder = new int[no_column];
		for(int i = 0; i < no_column; i++)
			reversedOrder[order[i]] = i;
		
		boolean reversed = true;
		int[] size = calcualteTotalSize(cipherText, reversed, no_column);
		
		String[][] grid = new String[size[0]][no_column];

		for(int column = 0; column < no_column; column++) {
		for(int row = 0; row < size[0]; row++) {
				int trueColumn = reversedOrder[column];
				
				for(int i = 0; i < (((row - trueColumn) % 2 == 0) ? reversed ? 2 : 1 : reversed ? 1 : 2); i++) {
					if(charactersBefore(reversed, false, no_column, row, trueColumn) >= cipherText.length())
						break;
					
					if(index >= cipherText.length())
						break;

					char character = cipherText.charAt(index);
					
					if(grid[row][trueColumn] == null)
						grid[row][trueColumn] = "" + character;
					else
						grid[row][trueColumn] += character;
					
					index += 1;
				}
			}
		}
		
		for(int row = 0; row < size[0]; row++) {
			for(int column = 0; column < no_column; column++) {
				if(grid[row][column] != null)
					plainText += grid[row][column];
			}
		}

		return plainText;
	}
	

	/**
	 * @param cipherText The cipher text that is trying to be decrypted
	 * @return The number of rows ranging from 1 - INFINATE
	 */
	public static int[] calcualteTotalSize(String cipherText, boolean reversed, int no_columns) {
		int row = 0;
		int column = 0;
		while(true) {
			int cb = charactersBefore(reversed, true, no_columns, row, column);
			if(cipherText.length() <= cb) {
				//System.out.println("EAew" + (row + 1) + "  " + + (column + 1));
				return new int[] {row + 1, column + 1};
			}
			
			column += 1;
			if(column >= no_columns)  {
				row += 1;
				column = 1;
			}
		}
	}
	
	public static int charactersBefore(boolean reversed, boolean including, int no_columns, int row, int column) {
		int total = 0;
		for(int i = 0; i <= row; i++) {
			for(int k = 0; k < no_columns; k++) {
				if(i == row && k == column + (including ? 1 : 0))
					break;
				
				total += ((i - k) % 2 == 0) ? reversed ? 2 : 1 : reversed ? 1 : 2;
			}
		}
		
		return total;
	}
}
