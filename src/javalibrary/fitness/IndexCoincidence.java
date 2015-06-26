package javalibrary.fitness;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import javalibrary.ForceDecryptManager;
import javalibrary.IForceDecrypt;
import javalibrary.language.ILanguage;
import javalibrary.string.LetterCount;
import javalibrary.string.StringAnalyzer;
import javalibrary.string.StringTransformer;

/**
 * @author Alex Barter (10AS)
 */
public class IndexCoincidence {

	public static double calculate(String text) {
		Hashtable<Character, LetterCount> test = StringAnalyzer.countLetters(text);
		int length = text.length();
		
		double total = 0.0D;
		
		for(char ch : test.keySet()) {
			double count = test.get(ch).count;
			total += count * (count - 1);
		}
		//return total / (0.0385D * length * (length - 1));

		return total / (length * (length - 1));
	}
	
	public static double calculateMaxKappa(String text, int startPeriod, int endPeriod) {
	    double smallestDifference = Double.MIN_VALUE;
	    for(int i = startPeriod; i <= endPeriod; ++i) {
	    	double total = 0;
	    	String temp = StringTransformer.rotateRight(text, i);
		    for(int j = 0; j < text.length(); ++j)
		       if(temp.charAt(j) == text.charAt(j))
		    	   total += 1;
	    	
	    	double average = total / text.length();
	    	
	        if(average > smallestDifference)
	        	smallestDifference = average;
	    }
	    
	    return smallestDifference;
	}
	
	/**
	 * Calculates the number of characters that are repeated 3 times in a row
	 */
	public static double calculateLongRepeat(String text) {
		int count = 0;
		for(int i = 0; i < text.length(); i++)
			for(int j = i + 1 ; j < text.length(); j++) {
				int n = 0;
				while(j + n < text.length() && text.charAt(i + n) == text.charAt(j + n))
					n++;
				
				if(n == 3)
					count += 1;
			}
		

		return Math.sqrt(count) / text.length();
	}
	
	public static List<List<Object>> get_num_dev(String text, HashMap<StatisticType, Double> cipher_values) {

		List<List<Object>> num_dev = new ArrayList<List<Object>>();
		for(IForceDecrypt forceDecrypt : ForceDecryptManager.getObjects()) {
			double x = 0.0D;
			List<StatisticRange> statistics = forceDecrypt.getStatistics();
			if(statistics == null) continue;
			
			for(StatisticRange defaultStatistic : statistics) {
				StatisticType type = defaultStatistic.type;
				double value = defaultStatistic.value;
				double standardDeviation = defaultStatistic.standardDeviation;
				if (type == StatisticType.INDEX_OF_COINCIDENCE) standardDeviation += 0.001D;

				if(value == 0.0D)
					x += cipher_values.get(type);
				else
					x += Math.abs((cipher_values.get(type) - value) / standardDeviation);
			}
			List<Object> t = new ArrayList<Object>();
			t.add(forceDecrypt.getName());
			t.add(x);
			num_dev.add(t);
		}

		return num_dev;
		
		/**
		List<List<Object>> num_dev = new ArrayList<List<Object>>();
		for (int i = 0; i < ctype.size(); i++) {
			double x = 0.0D;
			for (int j = 0; j < 9; j++) {
				double v = std.get(j)[i];
				if (j == 0) v += 0.001;
				
				if(cipher_values[j] != 0)
					if (ave.get(j)[i] == 0)
						x += cipher_values[j];
					else
						x += Math.abs((cipher_values[j] - ave.get(j)[i]) / v);
			}
			List<Object> t = new ArrayList<Object>();
			t.add(ctype.get(i));
			t.add(x);
			num_dev.add(t);
		}

		return num_dev;**/
	}
	
	public static ArrayList<Double[]> ave = new ArrayList<Double[]>();
	public static ArrayList<Double[]> std = new ArrayList<Double[]>();
	
