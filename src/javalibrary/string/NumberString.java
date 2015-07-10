package javalibrary.string;

/**
 * Modify version from http://javahungry.blogspot.com/2014/05/convert-math-number-to-equivalent-readable-word-in-java-code-with-example.html
 */
public class NumberString {

	private static final String[] specialNames = {"", "THOUSAND", "MILLION", "BILLION", "TRILLION", "QUADRILLION", "QUINTILLION"};
    
    private static final String[] tensNames = {"", "TEN", "TWENTY", "THIRTY", "FORTY", "FIFTY", "SIXTY", "SEVENTY", "EIGHTY", "NINTY"};
    
    private static final String[] numNames = {"", "ONE", "TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN", "EIGHT", "NINE", "TEN", "ELEVEN", "TWELVE", "THIRTEEN", "FOURTEEN", "FIFTEEN", "SIXTEEN", "SEVENTEEN", "EIGHTEEN", "NINETEEN"};
    
    private static String convertLessThanOneThousand(int number) {
        String current;
        
        if (number % 100 < 20){
            current = numNames[number % 100];
            number /= 100;
        }
        else {
            current = numNames[number % 10];
            number /= 10;
            
            current = tensNames[number % 10] + current;
            number /= 10;
        }
        if (number == 0) 
        	return current;
        
        return numNames[number] + "HUNDRED" + current;
    }
    
    public static String convert(int number) {
    	if(number == 0)
    		return "ZERO";
    	
        String current = "";
        int place = 0;
        
        do {
            int n = number % 1000;
            if (n != 0){
                String s = convertLessThanOneThousand(n);
                current = s + specialNames[place] + current;
            }
            place++;
            number /= 1000;
        } 
        while (number > 0);
        
        return current;
    }
}
