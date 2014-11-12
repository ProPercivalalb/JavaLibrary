package javalibrary.language;

/**
 * @author Alex Barter
 */
public class Czech implements ILanguage {

	@Override
	public double getFrequencyOfLetter(char character) {
		character = Character.toUpperCase(character);
		if(character == 'A') return D; if(character == 'B') return D;  if(character == 'C') return D; 
		if(character == 'D') return D; if(character == 'E') return D;  if(character == 'F') return D; 
		if(character == 'G') return D; if(character == 'H') return D;  if(character == 'I') return D; 
		if(character == 'J') return D; if(character == 'K') return D;  if(character == 'L') return D;
		if(character == 'M') return D; if(character == 'N') return D;  if(character == 'O') return D; 
        if(character == 'P') return D; if(character == 'Q') return D;  if(character == 'R') return D; 
        if(character == 'S') return D; if(character == 'T') return D;  if(character == 'U') return D; 
        if(character == 'V') return D; if(character == 'W') return D;  if(character == 'X') return D; 
        if(character == 'Y') return D; if(character == 'Z') return D;
        return 0.0D;
	}

	@Override
	public double getMaxFrequency() {
		return D;
	}

	@Override
	public String getName() {
		return "Czech";
	}
}