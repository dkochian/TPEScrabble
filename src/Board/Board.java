package board;

import java.awt.Point;
import java.util.HashSet;
import java.util.List;

import dictionary.Dic;

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
		score -= Dic.getLetterValue(board[row][col]);
		board[row][col]='.';
	}

	public void placePiece(char letter, int row, int col) {
		board[row][col]=letter;
		score += Dic.getLetterValue(letter);
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

	public boolean containsLetter(int row, int column){
		return this.board[row][column] != '.';
	}
	
	public int getScore(){
		return score;
	}
	
	public void setScore(int newScore){
		this.score = newScore;
	}
	
	public boolean verifyNotTransp(String word, int indexOfWord, int row, int col, List<Character> letters){
		
		if(board[row][col] != word.charAt(indexOfWord))
			return false;
		
		if(col - indexOfWord < 0)
			return false;
		
		if(word.length()+col-indexOfWord > 14)
			return false;
		
		for(int i=indexOfWord+1; i<word.length(); i++){
			int aux = col -indexOfWord + i;
			System.out.println(" la letra qeu esoty verificando es " + word.charAt(i) + " " + aux);
			System.out.println("lo uqe hay en estos casilleros es " + board[row+1][aux] + " " + board[row-1][aux] + " la fila es " + row);
			
			if(row < 14 && board[row+1][aux] != '.'){
				System.out.println("ENTRO ACA DEVUELVO FALSO");
				return false;
			}
			if(row > 0 && board[row-1][aux] !='.'){
				System.out.println("ENTRO ACA DEVUELVO FALSO");
				return false;
			}
			
			if(board[row][col+i-indexOfWord] == '.'){
				
				int index = letters.indexOf(word.charAt(i));
				if(index == -1)
					return false;
				letters.remove(index);
			}
			else if(board[row][col+i-indexOfWord] != word.charAt(i))
				return false;
		}
		for(int i = 0; i< indexOfWord; i++){
			int aux = col -indexOfWord + i;
			System.out.println(" la letra qeu esoty verificando es " + word.charAt(i) + " " + aux);
			if(col - i <0)
				return false;
			
			if(row+1 <= 14 && board[row+1][col- indexOfWord +i] != '.')
				return false;
			
			if(row-1 >= 0 && board[row-1][col- indexOfWord +i] != '.'){
			//	System.out.println("tiene algo alrededor metiendolo horizontalmente");
				return false;
			}
			
			if(board[row][col- indexOfWord +i] == '.'){
				int index = letters.indexOf(word.charAt(i));
				if(index == -1)
					return false;
				letters.remove(index);
			}
			else if(board[row][col- indexOfWord +i] != word.charAt(i))
				return false;
		}
		System.out.println("las letras que me quedan despeus del verify " + letters.toString());
	//	System.out.println("lo puedo meter horizontalemnte");
		
		return true;
	}
	
	public boolean verifyTransp(String word, int indexOfWord, int row, int col, List<Character> letters){
	//	System.out.println("las letras que tengo " + letters.toString());
	//	System.out.println("LETRA QUE VERIFICO " + word.charAt(indexOfWord) +" en " + row + col);
		
		if(row-indexOfWord <0)
			return false;
		
		if(row+	word.length() -indexOfWord > 14)
			return false;
		
		for(int i= indexOfWord + 1; i<word.length(); i++){
		//	System.out.println("verfico los costados " + board[row+i-indexOfWord][col+i] + " " + board[row+i-indexOfWord][col-1]);
			if(( col != 14 && board[row+i-indexOfWord][col+1] != '.') || (col != 0 &&  board[row+i-indexOfWord][col-1] != '.')){
				
				//System.out.println("lo que hay alrededor " + board[row+i-indexOfWord][col+1] + " " + board[row+i-indexOfWord][col-i]);
				return false;
			}
			
			if(board[row+i-indexOfWord][col] == '.'){
				//System.out.println("letrassSantes " +  letters.toString());
				Character letter = word.charAt(i);
				int index = letters.indexOf(letter);
				//System.out.println("verify: para la letra " + letter + " el index es " + index);
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
			if((col != 14 && board[i][col+1] != '.') ||( col != 0 && board[i][col-1] != '.')){
				//System.out.println("lo que hay alrededor " + board[i][col+1] + " " +board[i][col-1]);
				return false;
				
			}//System.out.println("no devuelvo falso todavia");
			
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
				System.out.println("la letra que hace qeu explote " + letter + " y las letras para que explote todo");
				System.out.println(letters.toString());
				letters.remove(index);
				Point indexs = new Point(row, i);
				modifiedIndexes.add(indexs);
				board[row][i] = word.charAt(j);
				score += Dic.values.get(word.charAt(j));
			}
		}
	//	System.out.println("agregue la palabra " + word);
	//	printBoard();
		return modifiedIndexes;
		
	}
	
	public HashSet<Point> putWordTransp(String word, int indexOfLetter, int row, int col, List<Character> letters){
		
		HashSet<Point> modifiedIndexes = new HashSet<Point>();
		int j=0;
		//System.out.println("plb: " + word +" la letra que me encontro es " + word.charAt(indexOfLetter) + " en la col " + col);
		for(int i = row - indexOfLetter; i< row; i++, j++){
			
			if(board[i][col] == '.'){
			//	System.out.println("pongo la letra " + word.charAt(j));
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
			//	System.out.println("en put el index de la letra: " + word.charAt(i) +" es " + index);
				board[row+i-indexOfLetter][col] = word.charAt(i);
				Point indexs = new Point(row+i-indexOfLetter, col);
				modifiedIndexes.add(indexs);
				letters.remove(index);
				score += Dic.values.get(word.charAt(i));
			}
		}
		//printBoard();
		//System.out.println("agregue la palabra " + word);
		printBoard();
		return modifiedIndexes;
	}
	
}

