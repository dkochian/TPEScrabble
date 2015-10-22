package game;

public class LetterNode {

	private char letter;
	private LetterNode previousLetter;
	private LetterNode nextLetter;
	private int size = 0;


	public LetterNode(LetterNode previousLetter, LetterNode nextLetter, char letter) {
		this.previousLetter = previousLetter;
		this.letter = letter;
		this.nextLetter = nextLetter;
		this.size += 1;
	}
	
	public void setLetter(char letter) {
		this.letter = letter;
	}


	public void setPreviousLetter(LetterNode previousLetter) {
		this.previousLetter = previousLetter;
	}


	public void setNextLetter(LetterNode nextLetter) {
		this.nextLetter = nextLetter;
	}


	public char getLetter() {
		return letter;
	}


	public LetterNode getPreviousLetter() {
		return previousLetter;
	}


	public LetterNode getNextLetter() {
		return nextLetter;
	}
	
	public int getSize(){
		return size;
	}

}
