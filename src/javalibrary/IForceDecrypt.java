package javalibrary;

import java.util.List;

import javalibrary.fitness.StatisticRange;
import javalibrary.language.ILanguage;

import javax.swing.JPanel;
import javax.swing.JProgressBar;

/**
 * @author Alex Barter (10AS)
 */
public interface IForceDecrypt {
	
	public String tryDecode(String cipherText, EncryptionData data, ILanguage language, Output output, JProgressBar progressBar);
	
	public String getName();

	public EncryptionData getEncryptionData();
	
	public JPanel getVarsPanel();
	
	public List<StatisticRange> getStatistics();
}
