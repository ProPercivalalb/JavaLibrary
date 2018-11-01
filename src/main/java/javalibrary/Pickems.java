package javalibrary;

public class Pickems {

	public static void main(String[] args) {
		String str ="=";
		for(char letter = 'E'; letter <= 'Z'; letter++) {

		for(int i = 5; i < 11; i++) {
			str += "IF(D"+i+"=\"\",0, IF("+letter+""+i+"=D"+i+",1,0))+";
		}
		str = str.substring(0, str.length() - 1);
		str += " ";
		}
		
		System.out.print(str);
	}
}
