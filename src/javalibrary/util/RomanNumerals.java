package javalibrary.util;

import java.util.ArrayList;
import java.util.List;

public class RomanNumerals {
	
	public static void main(String[] args) {
		String cipherText = "MLLCLDCXXMLDDDLDDCDDDLDMXMDDLCXXDDDCDMXMDDCDCCLDDMDCDCXMDDDMCMDDLCXMLXLDCCXXCXXLXXDDDDCXXMMLLDDDLCXLCDDMXDLDXCXCXXXXDDMLXXDDCXLDCCLLDCDDDLXMXMDDLCXMLCXXXDCCXMLDDDLCXMDLDDLCXMDDDLCDCDCMDCCCCDDLDDLCXMDDXXXCDDLDXCXXCXXXXLLDCXLDDDMDDDCDCDMXXMDLXCCDCLXMDDLCXMLCDMLLXMCCDMCLXMDLLDXCXXCXXDLDDCXXXDMXXXCCDDXXLLXMLXXXXCXMXDXLMDLCLDDCXXDLCLMDMLLDDDLCCDDMDDDDLCXMDCDMCMCMCDDLDDCDLXXXLLDLLDXCCDCXXXXXCCXDLCLDDCDDXXCXXMCCDDXMXDDCXCCDDMDDDCXDXMLXXMXXDDMLCDDMCXXDLCXXMXXMXLXMXMCCLDCCXMMXLDDDXXXLCXXMXXCCXDMLLDDDLCLDDDDDLCXMCXCDDCDCCDLXXLDLLDDDXXCCCCLDXXLCXXMXLDCCLLDLXMXXCXLDDCXMXDDDLCXXDDDDLCXMLDXCXMCCLDMLXMDLXMDLXMXXXDLDCCLLLCLDDCXCCDCLCLDMCCLDXCXXDDLDCDCCDCXXLLDLLDXCCDCXXXXXDLDLXXCCLLXMXDDDCDCMCXXXCCDDLXXXCXDCXMLDCCLXCDDLCLXXDDLDCDCCXXXLCDDMDDDDLCXMCMCXXXCCCCXMXDXDLDDCCMCDDCLDDDLDCDCCCDLXDDDLCDCDCMDCDCXMDDDDLDCCLLXXDDDLXXCMLDCCDDCDMLLCLDXCLCDDLCXMLDXCXMCCLDLXXMCXCXLDDDLDDCDCDDLDCXCXDMCCXCCXXMXXDLLCCDMLDDLCXMDCXXMXXXLLXMDDDLLDXLXMDCLCXXXDCXXMXXDLCCXMXDDDCDXDXMXCLDCMLCXMDLCDDMDLXXDLCLMDDCXCCDCLCLDMCCLDXCXXDDLDCDCCDCXXDCDDLCXMMDLCXXXDCCCDMLDLLDDDLDCCLLCDLXDDLCXMLDDLCDMLCCLCCDMLXMMXXMDLXMMXXMDLMDXXDLCLMDLCXXDCLDDDDCDDDLXXLDDDCDDLDCXXCCXDLDDDDCXMXMCLDCCXLDLMXMCXMDDDCDCLXMDDLCXXDDCDCCXMCDDLCLCDDLXMCDLXDDLCXMCXCDXCXXCXDDDLLDXLXMDCCMXMCDCMCXXMDDDMDLCCXMXDDCCDCLXMCDCCXMDCLCXMXXXDCXCDMXXMCDDLCLCDCCXMMDLDDDLDDCXXCXCXDDDLXMXXXCLCXMDLMDXLDMDDDDLCLDDCXXXCDDDDLCDLXMXXDDXMCCXMXDDDCDDMCCXDXMDLCLLDCCXMDDLCXMXMCLCMLDDLXMDCXMCCDDLDDLXMDCMDDCDDXMCLCDLXDCXMXCDMDLXMXCCDCLCLDMCCLDXCXXDDLDCDCCDDLCXMXCXXXMDCXXDLXCLDCMLCXMDLCMXMDLLCXXCMDCCLCDDLXMDDLCXXCCDLCDXXXDDCLCXXXDXMCCXXXLCXXMXDDDLCXMXMCLCMLDDLXMDDCDLXDMCCXCDDLDCDCCDCXMXCDMDLXMCXMDXXCCXDLDDDDCCXCDDCDCMLXXDCXXXDLDDCXXDCDDXMDLDDDMDLCCLDCCLLDDLCXXDDCXCDDCDCLDCCDDCDXXMXLDXCDDCDDLMDCDCCDDLCXMXLXXDDDDCXXMLXLDXMCXXDXDXMDCXMDLMXXMXDCLCDDLXMDLXMXCCDLLCCLDDDLDCDCCDDLCXXCCLDDDDLXMXCXMLDMXXMXDCMXMDLLCXXCMDCDCDMXMDDCDCCLDDMDCCCXMMXXMDLLMCCXMMLCDDLCMXMDLLCXXCMDCLCXMLXXXLDCXXMXDDDCDDMCCXDXMDLDCDDXXCCXDDDLCXMXCDMCCCCLDCCLLCDLXDDLCXMCMCXXXCCXLDMDDXMLDDDLCXMDLMLXXMDXDXMDCCMLDDDXMDDLCXMMXLDXCDDCDDLMDXXLLDLLDXCCDCXXXXXCCXDDDLCXMCCLDCCDDLCCXXMLLLDCDCCDLXMCLXXLDCCXMXDLDCCXDLDDCLLDLXXXCXMCDCCLCXMXXDLLDCCLLDDLCLDDCCCXMMLDCXCCXXXDMXDLDDMDCXCXXXMDCXXDLXXDMLLDMDCDDDMDCLLXMDLCLXXCCLDXCDMDCCCXMDLCDXMCLCMXMDLCDDLLDDCDCDMXMXDXXDCXMXCDLXMDDCMDLCDXCCXXXCLXXDDLDCDCCXMDCDDXXXLCXLDDCLCLDCCLLXXXCLDCMLCXMDLDCXCLCCDCDCXDDCDXDXMMXXMCXCDCMXXCCXMMLDCMDDCDDXMCLCDLXLDCLCMXMDLLDXXCXXCLDCMLCXMDLDCLDDDMLCDDLLMXMXDLDCCDCXMXCDLXMDDLXCDDLCDMXXMDLDDMLXMCCDDMDMDXMXXDLDCXDXMMXXMCXCDCMLDCCLLCCXMMLCLXMDDLCCDXDDCDDCDDCXMXCDMDLXMXCCDCLCLDMCCLDXCXXDDLDCDCCDCXXXCDLCDDCDCDDLCXMXMCLCMLDDLXMDCDDDMXDMDLDCCLLDDLCXMMLCDDLLMDCCDLXDDLCXMLLDLXMXMLMDCXXLLXMDCXMDMXCCXLDXDXXCCXDLCMDCMXXDDLDXXXXCCXDXMMXXMCCCDCXXDXMDLMLCDDLLMDCLXDLCDCLXXCCXCLDXMCCDDXLXXXLMDCXCDCCDDLCXMDCXCLCCDCDCXXDXMMXXMCXCDCMXMXDCCXMMLMLXXMDDCDDCDDCXXLXXMLLDMXXDLXDXCCDCLCLDMCCLDXCXXDDLDCDCCDCLXDLCDCLDLCDCLXMDCXMCCXMCLLDXMDCDDLCXMDCXMCCXMMLDCMDDCDDXMCLDCMLXMDLXMMLDLLDDDDDXMCCXDCDMLCCXXCCXDXDLDDCDDDLLDXLDMDDXMXDLDCCDDLCXMXCCDXDXMMCCDXCXCDMCXDDCDDLDMCLCMXMDLLCXXCMDCDDLCXMCLCDDCDDDCDDDLLDXCDDCXMDLLDMXXDLXDXMXDXDCDXCDMCLXMCCDDLDCCXXCXCXLCLDDCDDCDDLMDMLLCXMCCLDDDMLXXDCLXLDCCXXCXCXMDXCCDCLCMCXXMDDXMXDCLXXCCMDMDXMXXDLDCCXXXDDXMDLDDLCXMXMCLCMXMDLCDDLXDCDCLLDDDLDXXCCLDDCDCDMXMXDXXCCXMMCXMXCDMDDLDMXXMCDDLXDXMDLMLLCLDXCLCCMDLXMLXXXXCXMXDXMMXXMDLMDXCCDCMMDCDLXDDLCXMXCCDXDXMMCDDLCXXDDLDDDMLXXDCDDCDXLXMLLDMXXDLXDXMXDXLMDXMMXXMDLMDCXXMLLLDCDCCDDCDDDLCXMCXXXDCDDCLXXCCDDLCXMXDXMLXXMXXDDCDLXDDLCXMLDXCXMCCLDXXCCXDDDLCXMDCDMLDXCLDXDXMCDLXXLCDDMXDLDXCXCXXCMXMDLLCXXCMDCCMDLCDDDXMXCDDXMXDDDLCXMCCLDCCDDLCCXXMLLLDCDCCLXDLCDCLXDLDDCDCCDCXDMDDLDCDCCXXLLDLLDXCCDCXXXMLXXDCCXXXDLLLXMCXMDCXXMLXDDDDCDLCLDDCCDMLCCXDXMMXLDXCXMDCLDCCDDLCXMCMDLCDMXLDCCXCXMDDLCCDDMLLLCLDDDMLXXDCCLXXXDXMXCCXXMXXDLDDCDLCLDCLDDLCXXDDDDCDDLXMDDDMDLCCMLCDDMCXXDCLXMXXCCXXDDXLXMDCDDXDLDDCLLDLXXXCXMXXCCXDXXDDMLCDDLDCDDXDXMXXDDLCLDCCXXXLDLXMXXLMMLLDDDLCDDDLXXXDLDDDLDCDCCDDLCXMCCLDCCDDLCDLXXLDDCXMXDXXCCXMMLDCDDXXCCXDXXDLXDDDLCXMCXXMLLLDCDCCMLXXDCXMMCLDCXXMXDLDCCXDLDDCLLDLXXXCXMDDCDXMCCXDDMDLXMDDLCXMLCXXDLXDXXCCXDXDXXCCLLXMDLCDDMDCMLCDDLLMCDLXDCDMXLXDDMLDCCLLDDLCXMXCXXCXXMXDCDCCLDLDLDCCDDLCXMXLCXXMXXLMMLLDCXXDXMDLCCXMDCDCXMDCCDLXXCXXCXXMXDCDCCLDXXDCDMXMDDCDCCLDDMDCCDDLXDXMDLXMXDDDLCXMCCLDCCDDLCDDCDCLXXDLXCLCDDCDXMXLCDDLXXXCDMCLMLLCLDXCLCDDLCXMMDMLXMDLXMDDCDCLXXLMXMDDLCXMLDDLXLXXDCXMLXCDDLDLXXLDXDDCLDCCDDCDXCXXCXXMXDCDCCLDXXDDLCXMDMCCDCCMCDLMXMCCDLXMXXDCCDCCLXCDDLDDLCXMLDDLXMMCLDCXXMMLXXDCDDLCXXDDLDCCDDXMCXCXLDLLXMCCXCXMDLXMCMCDDLDDDCDCDMLLLLXMDCDDXMXDDDLCXXDDDDLCLDDCMLXXDCMLLCXMDLXMDDLCXMMDCLLDLLLCDDLXLDCCXDDDLCXMLDDLCXCDDCDDXXDXDMLDCXXXDDLCCDDCXMMLLCCDMLCDDMCXXDDLXMXXXDCDCCCLDMDCDDLXCDCXCXCDMLCLMDLXXXLDDDLCLXDMCXDCCXXXMXXMDDLDDLCDMLLCCDLXXXXCXMDCXXLLDLXXMXXMDDXXDCLMCMXMDLLCXXCMDCDDLCXMCXXXDLLLXMDCDDDDCDXDXXDDXMXXDCLCXMDDDLXXMXXMCXDCDDCDXCCDCCXCXMXXCXDDLCXMLXCDDMDLDDLCXCLCXXCMDDXMDLCDLXDDLCLDDCDDDLXXLLLDXCDDXXCXXM";
		char[] charArray = cipherText.toCharArray();
		
		List<Integer> list = new ArrayList<Integer>();

		for(int c = 2; c <= 2; c++) {
			int index = 0;
		for(int i = 0; i < cipherText.length(); i += c) {
			
			//for(int j = 0; j < 2; j++) {
				//String column = StringTransformer.getEveryNthChar(charArray, j, 2);

			
					int no = romanToDecimal(cipherText.substring(index, index + c));
					if(!list.contains(no))
						list.add(no);
					index+=c;
					System.out.println(no);
			

				
				//System.out.println(cipherText.charAt(i));
			}
		}
		System.out.println("Size: " + list.size());
		System.out.println(list);
		
		String newText = "";
		
		for(int c = 2; c <= 2; c++) {
			int index = 0;
		for(int i = 0; i < cipherText.length(); i += c) {
			
			//for(int j = 0; j < 2; j++) {
				//String column = StringTransformer.getEveryNthChar(charArray, j, 2);

			
					int m = list.indexOf(romanToDecimal(cipherText.substring(index, index + c)));
					newText += (char)('A' + m);
					index+=c;
		
			

				
				//System.out.println(cipherText.charAt(i));
			}
		}
		
		System.out.println(newText);
	}
	
