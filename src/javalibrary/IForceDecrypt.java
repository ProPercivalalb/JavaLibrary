package javalibrary;

import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTextField;

import javalibrary.language.ILanguage;
import javalibrary.swing.ProgressValue;
import nationalciphernew.cipher.stats.StatisticRange;

/**
 * @author Alex Barter (10AS)
 */
public interface IForceDecrypt {
	
	public String tryDecode(String cipherText, EncryptionData data, ILanguage language, Output output, ProgressValue progressBar, JTextField mostLikely);
	
	public String getName();

	public EncryptionData getEncryptionData();
	
	public JPanel getVarsPanel();
	
	public List<StatisticRange> getStatistics();

	public boolean canDictionaryAttack();

	public void tryDictionaryAttack(String cipherText, List<String> words, ILanguage language, Output outputObj, ProgressValue progressBar);
}
