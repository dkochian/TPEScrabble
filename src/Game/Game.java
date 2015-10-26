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
	private final static int T = 7;
	//private Scrabble gui;

	public Game() {
		this.dictionary = new Dic();
		this.board = new Board();
		this.letters = Reader.readLetters();
		//		this.computer = new Player(board, letters, dictionary);
		//		this.gui = new Scrabble(this.board);
	}

	public Board firstWordExact(){
		Board bestBoard = new Board();
		Board board = new Board();
		ArrayList<String> possibleWords = dictionary.getPossibleWords(letters);

		for(String word: possibleWords){

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
				Board bestBoardRec = new Board();
				exactSolver(possibleWords, letters, board, bestBoardRec);

				if(bestBoardRec.getScore() > bestBoard.getScore()){
					bestBoard.setBoard(bestBoardRec.getBoard());
					bestBoard.setScore(bestBoardRec.getScore());
				}

				board.initBoard();
				this.letters = Reader.readLetters();
			}
		}
		System.out.println("mejor tablero");
		bestBoard.printBoard();
		System.out.println("puntaje: " + bestBoard.getScore());
		return bestBoard;
	}

	public void approximateSolution(long endTime){
		Board bestBoard = new Board();
		long startTime = System.currentTimeMillis();
		//Array<String, Integer> wordValues = dictionary.getWordValues(letters);
		ArrayList<String> possibleWords = dictionary.getPossibleWords(letters);

		while(startTime < endTime){

			board.initBoard();
			letters = Reader.readLetters();
			Board bestBoardAux = new Board();

			//pongo la primera palabra
			String randWord = possibleWords.get((int) ((Math.random())*possibleWords.size()));
			Integer indexOfWord = (int) ((Math.random())*randWord.length());
			board.placePiece(randWord.charAt(indexOfWord), 7, 7);
			int index = letters.indexOf(randWord.charAt(indexOfWord));
			letters.remove(index);

			for(int i= indexOfWord + 1; i< randWord.length(); i++){
				board.placePiece(randWord.charAt(i), 7, 7+i-indexOfWord);
				index = letters.indexOf(randWord.charAt(i));
				letters.remove(index);
			}
			for(int i = indexOfWord - 1; i>=0; i--){
				board.placePiece(randWord.charAt(i), 7, 7-indexOfWord+i);
				index = letters.indexOf(randWord.charAt(i));
				letters.remove(index);
			}
			List<Character> lettersAux = new ArrayList<Character>(letters);
			board.printBoard();
			System.out.println("letras con las que entro: " + lettersAux.toString());
			approximateSolution2(board, bestBoardAux, lettersAux, randWord, possibleWords);
			if(bestBoardAux.getScore() > bestBoard.getScore()){
				bestBoard.setBoard(bestBoardAux.getBoard());
				bestBoard.setScore(bestBoardAux.getScore());
			}
			bestBoard.printBoard();
			//}
		}
		System.out.println("Mejor solucion final: ");
		bestBoard.printBoard();
	}

	public void approximateSolution2(Board board, Board bestBoard, List<Character> letters, String firstWord, List<String> words){
		System.out.println("mi board recibido");
		board.printBoard();
		int firstWordScore = dictionary.wordScore(firstWord);

		List<Move> possibleMoves = Move.getAllMoves(board, words, letters);

		while( !possibleMoves.isEmpty()){

			int randIndex = (int)(Math.random() * words.size());
			Move randMove = possibleMoves.get(randIndex);
			String word = randMove.getWord();

			int secondWordScore = dictionary.wordScore(word);
			double prob = 1/(1+Math.exp((firstWordScore - secondWordScore)/T));
			System.out.println("PROB: " + prob);
			double rand=Math.random();
			System.out.println("RAND: " + rand);
			if(prob > rand){
				//meto la palabra que me da el move
				if(randMove.isTransp()){
					board.putWordTransp(word, 0, randMove.getRow(), randMove.getCol(), letters);
				}else if(!randMove.isTransp()){
					board.putWordNotTransp(word, 0, randMove.getRow(), randMove.getCol(), letters);
				}
				//recalcular moves!
				//etc
				
				
//				System.out.println("quiero ver si puedo meter la palabra " + word);
//
//				for(int i=0; i<word.length(); i++){
//
//					for(int j=0; j<Board.BOARD_SIZE; j++){ //fil
//						for(int k=0; k<Board.BOARD_SIZE; k++){ //col
//
//							int locateLetter = locateLetter(word.charAt(i), j, k);
//							if( locateLetter == -1){ //VERTICAL
//								List<Character> lettersAux = new ArrayList<Character>(letters);
//								if(board.verifyTransp(word, i, j, k, lettersAux)){
//									//la meto verticalmente
//									List<Character >lettersAux2 = new ArrayList<Character>(letters);
//									board.putWordTransp(word, i, j, k, lettersAux2);
//									if(board.getScore() > bestBoard.getScore()){
//										bestBoard.setBoard(board.getBoard());
//										bestBoard.setScore(board.getScore());
//									}
//								}
//							}
//							else if(locateLetter == 1){ //HORIZONTAL
//
//								List<Character> lettersAux = new ArrayList<Character>(letters);
//								System.out.println("letras antes del verify ");
//								System.out.println(lettersAux.toString());
//								if(board.verifyNotTransp(word, i, j, k, lettersAux)){
//									//la meto horizontalmente
//									System.out.println("LA LETRA QUE ENCONTRE PARA METER ES  " + word.charAt(i) + " en la fila " + j + " en la columna " + k);
//									List<Character >lettersAux2 = new ArrayList<Character>(letters);
//									System.out.println("letras que uso para el put "  );
//									System.out.println(lettersAux2.toString());
//									board.putWordNotTransp(word, i, j, k, lettersAux2);
//									if(board.getScore() > bestBoard.getScore()){
//										bestBoard.setBoard(board.getBoard());
//										bestBoard.setScore(board.getScore());
//									}
//								}
//
//							}
//						}
//
//					}
//				}
			}
		}
	}

	//hasta que no me entra otra palabra mas no la voy a considerar maxima solucion
	public void exactSolver(ArrayList<String> words, List<Character> letters, Board board, Board bestBoard){

		if(board.getScore() > bestBoard.getScore()){
			bestBoard.setBoard(board.getBoard());
			bestBoard.setScore(board.getScore());
			bestBoard.printBoard();
		}

		if(letters.size() > 0){
			for(String word: words){
				for(int i=0; i<word.length(); i++){

					for(int j=0; j<Board.BOARD_SIZE; j++){ //row
						for(int k=0; k<Board.BOARD_SIZE; k++){ //col

							int locateLetter = locateLetter(word.charAt(i), j, k);
							if(locateLetter == 1){ //HORIZONTAL
								List<Character> auxLetters = new ArrayList<Character>(letters);
								if(board.verifyNotTransp(word, i, j, k, auxLetters)){
									List<Character> remainingLetters2 = new ArrayList<Character>(letters);
									HashSet<Point> indexes2 = board.putWordNotTransp(word, i, j, k, remainingLetters2);
									board.printBoard();
									exactSolver(words, remainingLetters2, board, bestBoard);
									for(Point index: indexes2){
										board.remove(index.x, index.y);
									}
								}
							}

							if(locateLetter == -1){//VERTICAL
								List<Character> auxLetters2 = new ArrayList<Character>(letters);
								if(board.verifyTransp(word, i, j, k, auxLetters2)){
									List<Character> remainingLetters = new ArrayList<Character>(letters);
									HashSet<Point>indexes =  board.putWordTransp(word, i, j, k, remainingLetters);
									board.printBoard();
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
			if((row == 14 || board.getBoard()[row+1][col] == '.') && (row == 0 || board.getBoard()[row-1][col] == '.'))
				return -1; //VERTICAL
			if((col == 14 || board.getBoard()[row][col+1] == '.') && (col ==0 || board.getBoard()[row][col-1] == '.'))
				return 1; //HORIZONTAL	
		}
		return 0;
	}

	public Dic getDictionary(){
		return this.dictionary;
	}

}
