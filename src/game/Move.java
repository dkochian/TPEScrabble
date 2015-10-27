package game;

import java.util.ArrayList;
import java.util.List;

import board.Board;

public class Move {

	private String word;
	private int row;
	private int col;
	private boolean transp;
	private final static int HORIZONTAL_WORD = 1;
	private final static int VERTICAL_WORD = -1;

	public Move(String word, int row, int col, boolean transp){
		this.word = word;
		this.row = row;
		this.col = col;
		this.setTransp(transp);
	}

	/**
	 * 
	 * @param board
	 * @param words
	 * @param letters
	 * @return a List with all the possible Moves that can be formed with the words and letters available
	 */
	public static List<Move> getAllMoves(Board board, List<String> words, List<Character> letters){
		List<Move> moves = new ArrayList<Move>();
		for(String word: words){
			
			for(int row=0; row<Board.BOARD_SIZE; row++){
				for(int col=0; col<Board.BOARD_SIZE; col++){
					
					for(int i=0; i <word.length(); i++){

						Integer locateLetter = Board.locateLetter(word.charAt(i), row, col, board);
						if(locateLetter == HORIZONTAL_WORD){
							
							List<Character> lettersAux = new ArrayList<Character>(letters);
							if(board.verifyNotTransp(word, i, row, col, lettersAux)){
								Move newMove = new Move(word, row, col-i, false);
								moves.add(newMove);
							}
							
						}else if(locateLetter == VERTICAL_WORD){
							
							List<Character> lettersAux2 = new ArrayList<Character>(letters);
							if(board.verifyTransp(word, i, row, col, lettersAux2)){
								Move newMove2 = new Move(word, row-i, col, true);
								moves.add(newMove2);
							}
							
						}
					}

				}
			}
		}
		return moves;
	}
	

	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getCol() {
		return col;
	}
	public void setCol(int col) {
		this.col = col;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + col;
		result = prime * result + row;
		result = prime * result + ((word == null) ? 0 : word.hashCode());
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
		Move other = (Move) obj;
		if (col != other.col)
			return false;
		if (row != other.row)
			return false;
		if (word == null) {
			if (other.word != null)
				return false;
		} else if (!word.equals(other.word))
			return false;
		return true;
	}


	public boolean isTransp() {
		return transp;
	}


	public void setTransp(boolean transp) {
		this.transp = transp;
	}

	public String toString(){
		return "Word: " + word + " row and column" + row + col + " is transp? " + transp;
	}


}
