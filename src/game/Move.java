package game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import board.Board;

public class Move {

	private String word;
	private int row;
	private int col;
	private boolean transp;

	public Move(String word, int row, int col, boolean transp){
		this.word = word;
		this.row = row;
		this.col = col;
		this.setTransp(transp);
	}


	public static List<Move> getAllMoves(Board board, List<String> words, List<Character> letters){
		List<Move> moves = new ArrayList<Move>();
		//System.out.println("letras con las que entro " + letters.toString());
		for(String word: words){
			for(int row=0; row<Board.BOARD_SIZE; row++){
				for(int col=0; col<Board.BOARD_SIZE; col++){
					for(int i=0; i <word.length(); i++){

						Integer locateLetter = locateLetter(word.charAt(i), row, col, board);
						if(locateLetter == 1){
							List<Character> lettersAux = new ArrayList<Character>(letters);
							if(board.verifyNotTransp(word, i, row, col, lettersAux)){
								Move newMove = new Move(word, row, col-i, false);
								moves.add(newMove);
							}
						}
						else if(locateLetter == -1){
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
		System.out.println("palabras que puedo meter " + moves.toString());
		//System.out.println("letras con las que salgo " + letters.toString());
		return moves;
	}

	private static Integer locateLetter(char letter, int row, int col, Board board){
		if(board.getLetter(row, col) == letter){
			if((row == 14 || board.getBoard()[row+1][col] == '.') && (row == 0 || board.getBoard()[row-1][col] == '.'))
				return -1; //VERTICAL
			if((col == 14 || board.getBoard()[row][col+1] == '.') && (col ==0 || board.getBoard()[row][col-1] == '.'))
				return 1; //HORIZONTAL	
		}
		return 0;
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
		return "palabra: " + word + " fila y columna " + row + col + " es transp? " + transp;
	}


}
