package board;

/**
 * no lo uso
 * Represents the games pieces, containing the piece's score and letter
 */
public class Piece {
	private int value;
	private char letter;
	
	public Piece(char letter, int value) {
		this.letter = letter;
		this.value = value;		
	}

	public int getValue() {
		return value;
	}

	public char getLetter() {
		return letter;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + letter;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Piece other = (Piece) obj;
		if (letter != other.letter)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Piece [value=" + value + ", letter=" + letter + "]";
	}	
}
