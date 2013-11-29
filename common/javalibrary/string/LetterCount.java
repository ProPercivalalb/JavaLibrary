package javalibrary.string;

/**
 * @author Alex Barter
 */
public class LetterCount {
		
	public char ch;
	public double count;
		
	public LetterCount(char ch, int count) {
		this.ch = ch;
		this.count = count;
	}
		
	public void increment() {
		this.count += 1;
	}
		
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof LetterCount) {
			LetterCount other = (LetterCount)obj;
			return other.ch == this.ch;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return new Character(this.ch).hashCode();
	}
}