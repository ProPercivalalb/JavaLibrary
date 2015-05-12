package javalibrary.cipher;

/**
 * @author Alex Barter (10AS)
 */
public class RailFence {

	public static String encode(String plainText, int rows) {
		
		StringBuilder[] cipherText = new StringBuilder[rows];
		for(int i = 0; i < rows; ++i)
			cipherText[i] = new StringBuilder();
		
		int no_per_ite = rows * 2 - 2;
		
		for(int i = 0; i < plainText.length(); ++i) {
			char character = plainText.charAt(i);
			int index_in_ite = i % no_per_ite;
			if(index_in_ite < rows)
				cipherText[index_in_ite].append(character);
			else
				cipherText[rows - (index_in_ite - rows) - 2].append(character);
		}
		
		String last = "";
		
		for(StringBuilder text : cipherText)
			last += text.toString();
		return last;
	}
	
	public static String decode(String cipherText, int rows) {
		char[] plainText = new char[cipherText.length()];
		int index = 0;
		int no_per_ite = rows * 2 - 2;
		double total = (double)cipherText.length() / no_per_ite;
		int total_ite = (int)Math.floor(total);
		int total_left = (int)((total - total_ite) * no_per_ite);
		
		for(int c_row = 1; c_row <= rows; c_row++) {
			if(index >= cipherText.length())
				break;
			
			int times = total_ite;
			if(c_row != 1 && c_row != rows)
				times *= 2;

			if(total_left >= c_row)
				times += 1;
			
			if(c_row != rows && rows - c_row <= total_left - rows) // 16 - 4 <= 11
				times += 1;
			
			for(int i = 0; i < times; i++) {
				int newIndex = 0;
				
				if(c_row == 1 || c_row == rows)
					newIndex = i * no_per_ite + c_row - 1;
				else {
					int x = (int)Math.floor(i / 2);
					newIndex = x * no_per_ite + c_row - 1;
					
					if(i % 2 == 1)
						newIndex += (rows - c_row) * 2;			
				}
				
				plainText[newIndex] = cipherText.charAt(index);
				index++;
			}
		}
		return new String(plainText);
	}
}
