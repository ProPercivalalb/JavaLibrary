package javalibrary;

import java.util.List;

import javalibrary.cipher.stats.StatisticRange;
import javalibrary.language.ILanguage;
import javalibrary.util.ProgressValue;

import javax.swing.JPanel;

/**
 * @author Alex Barter (10AS)
 */
public interface IForceDecrypt {
	
	public String tryDecode(String cipherText, EncryptionData data, ILanguage language, Output output, ProgressValue progressBar);
	
	public String getName();

	public EncryptionData getEncryptionData();
	
	public JPanel getVarsPanel();
	
	public List<StatisticRange> getStatistics();

	public boolean canDictionaryAttack();

	public void tryDictionaryAttack(String cipherText, List<String> words, ILanguage language, Output outputObj, ProgressValue progressBar);
}
