package board;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Board {

	private char[][] board;
	public final static int BOARD_SIZE = 15;
	private int score;


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

	public void remove(int row, int col){
		if(board[row][col] == '.')
			return;
		//deberia restar el valor de la letra
		score -= 1;
		board[row][col]='.';
	}

	public void placePiece(char letter, int row, int col) {
		board[row][col]=letter;
		score += 1;
		//agrego valor de la letra
	}



	public char getLetter(int row, int column) {
		return board[row][column];
	}


	/**
	 * Prints the board
	 */
	public void printBoard() {
		for (int i=0; i<Board.BOARD_SIZE; i++) {
			for (int j=0; j<Board.BOARD_SIZE; j++) {
					System.out.print(board[i][j]);
					System.out.print("\t");
			}
				System.out.print("\n");
			}
			System.out.println();
			System.out.println();
	}

	public char[][] getBoard() {
		return this.board;
	}
	
	public void setBoard(char[][] board){
		for(int i=0; i<Board.BOARD_SIZE; i++){
			for(int j=0; j<Board.BOARD_SIZE; j++){
				this.board[i][j]=board[i][j];
			}
		}
	}
	
	public boolean isCenterFree(){
		return board[7][7] == '7';
	}

//	public Square[][] getTransposedBoard() {
//		Square[][] transposed = new Square[BOARD_SIZE][BOARD_SIZE];
//		for (int row = 0; row < BOARD_SIZE; row++) {
//			for (int column = 0; column < BOARD_SIZE; column++) {
//				transposed[column][row] = board[row][column];
//			}
//		}
//		return transposed;
//	}
	
	public int getScore(){
		return score;
	}
	
	public void setScore(int newScore){
		this.score = newScore;
	}
	
	public boolean verifyNotTransp(String word, int indexOfWord, int row, int col, List<Character> letters){
		
		if(col - indexOfWord < 0)
			return false;
		
		for(int i=indexOfWord+1; i<word.length(); i++){
			if(i+col > 14)
				return false;
			if(board[row][col+i] == '.'){
				if(letters.indexOf(word.charAt(i)) == -1)
					return false;
			}
			else if(board[row][col+i] != word.charAt(i))
				return false;
		}
		
		for(int i = indexOfWord - 1; i>0; i--){
			if(board[row][col-i] == '.'){
				if(letters.indexOf(word.charAt(i)) == -1)
					return false;
			}
			else if(board[row][col-i] != word.charAt(i))
				return false;
		}
		
		return true;
	}
	
	public boolean verifyTransp(String word, int indexOfWord, int row, int col, List<Character> letters){
		
		if(row-indexOfWord <0)
			return false;
		
		for(int i= indexOfWord + 1; i<word.length(); i++){
			
			if(board[row+i-indexOfWord][col+1] != '.' || board[row+i-indexOfWord][col-1] != '.'){
				System.out.println("lo que hay alrededor " + board[row+i-indexOfWord][col+1] + " " + board[row+i-indexOfWord][col-i]);
				return false;
			}
			if(row+i > 14)
				return false;
			
			if(board[row+i-indexOfWord][col] == '.'){
				System.out.println("letrassSantes " +  letters.toString());
				Character letter = word.charAt(i);
				int index = letters.indexOf(letter);
				System.out.println("verify: para la letra " + letter + " el index es " + index);
				if(index == -1)
					return false;
				letters.remove(index);
			}
			else if(board[row+i-indexOfWord][col] != word.charAt(i))
				return false;
		}
		
		//j index of the letter relative to the word and i is the position in the board
		int j =0;
		for(int i = row - indexOfWord; i< row; i++, j++){
			if(board[i][col+1] != '.' || board[i][col-1] != '.'){
				System.out.println("lo que hay alrededor " + board[i][col+1] + " " +board[i][col-1]);
				return false;
				
			}System.out.println("no devuelvo falso todavia");
			
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
	
	public List<Character> putWordNotTransp(String word, int indexOfLetter, int row, int col, List<Character> letters){
		ArrayList<Character> lettersRemaining = new ArrayList<Character>();
		
		for(int i = indexOfLetter +1; i<word.length(); i++){
			if(board[row][col-indexOfLetter+i] == '.'){
				Character letter = word.charAt(i);
				int index = lettersRemaining.indexOf(letter);
				lettersRemaining.remove(index);
				board[row][col-indexOfLetter+i] = word.charAt(i);
				score+=1;
				//score += Dic.values.get(word.charAt(i));
			}
		}
		int j=0;
		for(int i = col-indexOfLetter; i<indexOfLetter; i++, j++){
			if(board[row][i] == '.'){
				Character letter = word.charAt(j);
				int index = lettersRemaining.indexOf(letter);
				lettersRemaining.remove(index);
				board[row][i] = word.charAt(j);
				score+=1;
				//score += Dic.values.get(word.charAt(i));
			}
		}
		return lettersRemaining;
		
	}
	
	public HashSet<Point> putWordTransp(String word, int indexOfLetter, int row, int col, List<Character> letters){
		
		HashSet<Point> modifiedIndexes = new HashSet<Point>();
		//System.out.println(letters.toString());
		int j=0;
		for(int i = row - indexOfLetter; i< row; i++, j++){
			System.out.println( word.charAt(j));
			if(board[i][col] == '.'){
				System.out.println("putt letrassSantes " +  letters.toString());
				int index = letters.indexOf(word.charAt(j));
				letters.remove(index);
				System.out.println("putt letras despues: " + letters.toString());
				board[i][col] = word.charAt(j);
				Point indexs = new Point(i, col);
				modifiedIndexes.add(indexs);
				score+=1;
				//score += Dic.values.get(word.charAt(i));
			}
		}
		//printBoard();
		
		for(int i= indexOfLetter +1; i<word.length(); i++){
			if(board[row + i - indexOfLetter][col] == '.'){
				int index = letters.indexOf(word.charAt(i));
				System.out.println("en put el index de la letra: " + word.charAt(i) +" es " + index);
				letters.remove(index);
				board[row+i-indexOfLetter][col] = word.charAt(i);
				Point indexs = new Point(row+i-indexOfLetter, col);
				modifiedIndexes.add(indexs);
				score+=1;
				//score += Dic.values.get(word.charAt(i));
			}
		}
		System.out.println("las letras que quedaron: " + letters.toString());
		return modifiedIndexes;
	}
	
}

