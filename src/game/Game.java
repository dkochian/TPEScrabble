package game;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import board.Board;
import dictionary.Dic;
import files.Reader;
import gui.Scrabble;

public class Game {
	
	private Board board;
	private List<Character> letters;
	private Dic dictionary;
	private final static int T = 7;
	private final static int HORIZONTAL_WORD = 1;
	private final static int VERTICAL_WORD = -1;
	private String lettersFileName;
	private int maxScore = 0;
	private Scrabble gui;
	
	public Game(String lettersFileName, String dictionaryFileName, boolean visual) {
		
		this.lettersFileName = lettersFileName;
		this.dictionary = new Dic(dictionaryFileName);
		this.board = new Board();
		this.letters = Reader.readLetters(lettersFileName, maxScore);
		if(visual){
			this.gui = new Scrabble();
		}
		
	}


	public Board firstWordExact(){
		Board bestBoard = new Board();
		ArrayList<String> possibleWords = dictionary.getPossibleWords(letters);
		int numberBoards = 0;

		for(String word: possibleWords){

			char[] charWord = word.toCharArray();

			for(int i = 0; i < word.length(); i++){

				Character letter = word.charAt(i);
				board.placePiece(letter, 7, 7);
				int index = letters.indexOf(charWord[i]);
				letters.remove(index);

				for(int j = i + 1; j < word.length(); j++){
					board.placePiece(charWord[j], 7, 7+j-i);
					index = letters.indexOf(charWord[j]);
					letters.remove(index);
				}
				
				for(int j = i - 1; j >= 0 ; j--){
					board.placePiece(charWord[j], 7, 7+j-i);
					index = letters.indexOf(charWord[j]);
					letters.remove(index);
				}
				
				Board bestBoardRec = new Board();
				
				exactSolver(possibleWords, letters, board, bestBoardRec);
				
				numberBoards += 1;
				System.out.println("Tablero Número : " + numberBoards);
				bestBoardRec.printBoard();
				
				if(gui != null){
					gui.updateBoard(bestBoardRec);
					gui.updateScores(bestBoardRec.getScore());
				}				

				if(bestBoardRec.getScore() > bestBoard.getScore()){
					bestBoard.setBoard(bestBoardRec.getBoard());
					bestBoard.setScore(bestBoardRec.getScore());
				}
				
				if(bestBoard.getScore() == maxScore){
					continue;
				}

				board.initBoard();
				this.letters = Reader.readLetters(lettersFileName);
			}
		}
		
		if(gui != null){
			gui.updateBoard(bestBoard);
			gui.updateScores(bestBoard.getScore());
		}
		
		System.out.println("Mejor Tablero");
		bestBoard.printBoard();
		System.out.println("Puntaje: " + bestBoard.getScore());
		return bestBoard;
	}

