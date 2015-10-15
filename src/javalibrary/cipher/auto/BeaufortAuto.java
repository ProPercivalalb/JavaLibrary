package javalibrary.cipher.auto;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import javalibrary.EncryptionData;
import javalibrary.ForceDecryptManager;
import javalibrary.IForceDecrypt;
import javalibrary.Output;
import javalibrary.cipher.Beaufort;
import javalibrary.cipher.stats.StatisticRange;
import javalibrary.cipher.stats.StatisticType;
import javalibrary.language.ILanguage;
import javalibrary.string.StringTransformer;
import javalibrary.swing.ProgressValue;

/**
 * @author Alex Barter
 * @since 26 Nov 2013
 */
public class BeaufortAuto implements IForceDecrypt {

	@Override
	public String tryDecode(String cipherText, EncryptionData data, ILanguage language, Output output, ProgressValue progressBar, JTextField mostLikely) {
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
		
		progressBar.addMaxValue(keyLength * 26);
		
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
	
	@Override
	public List<StatisticRange> getStatistics() {
		List<StatisticRange> list = new ArrayList<StatisticRange>();
		list.add(new StatisticRange(StatisticType.INDEX_OF_COINCIDENCE, 42.0D, 3.0D));
		list.add(new StatisticRange(StatisticType.MAX_IOC, 72.0D, 9.0D));
		list.add(new StatisticRange(StatisticType.MAX_KAPPA, 78.0D, 17.0D));
		list.add(new StatisticRange(StatisticType.DIGRAPHIC_IOC, 23.0D, 5.0D));
		list.add(new StatisticRange(StatisticType.EVEN_DIGRAPHIC_IOC, 23.0D, 9.0D));
		list.add(new StatisticRange(StatisticType.LONG_REPEAT_3, 9.0D, 4.0D));
		list.add(new StatisticRange(StatisticType.LONG_REPEAT_ODD, 50.0D, 10.0D));
		list.add(new StatisticRange(StatisticType.LOG_DIGRAPH, 443.0D, 32.0D));
		list.add(new StatisticRange(StatisticType.SINGLE_LETTER_DIGRAPH, 113.0D, 15.0D));
		return null;
	}
	
	@Override
	public boolean canDictionaryAttack() {
		return false;
	}

	@Override
	public void tryDictionaryAttack(String cipherText, List<String> words, ILanguage language, Output output, ProgressValue progressBar) {
		
	}
}
