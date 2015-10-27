package board;

import java.awt.Point;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import dictionary.Dic;

public class Board {

	private char[][] board;
	public final static int BOARD_SIZE = 15;
	private int score;
	private final static int HORIZONTAL_WORD = 1;
	private final static int VERTICAL_WORD = -1;


	public Board(){
		initBoard();
	}

	public void initBoard() {
		score = 0;
		this.board = new char[BOARD_SIZE][BOARD_SIZE];
		for (int row = 0; row < BOARD_SIZE; row++) {
			for (int column = 0; column < BOARD_SIZE; column++) {
				board[row][column] = '.';
				if (row == 7 && column == 7) {
					board[row][column]='7';
				}
			}
		}

	}
	/**
	 * Removes a letter from the board and subtracts the score of that letter. If there was not a letter returns without doing something.
	 * @param row
	 * @param col
	 */
	public void remove(int row, int col){
		if(board[row][col] == '.')
			return;
		score -= Dic.getLetterValue(board[row][col]);
		board[row][col]='.';
	}
	
	/**
	 * Places a letter on the board and adds the score that corresponds to that letter.
	 * @param letter
	 * @param row
	 * @param col
	 */
	public void placePiece(char letter, int row, int col) {
		board[row][col]=letter;
		score += Dic.getLetterValue(letter);
	}
	
	public char getLetter(int row, int column) {
		return board[row][column];
	}

	/**
	 * Prints the board
	 */
	public void printBoard() {
		System.out.println();
		for (int i= -1; i < Board.BOARD_SIZE; i++) {
			if(i >= 0){
				System.out.print(i + 1);
				System.out.print("\t");
			}
			for (int j=0; j < Board.BOARD_SIZE; j++) {
				if(i < 0){
					System.out.print("\t");
					System.out.print(j + 1);
				}else{
					System.out.print(board[i][j]);
					System.out.print("\t");
				}
			}
			System.out.print("\n");
		}
		System.out.println();
		System.out.println();
	}

	public char[][] getBoard() {
		return this.board;
	}
	
	/**
	 * Clones the board into a new one.
	 * @param board
	 */
	public void setBoard(char[][] board){
		for(int i=0; i<Board.BOARD_SIZE; i++){
			for(int j=0; j<Board.BOARD_SIZE; j++){
				this.board[i][j]=board[i][j];
			}
		}
	}

	public boolean containsLetter(int row, int column){
		return this.board[row][column] != '.';
	}
	
	public int getScore(){
		return score;
	}
	
	public void setScore(int newScore){
		this.score = newScore;
	}
	/**
	 * Cambiar el nombre de flag o explicar que es
	 * @param word
	 * @param indexOfWord
	 * @param row
	 * @param col
	 * @param letters
	 * @return true if the word is not transposed
	 */
	public boolean verifyNotTransp(String word, int indexOfWord, int row, int col, List<Character> letters){
		
		boolean flag = false;
		
		if(board[row][col] != word.charAt(indexOfWord))
			return false;
		
		if(col - indexOfWord < 0 || (col-indexOfWord > 0 && board[row][col - indexOfWord - 1] != '.'))
			return false;
		
		if(word.length()+col-indexOfWord > 14 || (col + word.length() - indexOfWord <= 14 && board[row][col+word.length() - indexOfWord] != '.'))
			return false;
		
		for(int i=indexOfWord+1; i<word.length(); i++){
			int aux = col -indexOfWord + i;
			
			if(row < 14 && board[row+1][aux] != '.'){
				return false;
			}
			if(row > 0 && board[row-1][aux] !='.'){
				return false;
			}
			
			if(board[row][col+i-indexOfWord] == '.'){
				
				int index = letters.indexOf(word.charAt(i));
				if(index == -1)
					return false;
				letters.remove(index);
				flag = true;
			}
			else if(board[row][col+i-indexOfWord] != word.charAt(i))
				return false;
		}
		for(int i = 0; i< indexOfWord; i++){
			if(col - i <0)
				return false;
			
			if(row+1 <= 14 && board[row+1][col- indexOfWord +i] != '.')
				return false;
			
			if(row-1 >= 0 && board[row-1][col- indexOfWord +i] != '.'){
				return false;
			}
			
			if(board[row][col- indexOfWord +i] == '.'){
				int index = letters.indexOf(word.charAt(i));
				if(index == -1)
					return false;
				letters.remove(index);
				flag = true;
			}
			else if(board[row][col- indexOfWord +i] != word.charAt(i))
				return false;
		}		
		return flag;
	}
	
