package javalibrary.cipher.auto;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.JTextField;

import javalibrary.EncryptionData;
import javalibrary.IForceDecrypt;
import javalibrary.Output;
import javalibrary.cipher.solver.IGodSolver;
import javalibrary.language.ILanguage;
import javalibrary.swing.ProgressValue;
import nationalciphernew.cipher.stats.StatisticRange;

public class GodSolverAuto implements IForceDecrypt {

	@Override
	public String tryDecode(String cipherText, EncryptionData data, ILanguage language, Output output, ProgressValue progressBar, JTextField mostLikely) {

		ClassLoader loader = GodSolverAuto.class.getClassLoader();
		try {
			Enumeration<URL> urls = loader.getResources("");
			System.out.println(urls);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getName() {
		return "God Solver";
	}

	@Override
	public EncryptionData getEncryptionData() {
		return null;
	}

	@Override
	public JPanel getVarsPanel() {
		return new JPanel();
	}

	@Override
	public List<StatisticRange> getStatistics() {
		return null;
	}

	@Override
	public boolean canDictionaryAttack() {
		return false;
	}

	@Override
	public void tryDictionaryAttack(String cipherText, List<String> words, ILanguage language, Output outputObj, ProgressValue progressBar) {
		
	}

}
