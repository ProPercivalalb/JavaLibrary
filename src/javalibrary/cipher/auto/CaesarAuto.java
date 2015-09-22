package javalibrary.cipher.auto;

import java.util.ArrayList;
import java.util.List;

import javalibrary.EncryptionData;
import javalibrary.IForceDecrypt;
import javalibrary.Output;
import javalibrary.cipher.Caesar;
import javalibrary.cipher.stats.StatisticRange;
import javalibrary.cipher.stats.StatisticType;
import javalibrary.fitness.QuadgramStats;
import javalibrary.language.ILanguage;
import javalibrary.swing.ProgressValue;

import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author Alex Barter (10AS)
 */
public class CaesarAuto implements IForceDecrypt {

	@Override
	public String tryDecode(String cipherText, EncryptionData data, ILanguage language, Output output, ProgressValue progressBar, JTextField mostLikely) {
		String lastText = "";
		String plainText = "";
		double bestScore = Integer.MIN_VALUE;
		double currentScore = 0;
		progressBar.addMaxValue(26);
		for(int i = 0; i < 26; ++i) {
			lastText = Caesar.decode(cipherText, i);
			currentScore = QuadgramStats.scoreFitness(lastText, language);
			if(currentScore > bestScore) {
				output.println("Fitness: %f, Shift: %d, Plaintext: %s", currentScore, i, lastText);
				bestScore = currentScore;
				plainText = lastText;
			}
			progressBar.addValue(1);
		}
		
		return plainText;
	}

	@Override
	public String getName() {
		return "Caesar Shift";
	}
	
	@Override
	public EncryptionData getEncryptionData() {
		return EncryptionData.createNew();
	}
	
	@Override
	public JPanel getVarsPanel() {
		return new JPanel();
	}
	
	@Override
	public List<StatisticRange> getStatistics() {
		List<StatisticRange> list = new ArrayList<StatisticRange>();
		list.add(new StatisticRange(StatisticType.INDEX_OF_COINCIDENCE, 66.0D, 3.0D));
		list.add(new StatisticRange(StatisticType.MAX_IOC, 68.0D, 3.0D));
		list.add(new StatisticRange(StatisticType.MAX_KAPPA, 80.0D, 9.0D));
		list.add(new StatisticRange(StatisticType.DIGRAPHIC_IOC, 77.0D, 7.0D));
		list.add(new StatisticRange(StatisticType.EVEN_DIGRAPHIC_IOC, 77.0D, 7.0D));
		list.add(new StatisticRange(StatisticType.LONG_REPEAT_3, 24.0D, 2.0D));
		list.add(new StatisticRange(StatisticType.LONG_REPEAT_ODD, 50.0D, 2.0D));
		list.add(new StatisticRange(StatisticType.LOG_DIGRAPH, 413.0D, 65.0D));
		list.add(new StatisticRange(StatisticType.SINGLE_LETTER_DIGRAPH, 116.0D, 49.0D));
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
