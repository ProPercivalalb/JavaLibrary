package javalibrary.cipher.stats;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import nationalciphernew.cipher.stats.StatCalculator;

public class TraverseTree {

	public static List<Integer> numerical_attributes = Arrays.asList(0,1,2,3,4,5,6,7,8,22,23,24,25,26,27,28,29,30,31,33,34,35,36);
	public static Map cipherType;
	
	public static String traverse_tree(Object next_tree, Object[] values) {
	    if(next_tree instanceof String) {
	    	String decision = (String)next_tree;
	        String ans = decision;
	        ans = ans.substring(0, decision.indexOf('.'));
	        ans = (String)cipherType.get(ans);
	        return ans;
	    }
	    else if(next_tree instanceof Map) {
	    	Map tree = (Map)next_tree;
	    	
	    	String key = "";
	    	for(Object obj : tree.keySet())
	    		if(obj instanceof String)
	    			key = (String)obj;
	    	int index0 = Integer.valueOf(key);
	    	
	    	Map subtree = (Map)tree.get(key);
	    	
	    	if(numerical_attributes.contains(index0)) {
	    		List<String> keys = new ArrayList<String>();
		    	for(Object obj : subtree.keySet())
		    		if(obj instanceof String)
		    			keys.add((String)obj);
		    	
		    	String subkey0 = keys.get(0); 
		    	String subkey1 = keys.get(1);
		    	
		    	String[] s1 = subkey0.split(" ");
		    	int cut_off = Integer.valueOf(s1[2]);
		    	
		        if(Double.valueOf("" + values[index0]) >= cut_off){
		            if(s1[1].equals("above"))
		                return traverse_tree(subtree.get(subkey0), values);
		            else          
		            	return traverse_tree(subtree.get(subkey1), values);
		        }
		        else {
		        	if(s1[1].equals("above"))                            
		        		return traverse_tree(subtree.get(subkey1), values);
		            else
		            	return traverse_tree(subtree.get(subkey0), values);
		        }
	    	}
	    	else {
	    		List<String> keys = new ArrayList<String>();
		    	for(Object obj : subtree.keySet())
		    		if(obj instanceof String)
		    			keys.add((String)obj);
		    	
		    	String subkey0 = keys.get(0); 
		    	String subkey1 = keys.get(1);

		        String[] s1 = subkey0.split(" ");
		        
		        String answer = s1[1];
		        
		        String value = (Boolean)values[index0] ? "Y" : "N";
		        if (value.equals(answer))                
		        	return traverse_tree(subtree.get(subkey0), values);
		        else                            
		        	return traverse_tree(subtree.get(subkey1), values);
		    }
	    }
	    
	    return "Error";
	}
	
	public static List<Map> trees = new ArrayList<Map>();
	
	public static void onLoad() {
		try {
			BufferedReader updateReader = new BufferedReader(new InputStreamReader(TraverseTree.class.getResourceAsStream("/javalibrary/cipher/stats/trees/trees.txt")));
			while(true) {
				String line = updateReader.readLine();
				if(line == null) break;
				if(line.isEmpty() || line.startsWith("#")) continue;
				
				Map map = new Gson().fromJson(line, Map.class);
				trees.add(map);
			}
			
			
			
			
			
		    BufferedReader updateReader2 = new BufferedReader(new InputStreamReader(TraverseTree.class.getResourceAsStream("/javalibrary/cipher/stats/trees/ciphers.txt")));
		    cipherType = new Gson().fromJson(updateReader2.readLine(), Map.class);
		    
		    
		    
		    
			BufferedReader updateReader3 = new BufferedReader(new InputStreamReader(StatCalculator.class.getResourceAsStream("/javalibrary/cipher/stats/trigraph.txt")));

			String[] split = updateReader3.readLine().split(",");
			int i = 0;
			for(String s : split) {
				StatCalculator.bstd[i] = Integer.valueOf(s);
				i += 1;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
