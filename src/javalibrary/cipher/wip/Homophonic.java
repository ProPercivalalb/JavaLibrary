package javalibrary.cipher.wip;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javalibrary.math.MathHelper;

public class Homophonic {

	public static Random rand = new Random(System.currentTimeMillis());
	
	public static String encode(String plainText, String key) {
		String cipherText = "";
		
		List<String> rows = new ArrayList<String>();
		
		for(int i = 0; i < 4; i++) {
			int charIndex = key.charAt(i) - 'A';
			
			if(charIndex >= 9) charIndex--;
			
			for(int no = 0; no < 25; no++) {
				String n = "" + (i * 25 + MathHelper.mod(no - charIndex, 25) + 1) % 100;
				if(n.length() < 2) n = "0" + n;
				rows.add(n);
			}
		}
		
		for(int i = 0; i < plainText.length(); i++) {
			int charIndex = plainText.charAt(i) - 'A';
			if(charIndex >= 9) charIndex--;
			
			cipherText += rows.get(rand.nextInt(4) * 25 + charIndex);
		}
		
		return cipherText;
	}
	
	public static String decode(String cipherText, String key) {
		String plainText = "";
		
		List<String> rows = new ArrayList<String>();
		String shortAlpha = "ABCDEFGHIKLMNOPQRSTUVWXYZ";
		
		for(int i = 0; i < 4; i++) {
			int c = key.charAt(i) - 'A';
			
			if(c >= 8) c--;
			
			for(int no = 0; no < 25; no++) {
				String n = "" + (i * 25 + MathHelper.mod(no - c, 25) + 1) % 100;
				if(n.length() < 2) n = "0" + n;
				rows.add(n);
			}
		}
		
		for(int i = 0; i < cipherText.length(); i += 2) {
			String s = cipherText.substring(i, i + 2);
			int col = rows.indexOf(s) % 25;
			plainText += shortAlpha.charAt(col);
		}
		
		return plainText;
	}
}
