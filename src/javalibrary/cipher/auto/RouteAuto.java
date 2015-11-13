package javalibrary.cipher.auto;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTextField;

import javalibrary.EncryptionData;
import javalibrary.IForceDecrypt;
import javalibrary.Output;
import javalibrary.cipher.wip.Routes;
import javalibrary.cipher.wip.Routes.RouteCipherType;
import javalibrary.fitness.TextFitness;
import javalibrary.language.ILanguage;
import javalibrary.math.MathHelper;
import javalibrary.string.StringTransformer;
import javalibrary.swing.ProgressValue;
import nationalciphernew.cipher.stats.StatisticRange;

/**
 * @author Alex Barter (10AS)
 */
public class RouteAuto implements IForceDecrypt {

	@Override
	public String tryDecode(String cipherText, EncryptionData data, ILanguage language, Output output, ProgressValue progressBar, JTextField mostLikely) {
		List<Integer> factors = MathHelper.getFactors(cipherText.length());
		
		output.println("Factors - " + factors);
		
		double bestScore = Double.POSITIVE_INFINITY;
		String bestText = "";
		
		for(Integer factor : factors) {
			if(factor == 1 || factor == cipherText.length()) continue;
			int totalSize = cipherText.length();
			int width = factor;
			int height = totalSize / width;
			
			
			for(RouteCipherType type : Routes.getRoutes()) {
				//Create pattern
				List<Integer> grid = new ArrayList<Integer>();
				grid = type.createPattern(width, height, totalSize);
				
				//Reads across the grid
				String gridString = "";
				for(int i = 0; i < cipherText.length(); i++)
					gridString += cipherText.charAt(grid.indexOf(i));
				
				//Read down columns
				String finalStr = "";
				for(int i = 0; i < factor; i++)
					finalStr += StringTransformer.getEveryNthChar(gridString, i, factor);
				
				double gridScore = TextFitness.scoreFitnessQuadgrams(gridString, language);
				String reversed = StringTransformer.reverseString(gridString);
				double gridBackwardsScore = TextFitness.scoreFitnessQuadgrams(reversed, language);
				double finalScore = TextFitness.scoreFitnessQuadgrams(finalStr, language);
				
				if(gridScore > finalScore && gridScore > gridBackwardsScore) {
					if(gridScore > bestScore) {
						bestScore = gridScore;
						bestText = gridString;
						output.println(gridString + " --- " + gridScore + " --- read across --- " + type.getDescription());
					}
				}
				else if(finalScore > gridScore && finalScore > gridBackwardsScore) {
				
					if(finalScore > bestScore) {
						bestScore = finalScore;
						bestText = finalStr;
						output.println(finalStr + " --- " + finalScore + " --- read down --- " + type.getDescription());
					}
				}
				else {
					if(gridBackwardsScore > bestScore) {
						bestScore = gridBackwardsScore;
						bestText = reversed;
						output.println(reversed + " --- " + gridBackwardsScore + " --- backwards --- " + type.getDescription());
					}
				}
			}
		}
		
		return "";
	}
	
	@Override
	public String getName() {
		return "Route";
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
