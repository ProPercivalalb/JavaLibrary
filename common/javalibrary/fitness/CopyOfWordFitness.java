package javalibrary.fitness;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * @author Alex Barter (10AS)
 */
public class CopyOfWordFitness {

	public static HashMap<String, Double> mapping1 = new HashMap<String, Double>();
	public static HashMap<String, Double> mapping2 = new HashMap<String, Double>();
	public static ArrayList<Double> mapping3 = new ArrayList<Double>();
	public static double number = 1024908267229D;
	
	public static double score(String text) {
		int maxwordlen = 20;
		double[][] prob = new double[text.length()][maxwordlen];
		String[][][] strs = new String[text.length()][maxwordlen][1];
		for(int i = 0; i < prob.length; ++i) {
			for(int j = 0; j < prob[i].length; ++j) {
				prob[i][j] = Double.MIN_VALUE;
				strs[i][j] = new String[] {""};
			}
		}
		
		for(double[] prob1 : prob)
			System.out.println(Arrays.toString(prob1));
		
		for(int i = 0; i < Math.min(text.length(), maxwordlen); ++i) {
			prob[0][i] = value(text.substring(0, i + 1), "");
			strs[0][i] = new String[] {text.substring(0, i + 1)};
		}
		for(int i = 1; i < text.length(); ++i) {
			for(int j = 0; j < Math.min(text.length(), maxwordlen); ++j) {
                if(i + j + 1 > text.length())
                	break;
                double[] candidates = new double[Math.min(i, maxwordlen)];
                String[][] candidates2 = new String[Math.min(i, maxwordlen)][0];
                
                for(int k = 0; k < Math.min(i, maxwordlen); ++k) {
                	candidates[k] = prob[i-k-1][k] + value(text.substring(i, i+j+1), strs[i-k-1][k][strs[i-k-1][k].length - 1]);
                	candidates2[k] = concat(strs[i-k-1][k], new String[] {text.substring(i, i+j+1)});
                }
                int index = 0;
                double largestSum = Double.MIN_VALUE;
                for(int s = 0; s < candidates.length; ++s) {
                	double currentSum = (double) candidates[s];
	                if(currentSum > largestSum) {
	                	index = s;
	                	largestSum = currentSum;
	                }
                }
	                
                prob[i][j] = candidates[index];
                strs[i][j] = candidates2[index];
                
        		System.out.println(Arrays.toString(candidates));
        		for(String[][] prob1 : strs) {
        			
        			for(String[] prob2 : prob1)
        				System.out.print(Arrays.toString(prob2));
        			System.out.println("");
        		
        			
        		}
			}
		}
		double[] end = new double[Math.min(text.length(), maxwordlen)];
		String[][] end2 = new String[Math.min(text.length(), maxwordlen)][0];
		
		for(int i = 0; i < Math.min(text.length(), maxwordlen); ++i) {
			end[i] = prob[prob.length-i-1][i];
			end2[i] = strs[strs.length-i-1][i];
		}
		
		int index = 0;
        double largestSum = Double.MIN_VALUE;
        for(int s = 0; s < end.length; ++s) {
        	double currentSum = (double) end[s];
            if(currentSum > largestSum) {
            	index = s;
            	largestSum = currentSum;
            }
        }
        double fitness = end[index];
        String finished = Arrays.toString(end2[index]);
        
        System.out.println(fitness + " " + finished);
		return 9;
	}
	
	public static String[] concat(String[] a, String[] b) {
		   int aLen = a.length;
		   int bLen = b.length;
		   String[] c= new String[aLen+bLen];
		   System.arraycopy(a, 0, c, 0, aLen);
		   System.arraycopy(b, 0, c, aLen, bLen);
		   return c;
		}
	
	public static void loadFiles() {
		
		InputStream inputStream = CopyOfWordFitness.class.getResourceAsStream("/javalibrary/fitness/count_1w.txt");
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		
		
		try {
			String line = "";
			while((line = reader.readLine()) != null) {

				String[] str = line.split("\\s+");
				
				if(str.length < 2) {
					continue;
				}
				
				String key = str[0].toUpperCase();
				
				double count = Double.valueOf(str[1]);
				if(mapping1.containsKey(key))
					count += mapping1.get(key);
				
				mapping1.put(key, (double)count);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		for(String key : mapping1.keySet()) {
			mapping1.put(key, Math.log10(mapping1.get(key) / number));
		}
		
		
		InputStream inputStream2 = CopyOfWordFitness.class.getResourceAsStream("/javalibrary/fitness/count_2w.txt");
		BufferedReader reader2 = new BufferedReader(new InputStreamReader(inputStream2));
		
		
		try {
			String line = "";
			while((line = reader2.readLine()) != null) {
				
				String[] str = line.split("\\t");
				
				if(str.length < 2) {
					System.out.println("contuine");
					continue;
				}
				
				String key = str[0].toUpperCase();
				
				double count = Double.valueOf(str[1]);
				if(mapping2.containsKey(key))
					count += mapping2.get(key);
				
				mapping2.put(key, (double)count);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		for(String key : mapping2.keySet()) {
			String[] str = key.split("\\s+");
			if(!mapping1.containsKey(str[0]))
				mapping2.put(key, Math.log10(mapping2.get(key) / number));
			else
				mapping2.put(key, Math.log10(mapping2.get(key) / number) - mapping1.get(str[0]));
			
		}
		
		for(int i = 0; i < 50; ++i) {
			mapping3.add(Math.log10(10 / (number * Math.pow(10, i))));
		}
	}
	
	public static double value(String word, String prev) {
		if(!mapping1.containsKey(word))
			return mapping3.get(word.length());
		else if(!mapping2.containsKey(prev + " " + word))
			return mapping1.get(word);
		else
			return mapping2.get(prev + " " + word);
	}
	
}