	public static int romanToDecimal(String romanNumber) {
        int decimal = 0;
        int lastNumber = 0;
        String romanNumeral = romanNumber.toUpperCase();
        /* operation to be performed on upper cases even if user 
           enters roman values in lower case chars */
        for (int x = romanNumeral.length() - 1; x >= 0 ; x--) {
            char convertToDecimal = romanNumeral.charAt(x);

            switch (convertToDecimal) {
                case 'M':
                    decimal = processDecimal(1000, lastNumber, decimal);
                    lastNumber = 1000;
                    break;

                case 'D':
                    decimal = processDecimal(500, lastNumber, decimal);
                    lastNumber = 500;
                    break;

                case 'C':
                    decimal = processDecimal(100, lastNumber, decimal);
                    lastNumber = 100;
                    break;

                case 'L':
                    decimal = processDecimal(50, lastNumber, decimal);
                    lastNumber = 50;
                    break;

                case 'X':
                    decimal = processDecimal(10, lastNumber, decimal);
                    lastNumber = 10;
                    break;

                case 'V':
                    decimal = processDecimal(5, lastNumber, decimal);
                    lastNumber = 5;
                    break;

                case 'I':
                    decimal = processDecimal(1, lastNumber, decimal);
                    lastNumber = 1;
                    break;
            }
        }
        return decimal;
    }

    public static int processDecimal(int decimal, int lastNumber, int lastDecimal) {
        if(lastNumber > decimal)
            return lastDecimal - decimal;
        else
            return lastDecimal + decimal;
    }
}
