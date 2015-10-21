package board;

import java.util.ArrayList;

public class Board {

	private Square[][] board;
	public static final int BOARD_SIZE = 15;
	private ArrayList<Square> placedSquares= new ArrayList<Square>();

	public Board()
	{
		initBoard();
	}

	private void initBoard() {
		
		board = new Square[BOARD_SIZE][BOARD_SIZE];
		

		for (int row = 0; row < BOARD_SIZE; row++) {
			for (int column = 0; column < BOARD_SIZE; column++) {
				board[row][column] = new Square('.', row, column);
				Square square = board[row][column];
				if (row == 7 && column == 7) {
					square.setCenterSquare();
				}
			}
		}

		for (int row = 0; row < BOARD_SIZE; row++) {
			for (int column = 0; column < BOARD_SIZE; column++) {
				Square square = board[row][column];
				Square left = (column <= 0) ? new Square(Square.OUT_BOUNDS, -1, -1): board[row][column - 1];
				Square right = (column >= BOARD_SIZE - 1) ? new Square(Square.OUT_BOUNDS, -1, -1): board[row][column + 1];
				Square up = (row <= 0) ? new Square(Square.OUT_BOUNDS, -1, -1): board[row - 1][column];
				Square down = (row >= BOARD_SIZE - 1) ? new Square(Square.OUT_BOUNDS,-1, -1): board[row + 1][column];
				square.setNeighbours(left, right, up, down);
			}
		}

	}

	/**
	 * @param letter to be placed
	 * @param square where letter will be placed
	 * @param transposed 
	 */
	public void placePiece(char letter, Square square, boolean transposed) {
		
		square.setContent(letter, transposed);
		//KOCHI FIJATE SI TE PARECE ESTO, no entiendo como ahces sino, no usas el board
		board[square.getRow()][square.getColumn()] = square;
		placedSquares.add(square);
	}

	/**
	 * @param square that will be checked if within the boundaries
	 * @return true if it is within boundaries, false if not.
	 */
	public boolean isOutofBounds(Square square) {
		return square.getContent() == Square.OUT_BOUNDS;
	}

	public ArrayList<Square> getAllSquares(){
		return placedSquares;
	}

	public Square getSquare(int row, int column) {
		return board[row][column];
	}


	/**
	 * Prints the board
	 */
	public void printBoard() {
		for (Square[] row : this.board) {
			for (Square cell : row) {
				System.out.print(cell.getContent());
				System.out.print("\t");
			}
			System.out.println();
			System.out.println();
		}
	}

	public Square[][] getBoard() {
		return this.board;
	}
	
	//no entiendo se seta comparando un string con un square...
	public boolean isCenterFree(){
		return board[7][7].equals("7");
	}

	public Square[][] getTransposedBoard() {
		Square[][] transposed = new Square[BOARD_SIZE][BOARD_SIZE];
		for (int row = 0; row < BOARD_SIZE; row++) {
			for (int column = 0; column < BOARD_SIZE; column++) {
				transposed[column][row] = board[row][column];
			}
		}
		return transposed;
	}
	
	public static boolean verifyNotTransp(Board board, String word, Square sq, int indexOfLetter){
		
		int row=sq.getRow();
		int col=sq.getColumn();
		
		for(int i=indexOfLetter+1; i<word.length(); i++){
			if(!board.board[row][col+i].getContent().equals('.') && !board.board[row][col+i].getContent().equals(word.charAt(i)))
				return false;
		}	
		for(int i=indexOfLetter-1; i>0; i--){
			if(!board.board[row][col-i].getContent().equals('.') && !board.board[row][col-i].getContent().equals(word.charAt(i)))
				return false;
		}
		
		return true; 
	}
	public static boolean verifyTransp(Board board, String word, Square sq, int indexOfLetter){
		
		int row=sq.getRow();
		int col=sq.getColumn();
		for(int i=indexOfLetter +1; i<word.length(); i++){
			if(!board.board[row+i][col].getContent().equals('.') && !board.board[row+i][col].getContent().equals(word.charAt(i)))
				return false;
		}
		for(int i=indexOfLetter -1; i>0; i--){
			if(!board.board[row-i][col].getContent().equals('.') && !board.board[row-i][col].getContent().equals(word.charAt(i)))
				return false;
		}
		return true;
	}
}

