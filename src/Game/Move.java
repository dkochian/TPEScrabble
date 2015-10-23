package game;

import board.Square;

//no lo uso
public class Move {

	private char[] word;
	private Square endSquare;
	private boolean transposed;
	private LetterNode letterNode;

	public Move(char[] word, LetterNode letterNode, Square endSquare, boolean transposed) {
		this.setWord(word);
		this.setLetterNode(letterNode);
		this.setEndSquare(endSquare);
		this.setTransposed(transposed);

	}

	public void setTransposed(boolean transposed) {
		this.transposed = transposed;
	}

	public boolean isTransposed() {
		return transposed;
	}

	public void setEndSquare(Square endSquare) {
		this.endSquare = endSquare;
	}

	public Square getEndSquare() {
		return endSquare;
	}

	public void setLetterNode(LetterNode letterNode) {
		this.letterNode = letterNode;
	}

	public LetterNode getLetterNode() {
		return letterNode;
	}

	public void setWord(char[] word) {
		this.word = word;
	}

	public char[] getWord() {
		return word;
	}

}
