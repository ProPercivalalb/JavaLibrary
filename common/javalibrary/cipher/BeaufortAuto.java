package javalibrary.cipher;

import javalibrary.EncryptionData;
import javalibrary.ForceDecryptManager;
import javalibrary.IForceDecrypt;
import javalibrary.Output;
import javalibrary.language.ILanguage;
import javalibrary.string.StringTransformer;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

/**
 * @author Alex Barter
 * @since 26 Nov 2013
 */
public class BeaufortAuto implements IForceDecrypt {

	@Override
	public String tryDecode(String cipherText, EncryptionData data, ILanguage language, Output output, JProgressBar progressBar) {
		char[] charArray = cipherText.toCharArray();

		String invervedText = "";
		//Runs through all the characters from the array
		for(char ch : charArray) {
			
			if(Character.isAlphabetic(ch))
				ch = (char)('Z' - ch + 'A');
			
			invervedText += ch;
		}
		
		int minKeywordLength = data.getData("minkeylength", Integer.class);
		int maxKeywordLength = data.getData("maxkeylength", Integer.class);
		
		int keyLength = ForceDecryptManager.vigenere.findKeywordLength(invervedText, minKeywordLength, maxKeywordLength, language);
		
		progressBar.setMaximum(keyLength * 26);
		
		String keyword = "";
        for(int i = 0; i < keyLength; ++i) {
        	String temp = StringTransformer.getEveryNthChar(invervedText, i, keyLength);
            int shift = ForceDecryptManager.vigenere.findBestCaesarShift(temp, language, progressBar);
            keyword += (char)('Z' - shift);
        }
        output.println("Keyword: " + keyword);
		
        String plainText = Beaufort.decode(cipherText, keyword);
        output.println("Plaintext: " + plainText);
        
		return plainText;
	}
	
	@Override
	public String getName() {
		return "Beaufort";
	}
	
	@Override
	public EncryptionData getEncryptionData() {
		EncryptionData data = EncryptionData.createNew();
		String text = rangeBox.getText().replaceAll("[^-0-9]", "");
		int minlength = 0;
		int maxlength = 0;
		if(!text.contains("-")) {
			minlength = Integer.valueOf(text);
			maxlength = Integer.valueOf(text);
		}
		else {
			minlength = Integer.valueOf(text.split("-")[0]);
			maxlength = Integer.valueOf(text.split("-")[1]);
		}
		data.putData("minkeylength", Math.min(minlength, maxlength));
		data.putData("maxkeylength", Math.max(maxlength, maxlength));
		return data;
	}
	
	private JTextField rangeBox = new JTextField("2-8");
	
	@Override
	public JPanel getVarsPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		JLabel range = new JLabel("Keyword length range:   ");
		panel.add(range);
		panel.add(rangeBox);
		return panel;
	}
}