	public static List<String> ctype= Arrays.asList("Plaintext", "Randomdigit", "Randomtext", "6x6Bifid", "6x6Playfair", "Amsco", "Bazeries", "Beaufort", "Bifid6", "Bifid7", "Cadenus", "Cmbifid", "Columnar", "Digrafid", "DoubleCheckerBoard", "Four_square", "FracMorse", "Grandpre", "Grille", "Gromark", "Gronsfeld", "Homophonic", "MonomeDinome", "Morbit", "Myszkowski", "Nicodemus", "Nihilistsub", "NihilistTransp", "Patristocrat", "Phillips", "Periodic gromark", "Playfair", "Pollux", "Porta", "Portax", "Progressivekey", "Progkey beaufort", "Quagmire2", "Quagmire3", "Quagmire4", "Ragbaby", "Redefence", "RunningKey", "Seriatedpfair", "Swagman", "Tridigital", "Trifid", "Trisquare", "Trisquare HR", "Two square", "Twosquarespiral", "Vigautokey", "Vigenere", "period 7 Vigenere", "Vigslidefair", "Route Transp");
	
	
	static {
		ave.add(new Double [] {63.0, 100.0, 38.0, 35.0, 42.0, 63.0, 64.0,42.0,47.0,47.0,63.0,47.0,63.0,41.0,90.0,48.0,47.0,128.0,63.0,39.0,40.0,101.0,124.0,122.0,63.0,42.0,144.0,63.0,63.0,49.0,38.0,50.0,100.0,41.0,42.0,38.0,38.0,41.0,42.0,41.0,41.0,63.0,39.0,48.0,62.0,122.0,42.0,43.0,43.0,49.0,47.0,39.0,42.0,42.0,40.0,63.0});
		std.add(new Double [] {5.0, 2.0, 1.0, 4.0, 4.0,5.0,4.0,3.0,4.0,4.0,5.0,4.0,5.0,3.0,13.0,3.0,2.0,3.0,5.0,1.0,2.0,1.0,7.0,4.0,5.0,3.0,11.0,5.0,5.0,3.0,1.0,4.0,0.0,2.0,2.0,1.0,1.0,2.0,2.0,2.0,1.0,5.0,4.0,3.0,5.0,8.0,3.0,2.0,1.0,3.0,3.0,1.0,2.0,3.0,2.0,5.0});
		
		ave.add(new Double [] {73.0,108.0,44.0,47.0,51.0,72.0,74.0,67.0,58.0,58.0,74.0,57.0,73.0,53.0,133.0,58.0,53.0,136.0,74.0,46.0,66.0,108.0,134.0,129.0,72.0,50.0,201.0,73.0,73.0,58.0,45.0,60.0,103.0,66.0,51.0,45.0,45.0,65.0,66.0,65.0,49.0,72.0,56.0,56.0,72.0,134.0,53.0,51.0,52.0,60.0,59.0,45.0,65.0,67.0,63.0,73.0});
		std.add(new Double [] {11.0,8.0,5.0,9.0,9.0,10.0,13.0,9.0,10.0,9.0,11.0,9.0,11.0,7.0,18.0,9.0,8.0,7.0,12.0,7.0,8.0,6.0,11.0,7.0,10.0,7.0,23.0,12.0,11.0,8.0,7.0,9.0,2.0,9.0,7.0,6.0,6.0,8.0,9.0,8.0,8.0,10.0,18.0,9.0,11.0,15.0,8.0,5.0,5.0,8.0,8.0,6.0,8.0,9.0,9.0,11.0});
		
		ave.add(new Double [] {95.0,132.0,60.0,62.0,67.0,94.0,94.0,78.0,75.0,77.0,95.0,75.0,96.0,67.0,149.0,76.0,70.0,158.0,91.0,63.0,76.0,127.0,169.0,156.0,95.0,73.0,195.0,97.0,95.0,74.0,63.0,79.0,121.0,74.0,66.0,63.0,63.0,75.0,76.0,75.0,71.0,94.0,74.0,75.0,90.0,161.0,68.0,64.0,65.0,77.0,76.0,62.0,74.0,78.0,72.0,92.0});
		std.add(new Double [] {19.0,16.0,12.0,16.0,15.0,19.0,20.0,17.0,15.0,17.0,17.0,15.0,18.0,13.0,23.0,15.0,15.0,15.0,16.0,13.0,19.0,13.0,19.0,16.0,18.0,14.0,30.0,18.0,19.0,16.0,14.0,18.0,9.0,16.0,14.0,13.0,14.0,15.0,18.0,18.0,14.0,16.0,22.0,19.0,17.0,22.0,14.0,11.0,11.0,16.0,15.0,12.0,15.0,17.0,16.0,17.0});
		
		ave.add(new Double [] {72.0,100.0,14.0,14.0,32.0,44.0,60.0,23.0,24.0,24.0,40.0,23.0,41.0,17.0,110.0,36.0,42.0,179.0,42.0,15.0,21.0,116.0,249.0,193.0,41.0,18.0,218.0,41.0,72.0,32.0,14.0,38.0,105.0,22.0,18.0,14.0,14.0,21.0,22.0,21.0,18.0,41.0,16.0,25.0,39.0,195.0,18.0,21.0,21.0,36.0,34.0,15.0,22.0,23.0,18.0,46.0});
		std.add(new Double [] {18.0,8.0,2.0,5.0,9.0,10.0,15.0,5.0,6.0,6.0,9.0,5.0,8.0,4.0,30.0,8.0,9.0,15.0,9.0,3.0,5.0,7.0,36.0,15.0,8.0,4.0,33.0,9.0,18.0,7.0,3.0,9.0,2.0,6.0,3.0,3.0,3.0,5.0,5.0,5.0,4.0,10.0,8.0,6.0,7.0,29.0,5.0,3.0,3.0,9.0,7.0,3.0,6.0,5.0,4.0,14.0});
		
		ave.add(new Double [] {73.0,98.0,14.0,14.0,72.0,43.0,61.0,23.0,24.0,23.0,41.0,23.0,41.0,20.0,207.0,72.0,43.0,227.0,43.0,15.0,25.0,160.0,252.0,194.0,41.0,18.0,266.0,40.0,73.0,32.0,15.0,72.0,105.0,25.0,19.0,13.0,14.0,25.0,24.0,23.0,18.0,43.0,16.0,25.0,39.0,197.0,18.0,21.0,21.0,72.0,72.0,14.0,26.0,23.0,25.0,47.0});
		std.add(new Double [] {24.0,15.0,5.0,8.0,24.0,13.0,20.0,9.0,8.0,8.0,13.0,9.0,12.0,7.0,58.0,24.0,13.0,39.0,14.0,6.0,11.0,15.0,43.0,25.0,13.0,7.0,42.0,13.0,24.0,10.0,6.0,24.0,4.0,11.0,8.0,5.0,6.0,10.0,10.0,10.0,6.0,16.0,15.0,9.0,12.0,37.0,8.0,6.0,5.0,24.0,24.0,5.0,11.0,8.0,9.0,18.0});
		
		ave.add(new Double [] {22.0,21.0,5.0,4.0,11.0,11.0,17.0,9.0,7.0,7.0,10.0,6.0,11.0,5.0,25.0,11.0,16.0,33.0,10.0,4.0,9.0,24.0,45.0,38.0,11.0,5.0,38.0,10.0,22.0,11.0,4.0,12.0,23.0,9.0,6.0,4.0,4.0,8.0,8.0,8.0,6.0,10.0,4.0,7.0,10.0,38.0,6.0,7.0,7.0,11.0,11.0,4.0,8.0,9.0,6.0,12.0});
		std.add(new Double [] {5.0,3.0,3.0,3.0,5.0,4.0,5.0,4.0,4.0,4.0,4.0,4.0,4.0,3.0,5.0,4.0,3.0,3.0,4.0,3.0,4.0,2.0,5.0,2.0,4.0,3.0,4.0,4.0,5.0,4.0,3.0,4.0,1.0,4.0,3.0,3.0,3.0,4.0,4.0,4.0,4.0,4.0,5.0,4.0,4.0,4.0,3.0,2.0,3.0,4.0,4.0,3.0,4.0,4.0,3.0,6.0});
		
		ave.add(new Double [] {50.0,50.0,50.0,49.0,25.0,50.0,49.0,50.0,48.0,49.0,49.0,50.0,50.0,43.0,13.0,28.0,50.0,43.0,49.0,50.0,42.0,42.0,49.0,49.0,49.0,50.0,40.0,50.0,50.0,49.0,48.0,32.0,50.0,42.0,48.0,49.0,49.0,42.0,43.0,44.0,49.0,49.0,49.0,49.0,50.0,49.0,51.0,49.0,50.0,28.0,25.0,50.0,42.0,50.0,40.0,50.0});
		std.add(new Double [] {6.0,3.0,10.0,12.0,9.0,8.0,5.0,10.0,10.0,9.0,9.0,10.0,7.0,11.0,7.0,8.0,7.0,3.0,7.0,12.0,14.0,2.0,2.0,2.0,7.0,10.0,6.0,9.0,6.0,9.0,11.0,8.0,1.0,13.0,12.0,14.0,12.0,14.0,12.0,13.0,11.0,7.0,19.0,8.0,6.0,3.0,12.0,6.0,7.0,8.0,9.0,12.0,13.0,10.0,11.0,7.0});
		
		ave.add(new Double [] {756.0,0.0,428.0,298.0,243.0,688.0,477.0,443.0,510.0,517.0,657.0,493.0,653.0,469.0,609.0,507.0,444.0,0.0,679.0,431.0,444.0,0.0,0.0,0.0,657.0,442.0,0.0,654.0,414.0,424.0,428.0,491.0,0.0,432.0,442.0,428.0,429.0,431.0,444.0,440.0,473.0,653.0,445.0,484.0,650.0,0.0,462.0,503.0,512.0,542.0,501.0,434.0,438.0,437.0,436.0,675.0});
		std.add(new Double [] {13.0,0.0,23.0,53.0,57.0,15.0,44.0,32.0,36.0,37.0,17.0,31.0,16.0,33.0,44.0,33.0,32.0,0.0,16.0,26.0,27.0,0.0,0.0,0.0,18.0,35.0,0.0,17.0,57.0,37.0,26.0,42.0,0.0,35.0,24.0,24.0,26.0,32.0,36.0,33.0,23.0,18.0,35.0,38.0,18.0,0.0,37.0,23.0,23.0,33.0,36.0,23.0,33.0,34.0,34.0,33.0});
		
		ave.add(new Double [] {303.0,0.0,109.0,71.0,63.0,188.0,112.0,113.0,119.0,118.0,134.0,114.0,128.0,112.0,133.0,114.0,107.0,0.0,173.0,109.0,111.0,0.0,0.0,0.0,135.0,112.0,0.0,129.0,106.0,106.0,108.0,118.0,0.0,111.0,113.0,109.0,109.0,109.0,110.0,111.0,112.0,128.0,107.0,115.0,135.0,0.0,112.0,119.0,120.0,121.0,119.0,111.0,106.0,108.0,112.0,162.0});
		std.add(new Double [] {23.0,0.0,14.0,16.0,19.0,17.0,21.0,15.0,16.0,17.0,18.0,16.0,15.0,15.0,19.0,16.0,17.0,0.0,17.0,15.0,15.0,0.0,0.0,0.0,18.0,15.0,0.0,17.0,23.0,17.0,16.0,19.0,0.0,16.0,13.0,15.0,14.0,16.0,17.0,17.0,15.0,15.0,23.0,17.0,16.0,0.0,15.0,14.0,13.0,18.0,17.0,16.0,16.0,17.0,15.0,50.0});
		
		String cipherText = "VEYJMYJKRZILYSYJEYDORULCSRCJNMOIDDEUGURLOGFSNWCRLHCKHGHMWKEJXLYKTLAGFLALVTMGKYYOMVLMGKYJOSFDRZEPWAQGNRZEQZINOAQFORKCSLTJWDUSSRZARLHCNAJNEQZABBAKEEBATJGOIKLGCERZEBJIDLWMGDUSSNMLJWDGFTMLHCEEAZALASKSNBTLMUKCVTFWILDERHRCKUKSBJQTFWCPWWFSDYDRCSDWSBYFDMFEBLHCNEQKEJOHGUHUSSJMCIQFMJUQOIRZOSLTFWSFAPUWWMMLBZATWHYVNMADCSTFSTRZEDVAFSDZWELGPCJARANEANRZEQWWYLEPKSCSHMJSCASLGLMFGCJAKQSRWRWLHCUURSWYQOLLHCKTYJBMSRBKIBWCJWAPWDYFAPWAMXAPGULVFGNEKWTCJSQIUYJEUATFSDGKTGFCRAVCHARLEPFODTOJLSDSSRWNCVTMJEGFFMJCCVDCUKNDARWSGKAUKOKWTFANEDIIWTFASMFAQMBPWSAMEKASQAOLSCMMPJWODQEYJSYYOUZELLHCQFGLTCVAJGCYDSFAPUATFSJSJYPAGEWDGFSNWCRAOLKYQLEKLHCVEACPJSTCKCYFCYJRWSCPSNCVEQAGLWDRGDCHLMQALJOTSRCEORWONWRYLEBNEFACJWDCKIEFEBXOPMNBWRQWAMHEPSTGGNQAWYKAJJEYVYAGNAWRLWDYTOSLTFWRCXEPWNAWTMLHCUAZDEQANRZEJSSRHAPLODLHCXDYDOETURLHCFEVLSCUTGGNFSSKWRCSLJQWMJRGWDGLIQWNAJYNLEBOIRZAKGRCKEAMRCEOBAFGWDYESAGTPSNQHOQATGGNAAPFWRYFDRWLJKUQOHYLTFWYUWRCJEYDLWMPRGWFSTGVOLLULVEPKTYFDGKHMOTFWWFGLCSSQWMZDYGKPMOEPWDRZEQGRRGFAGMNMTGFGRZEWEUQLBCVOGFGGKRCSLJQILLELKITWALVWMMLBTUPFTFJOSYHYTARLEPQILVAWKILLHYLTGEERZEGJILLEPUENLMGYHRFORUARUHYFYRZILYUQWFSDBSLTFWYASNFSRBDYFSVCZIHSCIWDYDOASLQGCIWTGFTFWMGVDJWODLHCGCCSNASNWGUEWTKWAAZAPLSFGWGFGRZEBWENKEYUAZDEQANRZEPWGGGNGVOLLIKSGGFERZESKWGDLZWANJOZDEKTURATKSYLWEBKOKWDGHLMEAAQTMYERLHCXUJDCMNEPSGCEANKFPGMRZEMEALAGMNEPFMCFTGXIYERGYHRATGKILLHCARZWSRANRWRCKTQLONDAWSLMFGUWAJDHYNEYDORLOJGSCZEPW";
		
	    double[] cipher_values = new double[9];
	    String s = "len: "+cipherText.length();
	    double x = IndexCoincidence.calculate(cipherText) * 1000;
	    //dump decimal part of x
	    cipher_values[0] = x;
	    s += " IC: "+cipher_values[0];
	    x = 0;//get_max_periodic_ic(cipherText);
	    cipher_values[1] = x;
	    s += " MIC: "+cipher_values[1];
	    x = IndexCoincidence.calculateMaxKappa(cipherText, 1, 15);
	    cipher_values[2] = x;
	    s += " MKA: "+cipher_values[2];
	    x = 0;//get_dic(cipherText);
	    cipher_values[3] = x;
	    s += " DIC: "+cipher_values[3];
	    x = 0;//get_even_dic(cipherText);
	    cipher_values[4] = x;
	    s += " EDI: "+cipher_values[4];
	    x = IndexCoincidence.calculateLongRepeat(cipherText);
	    cipher_values[5] = x;
	    s += " LR: "+cipher_values[5];
	    x = 0;//get_ROD(cipherText);
	    cipher_values[6] = x;
	    s += " ROD: "+cipher_values[6];
	    x = 0;//get_logdi(cipherText);
	    cipher_values[7] = x;
	    s += " LDI: "+cipher_values[7];
	    x = 0;//get_sdd(cipherText);
	    cipher_values[8] = x;
	    s += " SDD: "+cipher_values[8];
	    s += "\n\nNumber of standard deviations from averages for each cipher type:\n\n";
	    List<List<Object>> num_dev = IndexCoincidence.get_num_dev("", cipher_values);
	    //num_dev.sort(s_compare)
	    for(int i = 0; i < num_dev.size(); i++) {
	    	s += num_dev.get(i).get(0) + " " + num_dev.get(i).get(1) + "\n";
	    }
	    System.out.println(s);
	    
	}
}
