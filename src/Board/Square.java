package Board;

import java.util.HashSet;

import Dictionary.Dic;



public class Square {
//	static final char INVALID_SQUARE = (char) -1;
	public static final char EMPTY = 48;
	public static final char OUT_BOUNDS = 48;
	public static final char CENTER_SQUARE = 55;

	private char content;
	private int row;
	private int column;

	private HashSet<Character> validPieces;
	private Square nextLeft;
	private Square nextRight;
	private Square nextUp;
	private Square nextDown;

	public Square(char content, int row, int column) {
		this.validPieces = new HashSet<Character>();
		this.content = content;
		this.row = row;
		this.column = column;
		initValidPieces();
	}

	public void setNeighbours(Square left, Square right, Square up, Square down) {
		
		this.nextLeft = left;
		this.nextRight = right;
		this.nextUp = up;
		this.nextDown = down;

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Square)) {
			return false;
		}

		Square sq = (Square) obj;

		return this.row == sq.getRow() && this.column == sq.getColumn();
	}

	@Override
	public int hashCode() {
		Integer hash = ((Integer) this.row).hashCode() + ((Integer) this.column).hashCode();
		return hash;
	}

	public void setContent(char content, boolean transposed) {
		
		this.content = content;

	}

	public void calculateValidPieces(boolean transposed) {
		String wordDownwards = "";
		String wordUpwards = "";

		Square downSquare = getNextDown(transposed);
		Square upSquare = getNextUp(transposed);

		if (downSquare != null && downSquare.containsLetter()) {
			wordDownwards = verticalWord(downSquare, true, transposed);
		}

		if (upSquare != null && upSquare.containsLetter()) {
			wordUpwards = verticalWord(upSquare, false, transposed);
		}

		validPieces.clear();
		if (wordUpwards.equals("") && wordDownwards.equals("")) {
			initValidPieces();
		} else {
			for (int i = 0; i < Dictionary.Dic.alphabet.length() ; i++) {
				char letter = (char) Dictionary.Dic.alphabet.indexOf(i);

				String checkWord = wordUpwards + letter + wordDownwards;
				
				if (Dic.isValidWord(checkWord))
					validPieces.add((char) Dictionary.Dic.alphabet.indexOf(i));
			}
		}
	}

	public void initValidPieces() {
		for (int i = 0; i < Dictionary.Dic.alphabet.length(); i++) {
			validPieces.add((char) Dictionary.Dic.alphabet.indexOf(i));
		}
	}

	public String verticalWord(Square square, boolean wordDown,	boolean transposed) {
		if (square == null || !square.containsLetter())
			return "";

		char letter = square.getContent();

		if (! wordDown) {
			return verticalWord(square.getNextUp(transposed), wordDown,	transposed) + letter;
		} else {
			return letter + verticalWord(square.getNextDown(transposed), wordDown, transposed);
		}
	}

	public boolean containsLetter() {
		return content != ' ';
	}

	public boolean isEmpty() {
		return ! containsLetter();
	}

	public char getContent() {
		return content;
	}

	
	public HashSet<Character> getValidPieces() {
		return validPieces;
	}

	public boolean validPiecesContains(char letter) {
		return validPieces.contains(letter);
	}


	public int getColumn() {
		return this.column;
	}

	public int getRow() {
		return this.row;
	}

	public Square getNextRight(boolean transposed) {
		return (transposed) ? nextDown : nextRight;
	}

	public Square getNextLeft(boolean transposed) {
		return (transposed) ? nextUp : nextLeft;
	}

	public Square getNextDown(boolean transposed) {
		return (transposed) ? nextRight : nextDown;
	}

	public Square getNextUp(boolean transposed) {
		return (transposed) ? nextLeft : nextUp;
	}

	public void setCenterSquare() {
		this.content = Square.CENTER_SQUARE;
	}
}
