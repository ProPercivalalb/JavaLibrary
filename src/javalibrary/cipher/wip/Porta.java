package javalibrary.cipher.wip;

import javalibrary.string.StringTransformer;

/**
 * @author Alex Barter (10AS)
 */
public class Porta {

	public static String decode(String text, String keyword) {
		text = StringTransformer.removeEverythingButLetters(text).toUpperCase();
		keyword = keyword.toUpperCase();
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String first = "ABCDEFGHIJKLM";
		String second = "NOPQRSTUVWXYZ";
		String keyalphabet = "";
		String result = "";
		int keypos = 0;
	 
		for (int i=0; i<keyword.length(); i++)
		{
			if (alphabet.indexOf(keyword.charAt(i)) == -1) return "Invalid keyword used! You can only use letters from A to Z.";
		}	
	 
		for (int i=0; i<text.length(); i++)
		{
			if (alphabet.indexOf("" + text.charAt(i)) == -1)
			{
				text=text.replace("" + text.charAt(i), "");
			}
		}
	 
		for (int i=0; i<text.length(); i++)
		{
			int j=alphabet.indexOf(keyword.charAt(keypos))/2;
			j=(int)Math.floor(j);
			keyalphabet=second.substring(j) + second.substring(0,j);
			if (keyalphabet.indexOf(text.charAt(i)) > -1)
				result += first.charAt(keyalphabet.indexOf(text.charAt(i)));
			else
		      result += keyalphabet.charAt(first.indexOf(text.charAt(i)));
		    keypos++;
		    if (keypos>=keyword.length()) keypos=0;
		}

	 
	  return result;
	}
}
