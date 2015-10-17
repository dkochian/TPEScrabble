package Board;


public class Board {

	private Square[][] board;
	public final int BOARD_SIZE = 15;


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
					//square.setAnchor(true);
					//square.initCrossCheck();
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

	public void placePiece(char letter, Square square, boolean transposed) {
		square.setContent(letter, transposed);
	}

	public boolean isOutofBounds(Square square) {
		return square.getContent() == Square.OUT_BOUNDS;
	}


	public Square getSquare(int row, int column) {
		return board[row][column];
	}


	public void printBoard() {
		for (Square[] row : this.board) {
			for (Square cell : row) {
				if (cell.containsLetter()) {
					System.out.print(cell.getContent());
				}
				System.out.print("\t");
			}
			System.out.println();
			System.out.println();
		}
	}

	public Square[][] getBoard() {
		return this.board;
	}
	
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
}

