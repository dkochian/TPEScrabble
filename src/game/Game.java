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
	private final static int T = 7;// This variable sets for what number will be divided in the Stochastic Hill Climbing probability
	private final static int HORIZONTAL_WORD = 1;
	private final static int VERTICAL_WORD = -1;
	private String lettersFileName;
	private int maxScore = 0;
	private Scrabble gui;
	private boolean placedAllLetters;
	
	public Game(String lettersFileName, String dictionaryFileName, boolean visual) {
		
		this.lettersFileName = lettersFileName;
		this.dictionary = new Dic(dictionaryFileName);
		this.board = new Board();
		this.letters = Reader.readLetters(lettersFileName);
		if(visual){
			this.gui = new Scrabble();
		}
		for(Character each : letters){
			maxScore += Dic.getLetterValue(each);
		}
		
		System.out.println(maxScore);
	}
	
	/**
	 * Checks all the possible boards by starting with all the possible words. In addition selects which board is the best
	 * @return the best board
	 */

	public Board exactSolution(){
		long startTime = System.currentTimeMillis();
		Board bestBoard = new Board();
		List<String> possibleWords = dictionary.getPossibleWords(letters);
		int numberBoards = 0;



		for(int k=0; k<possibleWords.size() && !placedAllLetters; k++){
			String word = possibleWords.get(k);
		//for(String word: possibleWords){
			
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
				System.out.println("Board Number : " + numberBoards);
				bestBoardRec.printBoard();
				System.out.println("Score of the board number " + numberBoards + " : " + bestBoardRec.getScore() + " points.");
				System.out.println();
				
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
		
		System.out.println("Best Board");
		bestBoard.printBoard();
		System.out.println("Score: " + bestBoard.getScore());
		long currTime = System.currentTimeMillis();
		System.out.println("Time: " + (currTime-startTime));
		return bestBoard;
	}

	/**
	 * Checks all the possible words that can fit and will be included on the board which maximizes the score with the first word already placed
	 * @param words
	 * @param letters
	 * @param board
	 * @param bestBoard
	 */
	public void exactSolver(List<String> words, List<Character> letters, Board board, Board bestBoard){
		
		if(board.getScore() > bestBoard.getScore()){
			bestBoard.setBoard(board.getBoard());
			bestBoard.setScore(board.getScore());
		}
		
		if(board.getScore() >= maxScore){
			return;
		}
		
		if(letters.size() == 0)
			placedAllLetters = true;

		if(!placedAllLetters){
			for(String word: words){
				for(int i = 0; i < word.length(); i++){

					for(int j = 0; j < Board.BOARD_SIZE; j++){ //row
						for(int k = 0; k < Board.BOARD_SIZE; k++){ //col
							
							int locateLetter = Board.locateLetter(word.charAt(i), j, k, board);
							if(locateLetter == HORIZONTAL_WORD){ 
								
								List<Character> auxLetters = new ArrayList<Character>(letters);
								if( board.verifyNotTransp(word, i, j, k, auxLetters) ){
									List<Character> remainingLetters2 = new ArrayList<Character>(letters);
									HashSet<Point> indexes2 = board.putWordNotTransp(word, i, j, k, remainingLetters2);
									exactSolver(words, remainingLetters2, board, bestBoard);
									board.printBoard();
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
									board.printBoard();
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
	
	/**
	 * Initialize the Stochastic Hill Climbing algorithm, setting the first random word
	 * @param endTime 
	 * @return the best board
	 */	
	public Board approximateSolution(long endTime){
		Board bestBoard = new Board();
		long startTime;
		List<String> possibleWords = dictionary.getPossibleWords(letters);
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

			approximateSolutionRec(board, bestBoardAux, letters, randWord, possibleWords, endTime);
			
			numberBoards += 1;
			System.out.println("Board number: " + numberBoards);
			board.printBoard();
			System.out.println("Score of the board number" + numberBoards + " : " + board.getScore() + " points.");
			System.out.println();
			
			if(gui != null){
				gui.updateBoard(board);
				gui.updateScores(board.getScore());
			}
			
			if(bestBoardAux.getScore() > bestBoard.getScore()){
				bestBoard.setBoard(bestBoardAux.getBoard());
				bestBoard.setScore(bestBoardAux.getScore());
			}

		}
		
		//checks if the board that couldnt finish running is actually better than 
		//the actual best board
		if(board.getScore() > bestBoard.getScore()){
			bestBoard.setScore(board.getScore());
			bestBoard.setBoard(board.getBoard());
		}
		
		if(gui != null){
			gui.updateBoard(bestBoard);
			gui.updateScores(bestBoard.getScore());
		}	
		
		System.out.println("Best final solution: ");
		bestBoard.printBoard();
		System.out.println("Final Score: " + bestBoard.getScore());
		
		return bestBoard;	
	}

	/**
	 * Checks by a probability which word will be add on the board while it has time
	 * @param board
	 * @param bestBoard
	 * @param letters
	 * @param firstWord
	 * @param words
	 * @param endTime
	 */
	public void approximateSolutionRec(Board board, Board bestBoard, List<Character> letters, String firstWord, List<String> words, long endTime){

		int firstWordScore = dictionary.wordScore(firstWord);

		List<Move> possibleMoves = Move.getAllMoves(board, words, letters);

		long time;
		while( endTime > (time = System.currentTimeMillis() ) && !possibleMoves.isEmpty()){

			int randIndex = (int)(Math.random() * possibleMoves.size());
			Move randMove = possibleMoves.get(randIndex);
			String word = randMove.getWord();

			int secondWordScore = dictionary.wordScore(word);
			double prob = 1/(1+Math.exp((firstWordScore - secondWordScore)/T));
			double rand = Math.random();
			
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

}
