package javalibrary;

public class Player {

	public String irlName;
	public String ignName;
	public int rank;
	public int role1;
	public int role2;
	
	public Player(String input) {
		String[] split = input.split("[ ]+");
		
		this.irlName = split[0];
		this.ignName = split[1];
		
		char tier = split[2].charAt(0);
		char division = split[2].charAt(1);
		
		
		this.rank = 0;
		
		switch(tier) {
		
		case 'M': this.rank = 2500;
		case 'D': this.rank = 2000;
		case 'P': this.rank = 1500;
		case 'G': this.rank = 1000;
		case 'S': this.rank = 500;
		default: this.rank = 0;
		}
		
		this.rank = (5-(division - '1')) * 10;
		
		System.out.print(this.ignName + " " + this.rank);
	}
}
