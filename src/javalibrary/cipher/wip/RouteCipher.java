package javalibrary.cipher.wip;

import java.util.ArrayList;
import java.util.List;

import javalibrary.cipher.wip.Routes.RouteCipherType;
import javalibrary.math.MathHelper;
import javalibrary.string.StringTransformer;

public class RouteCipher {

	public static void decode(String cipherText) {
		List<Integer> factors = MathHelper.getFactors(cipherText.length());
		
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
				for(int i = 0; i < cipherText.length(); i++) {
					gridString += cipherText.charAt(grid.indexOf(i));
				}
				
				//Read down columns
				String finalStr = "";				
				for(int i = 0; i < factor; i++) {
					finalStr += StringTransformer.getEveryNthChar(gridString, i, factor);
				}
				
				System.out.println(finalStr + " --- " + type.getDescription());
			}
		}
	}
	
	
}
