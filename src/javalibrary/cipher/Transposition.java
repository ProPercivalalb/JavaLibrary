package javalibrary.cipher;

/**
 * @author Alex Barter (10AS)
 */
public class Transposition {

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
		double y = cipherText.length() / no_columns;
		int rows = (int)Math.ceil(y);
		int end_column = (int)((y - rows) * no_columns);
		
		return new int[] {rows, end_column};
	}
	
	public static int charactersBefore(boolean including, int no_columns, int row, int column) {
		return (row - 1) * no_columns + column;
	}
}
