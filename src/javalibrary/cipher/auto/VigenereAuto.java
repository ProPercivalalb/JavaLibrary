package javalibrary.cipher.auto;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import javalibrary.EncryptionData;
import javalibrary.IForceDecrypt;
import javalibrary.Output;
import javalibrary.cipher.Caesar;
import javalibrary.cipher.Vigenere;
import javalibrary.fitness.ChiSquared;
import javalibrary.language.ILanguage;
import javalibrary.string.StringTransformer;
import javalibrary.swing.ProgressValue;
import nationalciphernew.cipher.stats.StatCalculator;
import nationalciphernew.cipher.stats.StatisticRange;
import nationalciphernew.cipher.stats.StatisticType;

/**
 * @author Alex Barter
 * @since 26 Nov 2013
 */
public class VigenereAuto implements IForceDecrypt {

	@Override
	public String tryDecode(String cipherText, EncryptionData data, ILanguage language, Output output, ProgressValue progressBar, JTextField mostLikely) {
		int minKeywordLength = data.getData("minkeylength", Integer.class);
		int maxKeywordLength = data.getData("maxkeylength", Integer.class);
		
		int keyLength = StatCalculator.calculateBestKappaIC(cipherText, minKeywordLength, maxKeywordLength, language);
		
		progressBar.addMaxValue(keyLength * 26);
		
		String keyword = "";
        for(int i = 0; i < keyLength; ++i) {
        	String temp = StringTransformer.getEveryNthChar(cipherText, i, keyLength);
            int shift = this.findBestCaesarShift(temp, language, progressBar);
            keyword += (char)('A' + shift);
        }
        output.println("Keyword: " + keyword);
		
        String plainText = Vigenere.decode(cipherText, keyword);
        output.println("Plaintext: " + plainText);
        
		return plainText;
	}

    public int findBestCaesarShift(char[] text, ILanguage language, ProgressValue progressBar) {
        int best = 0;
        double smallestSum = Double.MAX_VALUE;
        for(int shift = 0; shift < 26; ++shift) {
            char[] encodedText = Caesar.decode(text, shift);
            double currentSum = ChiSquared.calculate(encodedText, language);
    
            if(currentSum < smallestSum) {
                best = shift;
                smallestSum = currentSum;
            }
            
            progressBar.addValue(1);
        }
        return best;
    }

	@Override
	public String getName() {
		return "Vigenere";
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
		list.add(new StatisticRange(StatisticType.INDEX_OF_COINCIDENCE, 42.0D, 2.0D));
		list.add(new StatisticRange(StatisticType.MAX_IOC, 65.0D, 8.0D));
		list.add(new StatisticRange(StatisticType.MAX_KAPPA, 74.0D, 15.0D));
		list.add(new StatisticRange(StatisticType.DIGRAPHIC_IOC, 22.0D, 6.0D));
		list.add(new StatisticRange(StatisticType.EVEN_DIGRAPHIC_IOC, 26.0D, 11.0D));
		list.add(new StatisticRange(StatisticType.LONG_REPEAT_3, 8.0D, 4.0D));
		list.add(new StatisticRange(StatisticType.LONG_REPEAT_ODD, 42.0D, 13.0D));
		list.add(new StatisticRange(StatisticType.LOG_DIGRAPH, 438.0D, 33.0D));
		list.add(new StatisticRange(StatisticType.SINGLE_LETTER_DIGRAPH, 106.0D, 16.0D));
		return list;
	}
	
	@Override
	public boolean canDictionaryAttack() {
		return false;
	}

	@Override
	public void tryDictionaryAttack(String cipherText, List<String> words, ILanguage language, Output output, ProgressValue progressBar) {
		
	}
}
