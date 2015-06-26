package javalibrary.cipher.auto;

import java.util.List;

import javalibrary.EncryptionData;
import javalibrary.IForceDecrypt;
import javalibrary.Output;
import javalibrary.cipher.Caesar;
import javalibrary.cipher.Vigenere;
import javalibrary.fitness.ChiSquared;
import javalibrary.fitness.StatisticRange;
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
public class VigenereAuto implements IForceDecrypt {

	@Override
	public String tryDecode(String cipherText, EncryptionData data, ILanguage language, Output output, JProgressBar progressBar) {
		int minKeywordLength = data.getData("minkeylength", Integer.class);
		int maxKeywordLength = data.getData("maxkeylength", Integer.class);
		
		int keyLength = this.findKeywordLength(cipherText, minKeywordLength, maxKeywordLength, language);
		
		progressBar.setMaximum(keyLength * 26);
		
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
	
	public int findKeywordLength(String text, int minLength, int maxLength, ILanguage language) {
		int bestLength = minLength;
	
	    double smallestDifference = Double.MAX_VALUE;
	    for(int i = minLength; i <= maxLength; ++i) {
	    	double total = 0;
	    	String temp = StringTransformer.rotateRight(text, i);
		    for(int j = 0; j < text.length(); ++j)
		       if(temp.charAt(j) == text.charAt(j))
		    	   total += 1;
	    	
	    	double average = total / text.length();
	    	double currentSquaredDiff = Math.abs((language.getNormalCoincidence() - average));
	    	
	        if(currentSquaredDiff < smallestDifference) {
	        	bestLength = i;
	        	smallestDifference = currentSquaredDiff;
	        }
	    }
	    
	    return bestLength;
	}	

    public int findBestCaesarShift(String text, ILanguage language, JProgressBar progressBar) {
        int best = 0;
        double smallestSum = Double.MAX_VALUE;
        for(int shift = 0; shift < 26; ++shift) {
            String encodedText = Caesar.decode(text, shift);
            double currentSum = ChiSquared.calculate(encodedText, language);
    
            if(currentSum < smallestSum) {
                best = shift;
                smallestSum = currentSum;
            }
            
            progressBar.setValue(progressBar.getValue() + 1);
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
		return null;
	}
}
