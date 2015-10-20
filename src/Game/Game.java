package Game;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import Board.Board;
import Board.Square;
import Dictionary.Dic;
import Files.Reader;
import Player.Player;

public class Game {
	private Board board;
	private List<Character> letters;
	private Player computer;
	private Dic dictionary;
	private static int boardSize = 225;
	

	public Game() {
		this.dictionary = new Dic();
		this.board = new Board();
		this.letters = Reader.readLetters();
		this.computer = new Player(board, letters, dictionary);
		
	}
	
	

	
	
	public Board play(){
		boolean gameOver = false;
		
		while (! gameOver) {
			boolean successfulMove = playTurn(); //encuentra palabra y la puede poner en el tablero
			if(! successfulMove || computer.placedAllPieces()){
				gameOver = true;
				continue;
			}
			board.printBoard();
			System.out.println();			

			System.out.println("----------------------------------");
			System.out.println();
		}
		return this.board;
	}

	
	//busco una cota para usar con backtracking, no valida que se formen palabras con sentido, eso habria que hacerlo para 
	//lograr una mejor cota ma;ana me fijo de como hacerlo pero es jodido hay que pensarlo bien.
	/**
	 * @return highest possible score. With all the possible letters, it returns the score that would result from
	 * the player placing from the highest pieces to the lower ones filling the whole board or using all pieces.
	 */
	private int highestScore(){
		int score = 0;
		
		//key es el puntaje y value es la cantidad de letras con ese key
		HashMap<Integer, Integer>  letterValuesQuant = new HashMap<Integer, Integer>();
		
		int possValues = {10, 8, 5, 4, 3, 2, 1};
		
		//aca asigno la cantidad de piezas de cada puntaje en el mapa, x con 10, x con 8, etc.
		for(Character letter: letters){
			int value = dictionary.getLetterValue(letter);
			int currQuant = lettersValuesQuant.get(value);
			if(currQuant == null){
				letterValuesQuant.put(value, 1);
			}else{
				letterValuesQuant.put(value,currQuant + 1);
			}
		}
		
		int quantPieces = 0;
		boolean noMorePieces = false;
		
		//aca voy sumando de mayor puntaje de pieza a menor, paro cuando o haya agregado todas las piezas o haya llegado al maximo de
		//piezas posibles
			for(int valIndex=0; valIndex < 7 && quantPieces<boardSize; valIndex++){
				int quant = letterValuesQuant.get(possValues[valIndex]);
				if(quantPieces + quant < boardSize){
					quantPieces += quant;
				}else{
					quant = boardSize - quantPieces;
					
				}
				score+= (possValues[valIndex]*quant);
			}
		
		
		
		return score;
		
		 
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

	public int makeMove(Move move) {

		LetterNode wn = move.getLetterNode();

		int score = 0;
		Square sq = board.getSquare(move.getEndSquare().getRow(), move.getEndSquare().getColumn());
		
			
		while (wn.getPreviousLetter() != null && sq.getContent() != Square.OUT_BOUNDS) {

			char letter = wn.getLetter();
			boolean isTransposed = move.isTransposed();
			
			if (sq.containsLetter()) { //
				board.placePiece(letter, sq, isTransposed);

				computer.removePieceFromHand(letter);
				wn = wn.getPreviousLetter();
				
			} else {
				wn = wn.getPreviousLetter();
			}
			
			sq = sq.getNextLeft(isTransposed);
			board.printBoard();
		}
		return score;
	}
}
