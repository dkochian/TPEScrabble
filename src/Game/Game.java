package game;

//import java.util.ArrayList;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import board.Board;
//import board.Square;
import dictionary.Dic;
import files.Reader;
//import gui.Scrabble;
//import player.Player;

public class Game {
	private Board board;
	private List<Character> letters;
	//private Player computer;
	private Dic dictionary;
	//private Scrabble gui;

	public Game() {
		this.dictionary = new Dic();
		this.board = new Board();
		this.letters = Reader.readLetters();
		//		this.computer = new Player(board, letters, dictionary);
		//		this.gui = new Scrabble(this.board);
	}


	public Board firstWord(){
		
		Board bestBoard = new Board();

		HashSet<String> possibleWords = dictionary.getPossibleWords(letters);
		//System.out.println("possible words "+ possibleWords.toString());

		for(String word: possibleWords){
			//System.out.println(letters.toString());
			char[] charWord = word.toCharArray();

			for(int i=0; i<word.length(); i++){

				Character letter = word.charAt(i);
				board.placePiece(letter, 7, 7);
				int index = letters.indexOf(charWord[i]);
				letters.remove(index);

				for(int j=i+1; j<word.length(); j++){
					board.placePiece(charWord[j], 7, 7+j-i);
					index = letters.indexOf(charWord[j]);
					letters.remove(index);
				}
				for(int j=i-1; j>=0; j--){
					board.placePiece(charWord[j], 7, 7+j-i);
					index = letters.indexOf(charWord[j]);
					letters.remove(index);
				}
				//	System.out.println("con primera palabra " + word);
				//board.printBoard();
				Board bestBoardRec = new Board();
				//System.out.println("primer bestBoard");
				//bestBoardRec.printBoard();
				//System.out.println("board con el que entro");
				//board.printBoard();
				exactSolver(possibleWords, letters, board, bestBoardRec);

				if(bestBoardRec.getScore() > bestBoard.getScore()){
					bestBoard.setBoard(bestBoardRec.getBoard());
					bestBoard.setScore(bestBoardRec.getScore());
				}

				board.initBoard();
				this.letters = Reader.readLetters();
			}
		}
		//board.printBoard();
		System.out.println("mejor tablero");
		bestBoard.printBoard();
		System.out.println("puntaje: " + bestBoard.getScore());
		return bestBoard;
	}


	//hasta que no me entra otra palabra mas no la voy a considerar maxima solucion
	public void exactSolver(HashSet<String> words, List<Character> letters, Board board, Board bestBoard){
		
		if(board.getScore() > bestBoard.getScore()){
			//System.out.println("cambio el best boardARRIBA DE TODO!!!");
			//System.out.println("score best board: " +bestBoard.getScore());
			//System.out.println("score del nuevo best Board: "+ board.getScore());
			bestBoard.setBoard(board.getBoard());
			bestBoard.setScore(board.getScore());
			bestBoard.printBoard();
		}
		
		if(letters.size() > 0){
			for(String word: words){

				//System.out.println("plb que quiero agregar: " + word);
				for(int i=0; i<word.length(); i++){

					//System.out.println("LETRA CON LA QUE TRATO de mi plb " + word.charAt(i));

					for(int j=0; j<Board.BOARD_SIZE; j++){ //row
						for(int k=0; k<Board.BOARD_SIZE; k++){ //col

							int locateLetter = locateLetter(word.charAt(i), j, k);
							//System.out.println("locateLetter " + locateLetter);

							if(locateLetter == 1){ //HORIZONTAL
								List<Character> auxLetters = new ArrayList<Character>(letters);
								if(board.verifyNotTransp(word, i, j, k, auxLetters)){
//									List<Character> remainingLetters2 = new ArrayList<Character>(letters);
//									HashSet<Point> indexes2 = board.putWordNotTransp(word, i, j, k, remainingLetters2);
//									board.printBoard();
//									exactSolver(words, remainingLetters2, board, bestBoard);
//									for(Point index: indexes2){
//										board.remove(index.x, index.y);
//									}
								}
							}

							if(locateLetter == -1){//VERTICAL
								List<Character> auxLetters2 = new ArrayList<Character>(letters);
								if(board.verifyTransp(word, i, j, k, auxLetters2)){
									//System.out.println("entre de nuevo con " + word.charAt(i) + " i " + i);
									List<Character> remainingLetters = new ArrayList<Character>(letters);
									//	System.out.println("salgo");
									HashSet<Point>indexes =  board.putWordTransp(word, i, j, k, remainingLetters);
									board.printBoard();
									//System.out.println("score: " + board.getScore());
									exactSolver(words, remainingLetters, board, bestBoard);

									for(Point index: indexes){
										board.remove(index.x, index.y);
									}

								}
							}
						}



					}
				}
			}
			//board.printBoard();
		}

	}

	private Integer locateLetter(char letter, int row, int col){
		if(board.getLetter(row, col) == letter){
			if(board.getBoard()[row+1][col] == '.' && board.getBoard()[row-1][col] == '.')
				return -1; //VERTICAL
			if(board.getBoard()[row][col+1] == '.' && board.getBoard()[row][col-1] == '.')
				return 1; //HORIZONTAL	
		}
		return 0;
	}


	public Dic getDictionary(){
		return this.dictionary;
	}

}
