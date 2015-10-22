package game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import board.Board;
import board.Square;
import dictionary.Dic;
import files.Reader;
import gui.Scrabble;
import player.Player;

public class Game {
	private Board board;
	private List<Character> letters;
	private Player computer;
	private Dic dictionary;
	private Scrabble gui;

	public Game() {
		this.dictionary = new Dic();
		this.board = new Board();
		this.letters = Reader.readLetters();
		this.computer = new Player(board, letters, dictionary);
		this.gui = new Scrabble(this.board);
	}
	
	
	public Board firstWord(){
		
		Board bestBoard = new Board();
		//Integer bestScore = 0;
		HashSet<String> possibleWords = computer.possibleWords();
		
		System.out.println(possibleWords.toString());
		
		for(String word: possibleWords){
			
			if(board.isCenterFree()){
				char[] charWord = word.toCharArray();
				
				for(int i=0; i<word.length(); i++){
					
					Character letter = word.charAt(i);
					int row=7, col=7;
					Square center = new Square(letter, row, col);
					Square sq2 = center;
					board.placePiece(letter, 7, 7, false);
					for(int j=i+1; i<word.length() - i; j++){
						Square sq = new Square(charWord[j], row, 7+j);
						sq.setLeft(sq2);
						board.placePiece(charWord[j], sq.getRow(), sq.getColumn(), false);
						sq2.setRight(sq);
						sq2=sq;	
					}
					sq2 = center;
					for(int j=i-1; j>word.length() - i; j--){
						Square sq=new Square(charWord[j], row, j);
						sq.setRight(sq2);
						board.placePiece(charWord[j], sq.getRow(), sq.getColumn(), false);
						sq2.setLeft(sq);
						sq2=sq;
					}
				}
			}
		}
		return bestBoard;
	}
	
	
	public void exactSolver(HashSet<String> words, ArrayList<Character> letters, Board board, Board bestBoard, Integer bestScore){

		for(String word: words){
			
			for(int i=0; i<word.length(); i++){
				for(Square square : board.getAllSquares()){
					if(locateLetter(word.charAt(i), board, square) == 1){
						if(Board.verifyNotTransp(board, word, square, i)){
							board.putWordNotTransposed(word, square);
							ArrayList<Character> lettersAux = letters;
							for(Character letter: word.toCharArray()){
								lettersAux.remove(letter);
							}
							exactSolver(words, lettersAux, board, bestBoard, bestScore);
							
							
							//llamo a la funcion sin las letras y con la palabra
							//metida en el board
						}
					}
					if(locateLetter(word.charAt(i), board, square) == -1){
						if(Board.verifyNotTransp(board, word, square, i)){
							board.putWordTransposed(word, square);
							
							//llamo a la funcion sin las letras y con la palabra
							//metida en el board
						}
					}
				}



			}
		}
	}
		
		
	
	
	private Integer locateLetter(Character letter, Board board, Square sq){
		
			if( sq.getContent().equals(letter)){
				if(sq.getRight() == null && sq.getLeft() == null){
					return 1;
				}if(sq.getDown() == null && sq.getUp() == null){
					return -1;
				}
			}
		return 0;
	}
	
	
	
	
	public Board play(long seconds){
		boolean gameOver = false;
		int score = 0;
		
		
		long start = System.currentTimeMillis();
		long end = start + seconds*1000; // 60 seconds * 1000 ms/sec
		
		
		
		while (! gameOver){
			
			try {
				Thread.sleep(2000);                 //1000 milliseconds is one second.
			} catch(InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
			
			boolean successfulMove = false;
			if(System.currentTimeMillis() < end){
				successfulMove = playTurn();
			}
			
			if(! successfulMove || computer.placedAllPieces()){
				gameOver = true;
				continue;
			}

			gui.updateBoard();

			board.printBoard();
			System.out.println();			

			System.out.println("----------------------------------");
			System.out.println();

			score = computer.getScore();
			gui.updateScores(score);

		}
		return this.board;
	}

	protected boolean playTurn() {
		Move move = computer.generateMove();
		
		if (move == null){
			return false;
		}
		
		makeMove(move);
		return true;
	}

	public Dic getDictionary() {
		return dictionary;
	}

	/**
	 * Make the move. Place a word on the board, letters already located on the
	 * board should not be touched. 
	 * 
	 * @param move
	 * @return
	 */
	public void makeMove(Move move) {

		LetterNode wn = move.getLetterNode();

		Square sq = board.getSquare(move.getEndSquare().getRow(), move.getEndSquare().getColumn());
		
		
		while (wn != null && sq.getContent() != Square.OUT_BOUNDS) {

			char letter = wn.getLetter();
			boolean isTransposed = move.isTransposed();
			if (! sq.containsLetter()) {
				board.placePiece(letter, sq.getRow(), sq.getColumn(), isTransposed);

				computer.removePieceFromHand(letter);
				wn = wn.getPreviousLetter();
				
			} else {
				wn = wn.getPreviousLetter();
			}

			sq = sq.getNextLeft(isTransposed);

		}
	}
}
