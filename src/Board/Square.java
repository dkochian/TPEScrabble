package Board;

import java.util.HashSet;

import Dictionary.Dic;



public class Square {
//	static final char INVALID_SQUARE = (char) -1;
	public static final char EMPTY = 48;
	public static final char OUT_BOUNDS = 48;
	public static final char CENTER_SQUARE = 55;

	/**
	 * Content of the square
	 */
	private char content;
	/**
	 * Row where the square is located
	 */
	private int row;
	/**
	 * Column where the square is located
	 */
	private int column;
	
	/**
	 * Square to the left
	 */
	private Square nextLeft;
	/**
	 * Square to the right
	 */
	private Square nextRight;
	/**
	 * Square over this one
	 */
	private Square nextUp;
	/**
	 * Square under this one
	 */
	private Square nextDown;

	public Square(char content, int row, int column) {
		this.validPieces = new HashSet<Character>();
		this.content = content;
		this.row = row;
		this.column = column;
	}

	/**
	 * Sets the neighbouring squares
	 * @param left square
	 * @param right square
	 * @param up square
	 * @param down square
	 */
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

	/**
	 * @param content to be set in the square
	 * @param transposed to be set in the square
	 */
	public void setContent(char content, boolean transposed) {
		this.content = content;
	}

	/**
	 * @return true if the square does not contain a blank space
	 */
	public boolean containsLetter() {
		return content != '.' && content != '7';
	}
	/**
	 * @return true if the square is empty
	 */
	public boolean isEmpty() {
		return ! containsLetter();
	}

	public char getContent() {
		return content;
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