	/**
	 * 
	 * @param word
	 * @param indexOfWord
	 * @param row
	 * @param col
	 * @param letters
	 * @return true if the word is transposed
	 */
	public boolean verifyTransp(String word, int indexOfWord, int row, int col, List<Character> letters){

		if(row-indexOfWord <0 || (row-indexOfWord > 0 && board[row-indexOfWord-1][col] != '.'))
			return false;
		
		if(row+	word.length() -indexOfWord > 14 || (row+word.length() -indexOfWord <=14 && board[row+word.length()-indexOfWord][col] != '.'))
			return false;
		
	
		for(int i= indexOfWord + 1; i<word.length(); i++){
			if(( col != 14 && board[row+i-indexOfWord][col+1] != '.') || (col != 0 &&  board[row+i-indexOfWord][col-1] != '.')){
				return false;
			}
			
			if(board[row+i-indexOfWord][col] == '.'){
				Character letter = word.charAt(i);
				int index = letters.indexOf(letter);
				if(index == -1)
					return false;
				letters.remove(index);
			}
			else if(board[row+i-indexOfWord][col] != word.charAt(i))
				return false;
		}
		int j =0;
		
		for(int i = row - indexOfWord; i< row; i++, j++){
			if((col != 14 && board[i][col+1] != '.') ||( col != 0 && board[i][col-1] != '.')){
				return false;
			}
			
			if(board[i][col] == '.'){
				Character letter = word.charAt(j);
				int index = letters.indexOf(letter);
				if(index == -1)
					return false;
				letters.remove(index);
			}
			else if(board[i][col] != word.charAt(j))
				return false;
		}
		return true;
		
	}
	
	/**
	 * 
	 * @param word
	 * @param indexOfLetter
	 * @param row
	 * @param col
	 * @param letters
	 * @return
	 */
	public HashSet<Point> putWordNotTransp(String word, int indexOfLetter, int row, int col, List<Character> letters){	
		HashSet<Point> modifiedIndexes = new HashSet<Point>();
		
		for(int i = indexOfLetter +1; i<word.length(); i++){
			if(board[row][col-indexOfLetter+i] == '.'){
				Character letter = word.charAt(i);
				int index = letters.indexOf(letter);
				letters.remove(index);
				Point indexs = new Point(row, col-indexOfLetter + i);
				modifiedIndexes.add(indexs);
				board[row][col-indexOfLetter+i] = word.charAt(i);
				score += Dic.values.get(word.charAt(i));
			}
		}
		int j=0;
		for(int i = col-indexOfLetter; i<col; i++, j++){
			if(board[row][i] == '.'){
				Character letter = word.charAt(j);
				int index = letters.indexOf(letter);
				letters.remove(index);
				Point indexs = new Point(row, i);
				modifiedIndexes.add(indexs);
				board[row][i] = word.charAt(j);
				score += Dic.values.get(word.charAt(j));
			}
		}
		return modifiedIndexes;
		
	}
	
	/**
	 * 
	 * @param word
	 * @param indexOfLetter
	 * @param row
	 * @param col
	 * @param letters
	 * @return
	 */
	public HashSet<Point> putWordTransp(String word, int indexOfLetter, int row, int col, List<Character> letters){
		
		HashSet<Point> modifiedIndexes = new HashSet<Point>();
		int j=0;
		for(int i = row - indexOfLetter; i< row; i++, j++){
			
			if(board[i][col] == '.'){
				Character letter = word.charAt(j);
				int index = letters.indexOf(letter);
				letters.remove(index);
				board[i][col] = word.charAt(j);
				Point indexs = new Point(i, col);
				modifiedIndexes.add(indexs);
				score += Dic.values.get(word.charAt(j));
			}
		}
		
		
		for(int i= indexOfLetter+1; i<word.length(); i++){
			if(board[row + i - indexOfLetter][col] == '.'){
				int index = letters.indexOf(word.charAt(i));
				board[row+i-indexOfLetter][col] = word.charAt(i);
				Point indexs = new Point(row+i-indexOfLetter, col);
				modifiedIndexes.add(indexs);
				letters.remove(index);
				score += Dic.values.get(word.charAt(i));
			}
		}
		return modifiedIndexes;
	}
	
	
	public static Integer locateLetter(char letter, int row, int col, Board board){
		if(board.getLetter(row, col) == letter){
			if((row == 14 || ! board.containsLetter(row + 1, col)) && (row == 0 || ! board.containsLetter(row - 1, col)))
				return VERTICAL_WORD;
			if((col == 14 || ! board.containsLetter(row, col + 1)) && (col ==0 || ! board.containsLetter(row , col - 1)))
				return HORIZONTAL_WORD; 
		}
		return 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(board);
		result = prime * result + score;
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
		Board other = (Board) obj;
		if (!Arrays.deepEquals(board, other.board))
			return false;
		if (score != other.score)
			return false;
		return true;
	}
	
}

