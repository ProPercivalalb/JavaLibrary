package javalibrary.cipher.auto;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import javalibrary.EncryptionData;
import javalibrary.IForceDecrypt;
import javalibrary.Output;
import javalibrary.cipher.RailFence;
import javalibrary.cipher.stats.StatisticRange;
import javalibrary.cipher.stats.StatisticType;
import javalibrary.fitness.TextFitness;
import javalibrary.language.ILanguage;
import javalibrary.swing.ProgressValue;

/**
 * @author Alex Barter (10AS)
 */
public class RailFenceAuto implements IForceDecrypt {

	@Override
	public String tryDecode(String cipherText, EncryptionData data, ILanguage language, Output output, ProgressValue progressBar, JTextField mostLikely) {
		int minRows = data.getData("minrows", Integer.class);
		int maxRows = data.getData("maxrows", Integer.class);

		progressBar.addMaxValue(maxRows - minRows + 1);
		
		String lastText = "";
		String plainText = cipherText;
		double bestScore = Integer.MIN_VALUE;
		double currentScore = 0;
		
		for(int rows = minRows; rows <= maxRows; ++rows) {
			lastText = RailFence.decode(cipherText, rows);
			currentScore = TextFitness.scoreFitnessQuadgrams(lastText, language);
		
			if(currentScore > bestScore) {
				output.println("Fitness: %f, Number of Rows: %d, Plaintext: %s", currentScore, rows, lastText);
				bestScore = currentScore;
				plainText = lastText;
			}
			
			progressBar.addValue(1);
		}
		
		return plainText;
	}
	
	@Override
	public String getName() {
		return "Rail Fence";
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
		data.putData("minrows", Math.min(minlength, maxlength));
		data.putData("maxrows", Math.max(maxlength, maxlength));
		return data;
	}
	
	private JTextField rangeBox = new JTextField("2-8");
	
	@Override
	public JPanel getVarsPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		JLabel range = new JLabel("Number of rows range:  ");
		range.setMaximumSize(new Dimension(200, 100));
		panel.add(range);
		panel.add(rangeBox);
		return panel;
	}
	
	@Override
	public List<StatisticRange> getStatistics() {
		List<StatisticRange> list = new ArrayList<StatisticRange>();
		list.add(new StatisticRange(StatisticType.INDEX_OF_COINCIDENCE, 63.0D, 5.0D));
		list.add(new StatisticRange(StatisticType.MAX_IOC, 72.0D, 10.0D));
		list.add(new StatisticRange(StatisticType.MAX_KAPPA, 94.0D, 16.0D));
		list.add(new StatisticRange(StatisticType.DIGRAPHIC_IOC, 41.0D, 10.0D));
		list.add(new StatisticRange(StatisticType.EVEN_DIGRAPHIC_IOC, 43.0D, 16.0D));
		list.add(new StatisticRange(StatisticType.LONG_REPEAT_3, 10.0D, 4.0D));
		list.add(new StatisticRange(StatisticType.LONG_REPEAT_ODD, 49.0D, 7.0D));
		list.add(new StatisticRange(StatisticType.LOG_DIGRAPH, 653.0D, 18.0D));
		list.add(new StatisticRange(StatisticType.SINGLE_LETTER_DIGRAPH, 128.0D, 15.0D));
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