	//hasta que no me entra otra palabra mas no la voy a considerar maxima solucion
	public void exactSolver(ArrayList<String> words, List<Character> letters, Board board, Board bestBoard){
		
		if(board.getScore() > bestBoard.getScore()){
			bestBoard.setBoard(board.getBoard());
			bestBoard.setScore(board.getScore());
		}
		
		if(board.getScore() == maxScore){
			return;
		}

		if(letters.size() > 0){
			for(String word: words){
				for(int i = 0; i < word.length(); i++){

					for(int j = 0; j < Board.BOARD_SIZE; j++){ //row
						for(int k = 0; k < Board.BOARD_SIZE; k++){ //col
							
							int locateLetter = locateLetter(word.charAt(i), j, k);
							if(locateLetter == HORIZONTAL_WORD){ 
								
								List<Character> auxLetters = new ArrayList<Character>(letters);
								if( board.verifyNotTransp(word, i, j, k, auxLetters) ){
									List<Character> remainingLetters2 = new ArrayList<Character>(letters);
									HashSet<Point> indexes2 = board.putWordNotTransp(word, i, j, k, remainingLetters2);
									exactSolver(words, remainingLetters2, board, bestBoard);
									for(Point index: indexes2){
										board.remove(index.x, index.y);
									}
								}
							}

							if(locateLetter == VERTICAL_WORD){
								List<Character> auxLetters2 = new ArrayList<Character>(letters);
								if(board.verifyTransp(word, i, j, k, auxLetters2)){
									
									List<Character> remainingLetters = new ArrayList<Character>(letters);
									HashSet<Point>indexes =  board.putWordTransp(word, i, j, k, remainingLetters);
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
		}
	}
	
	
	public Board approximateSolution(long endTime){
		Board bestBoard = new Board();
		long startTime;
		ArrayList<String> possibleWords = dictionary.getPossibleWords(letters);
		int numberBoards = 0;

		while(( startTime = System.currentTimeMillis()) < endTime){

			board.initBoard();
			letters = Reader.readLetters(lettersFileName);
			Board bestBoardAux = new Board();

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
			for(int i = indexOfWord - 1; i >= 0; i--){
				board.placePiece(randWord.charAt(i), 7, 7-indexOfWord+i);
				index = letters.indexOf(randWord.charAt(i));
				letters.remove(index);
			}

			approximateSolution2(board, bestBoardAux, letters, randWord, possibleWords, endTime);
			
			numberBoards += 1;
			System.out.println("Tablero Número : " + numberBoards);
			board.printBoard();
			
			if(gui != null){
				gui.updateBoard(board);
				gui.updateScores(board.getScore());
			}
			
			if(bestBoardAux.getScore() > bestBoard.getScore()){
				bestBoard.setBoard(bestBoardAux.getBoard());
				bestBoard.setScore(bestBoardAux.getScore());
			}

		}
		
		if(board.getScore() > bestBoard.getScore()){//    PARA QUE SIRVE ESTE IF NATI?????????????????
			bestBoard.setScore(board.getScore());
			bestBoard.setBoard(board.getBoard());
		}
		
		if(gui != null){
			gui.updateBoard(bestBoard);
			gui.updateScores(bestBoard.getScore());
		}	
		
		System.out.println("Mejor solucion final: ");
		bestBoard.printBoard();
		System.out.println("Puntaje Final: " + bestBoard.getScore());
		
		return bestBoard;	
	}

	public void approximateSolution2(Board board, Board bestBoard, List<Character> letters, String firstWord, List<String> words, long endTime){

		int firstWordScore = dictionary.wordScore(firstWord);

		List<Move> possibleMoves = Move.getAllMoves(board, words, letters);

		long time;
		while( endTime > (time = System.currentTimeMillis() ) && !possibleMoves.isEmpty()){

			int randIndex = (int)(Math.random() * possibleMoves.size());
			Move randMove = possibleMoves.get(randIndex);
			String word = randMove.getWord();

			int secondWordScore = dictionary.wordScore(word);
			double prob = 1/(1+Math.exp((firstWordScore - secondWordScore)/T));
			
			//System.out.println("PROB: " + prob);
			double rand = Math.random();
			//System.out.println("RAND: " + rand);
			
			if(prob > rand){

				if(randMove.isTransp()){
					if(! board.containsLetter(randMove.getRow(), randMove.getCol())){
						board.placePiece(word.charAt(0), randMove.getRow(), randMove.getCol());
						int index = letters.indexOf(word.charAt(0));
						letters.remove(index);
					}
					
					board.putWordTransp(word, 0, randMove.getRow(), randMove.getCol(), letters);	

				}else{
					if(! board.containsLetter(randMove.getRow(), randMove.getCol())){
						board.placePiece(word.charAt(0), randMove.getRow(), randMove.getCol());
						int index = letters.indexOf(word.charAt(0));
						letters.remove(index);
					}

					board.putWordNotTransp(word, 0, randMove.getRow(), randMove.getCol(), letters);

					
				}
			}
			possibleMoves = Move.getAllMoves(board, words, letters);
		}
		if(bestBoard.getScore() < board.getScore()){
			bestBoard.setBoard(board.getBoard());
			bestBoard.setScore(board.getScore());
		}
	}

	private Integer locateLetter(char letter, int row, int col){
		if(board.getLetter(row, col) == letter){
			if((row == 14 || ! board.containsLetter(row + 1, col)) && (row == 0 || ! board.containsLetter(row - 1, col)))
				return VERTICAL_WORD; 
			if((col == 14 || ! board.containsLetter(row, col + 1)) && (col ==0 || ! board.containsLetter(row , col - 1)))
				return HORIZONTAL_WORD; 
		}
		return 0;
	}
}
