package javalibrary.cipher;

import javalibrary.string.StringTransformer;

public class NihilistSubstitution {

	public static void main(String[] args) {
		System.out.println(new String(Homophonic.decode("16261199694633038879548312063894672404002789".toCharArray(), "GOLF")));
		//System.out.println(TriSquare.encode("EYESONLYRUMOURSOFASOURCEINBERLINWITHACCESSTOTHERATLINESSOURCESEEMSTOGOBYNAMEOFREICHSDOKTORRUSSIANINTERCEPTSSUGGESTHASBEENSEENINVICINITYOFUSEMBASSYNOTCLEARHOWTOMAKEDIRECTCONTACTALSONOTCLEARWHYOURUSFRIENDSAREKEEPINGTHISTOTHEMSELVESDETAILEDINFOABOUTRATILINESHARDTOOBTAINBUTHIGHVALUECOULDLEADTOARRESTOFMAJORTARGETSOFNUREMBERGINVESTIGATIONSVITALWEREACHREICHSDOKTORATEARLIESTOPPORTUNITYDISCREETENQUIRIESINFRENCHANDUSSECTORSONLYREQUESTFUNDSFORFURTHERINVESTIGATION", "NSFMUOAGPWVBHQXECIRYLDKTZ", "READINGBCFHKLMOPQSTUVWXYZ", "PASTINOQRMLYZUEKXWVBHGFDC"));
	}
	
	public static String encode(String plainText, String keysquare, String key) {
		String cipherText = "";
		int index = 0;
		for(char ch : plainText.toCharArray()) {

			int no = getNumberValue(ch, keysquare) + getNumberValue(key.charAt(index % key.length()), keysquare);
			if(no >= 100) no -= 100;
			String strNo = "" + no;
			if(strNo.length() < 2) strNo = "0" + strNo;
			cipherText += strNo;
			index += 1;
		}
		return cipherText;
	}
	
	public static char[] decode(char[] cipherText, String keysquare, String key) {
		char[] plainText = new char[cipherText.length / 2];
		
		for(int i = 0; i < cipherText.length / 2; i++) {
			int no = Integer.valueOf(new String(cipherText, i * 2, 2));
			if(no <= 10) no += 100;
			
			no -= getNumberValue(key.charAt(i % key.length()), keysquare);
			
			int column = no % 10;

			int row = (no - column) / 10 - 1;
			
			if(row * 5 + column - 1 >= keysquare.length() || row * 5 + column - 1 < 0 ) return StringTransformer.repeat("Z", plainText.length).toCharArray();
			plainText[i] = keysquare.charAt(row * 5 + column - 1);
		}
		
		return plainText;
	}
	
	private static int getNumberValue(char character, String keysquare) {
		if(character == 'J')
			character = 'I';
		int index = keysquare.indexOf(character);
		int row = (int)(index / 5) + 1;
		int column = index % 5 + 1;
		return row * 10 + column;
	}
}
