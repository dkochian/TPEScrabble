package game;

//import java.util.ArrayList;
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
		Board bestBoardRec = new Board();
		//ArrayList<Square> squares = new ArrayList<Square>();

		HashSet<String> possibleWords = dictionary.getPossibleWords(letters);
		System.out.println("possible words "+ possibleWords.toString());

		for(String word: possibleWords){

			char[] charWord = word.toCharArray();

			for(int i=0; i<word.length(); i++){

				Character letter = word.charAt(i);
				board.placePiece(letter, 7, 7);
				int index = letters.indexOf(charWord[i]);
				letters.remove(index);

				for(int j=i+1; j<word.length(); j++){
					board.placePiece(charWord[j], 7, 7+j-i);

					//System.out.println("LETTERSS2" + letters.toString());
					//System.out.println(charWord[j]);
					index = letters.indexOf(charWord[j]);
					letters.remove(index);
					//System.out.println("LETTERSS2.2" + letters.toString());
				}
				for(int j=i-1; j>=0; j--){
					board.placePiece(charWord[j], 7, 7+j-i);
					index = letters.indexOf(charWord[j]);
					letters.remove(index);
				}

				//System.out.println("LETTERSS4" + letters.toString());

				//System.out.println("antes de llamar a exact con ");
				//System.out.println("entro " + i + "veces");
				System.out.println("puse la palabra: " + word);
				System.out.println("me quedan las letras: " + letters);
				board.printBoard();
				exactSolver(possibleWords, letters, board, bestBoardRec);
				if(bestBoardRec.getScore() > bestBoard.getScore()){
					bestBoard.setBoard(bestBoardRec.getBoard());
					bestBoard.setScore(bestBoardRec.getScore());
				}
				board.initBoard();
				//System.out.println("despues de refresh");
				//board.printBoard();
				this.letters = Reader.readLetters();

				//System.out.println("i igual a " + i);

			}


		}
		//board.printBoard();
		//bestBoard.printBoard();
		return bestBoard;
	}


	//hasta que no me entra otra palabra mas no la voy a considerar maxima solucion
	public void exactSolver(HashSet<String> words, List<Character> letters, Board board, Board bestBoard){

		boolean flag = false;
		for(String word: words){
			if(letters.size() != 0){
				System.out.println("PALABRA " + word);
				for(int i=0; i<word.length(); i++){
					System.out.println("LETRA CON LA QUE TRATO de mi plb " + word.charAt(i));
					for(int j=0; j<Board.BOARD_SIZE; j++){ //row
						for(int k=0; k<Board.BOARD_SIZE; k++){ //col
							int locateLetter = locateLetter(word.charAt(i), j, k);
//							if(locateLetter == 1){ //HORIZONTAL
//								
//								System.out.println(letters.toString());
//								if(board.verifyNotTransp(word, i, j, k, letters)){
//									System.out.println("entre a horizontal! " + word.charAt(i));
//									board.putWordNotTransp(word, i, j, k, letters);
//								//								flag = true;
//								//								List<Integer> placedLettersIndex = board.putWordNotTransposed(word, square, i);
//								//								List<Character> lettersAux = letters;
//								//								System.out.println(placedLettersIndex.isEmpty());
//								//								for(Integer index: placedLettersIndex){
//								//									int row = square.getRow();
//								//									Integer indexOfLetters = lettersAux.indexOf(board.getLetter(row, index));
//								//									lettersAux.remove(indexOfLetters);
//								//								}
//								//								ArrayList<Square> sqs = new ArrayList<Square>();
//								//								
//								//								
//								//								exactSolver(words, lettersAux, board, bestBoard, sqs);
//								//								board.removeAllPiecesNotTransp(placedLettersIndex, square.getRow());
//								}
//							}
							if(locateLetter == -1){//VERTICAL
								if(board.verifyTransp(word, i, j, k, letters)){
									//voy a tener que usar un board auxiliar para volver al estado anterior y hacer el paso recursivo
									//con backtracking
									
									List<Character> remainingLetters = board.putWordTransp(word, i, j, k, letters);

									flag = true;
									exactSolver(words, remainingLetters, board, bestBoard);
									//								
									//								for(Integer index: placedLettersIndex){
									//									int col = square.getColumn();
									//									Integer indexOfLetters = lettersAux.indexOf(board.getLetter(index, col));
									//									lettersAux.remove(indexOfLetters);
									//								board.removeAllPiecesTransp(placedLettersIndex, square.getColumn());
								}
							}
						}



					}
				}
			}
			board.printBoard();
			//System.out.println("SALI DEL FOOOOR!!! Y FLAG =" + flag);

			if(flag){
				System.out.println("SA2!!!");
				if(board.getScore() > bestBoard.getScore()){
					System.out.println("cambio el best board!!!");
					bestBoard.setBoard(board.getBoard());
					bestBoard.setScore(board.getScore());
				}

			}
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




	//	public Board play(long seconds){
	//		boolean gameOver = false;
	//		int score = 0;
	//
	//
	//		long start = System.currentTimeMillis();
	//		long end = start + seconds*1000; // 60 seconds * 1000 ms/sec
	//
	//
	//
	//		while (! gameOver){
	//
	//			try {
	//				Thread.sleep(2000);                 //1000 milliseconds is one second.
	//			} catch(InterruptedException ex) {
	//				Thread.currentThread().interrupt();
	//			}
	//
	//			boolean successfulMove = false;
	//			if(System.currentTimeMillis() < end){
	//				successfulMove = playTurn();
	//			}
	//
	//			if(! successfulMove || computer.placedAllPieces()){
	//				gameOver = true;
	//				continue;
	//			}
	//
	//			//gui.updateBoard();
	//
	//			board.printBoard();
	//			System.out.println();			
	//
	//			System.out.println("----------------------------------");
	//			System.out.println();
	//
	//			//score = computer.getScore();
	//			//gui.updateScores(score);
	//
	//		}
	//		return this.board;
	//	}

	//	protected boolean playTurn() {
	//		Move move = computer.generateMove();
	//
	//		if (move == null){
	//			return false;
	//		}
	//
	//		makeMove(move);
	//		return true;
	//	}

	/**
	 * Make the move. Place a word on the board, letters already located on the
	 * board should not be touched. 
	 * 
	 * @param move
	 * @return
	 */
	//	public void makeMove(Move move) {
	//
	//		LetterNode wn = move.getLetterNode();
	//
	//		Square sq = board.getSquare(move.getEndSquare().getRow(), move.getEndSquare().getColumn());
	//
	//
	//		while (wn != null && sq.getContent() != Square.OUT_BOUNDS) {
	//
	//			char letter = wn.getLetter();
	//			boolean isTransposed = move.isTransposed();
	//			if (! sq.containsLetter()) {
	//				board.placePiece(letter, sq.getRow(), sq.getColumn(), isTransposed);
	//
	//				computer.removePieceFromHand(letter);
	//				wn = wn.getPreviousLetter();
	//
	//			} else {
	//				wn = wn.getPreviousLetter();
	//			}
	//
	//			sq = sq.getNextLeft(isTransposed);
	//
	//		}
	//	}


	public Dic getDictionary(){
		return this.dictionary;
	}

}
