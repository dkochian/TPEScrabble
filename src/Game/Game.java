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
	private Scrabble gui;

	public Game() {
		this.dictionary = new Dic();
		this.board = new Board();
		this.letters = Reader.readLetters();
		this.computer = new Player(board, letters, dictionary);
		this.gui = new Scrabble(this.board);
	}
	
	public Board play(){
		boolean gameOver = false;
		int score = 0;
		while (! gameOver) {
			boolean successfulMove = playTurn();
			if(! successfulMove || computer.placedAllPieces()){
				gameOver = true;
				continue;
			}			
			gui.updateBoard();

			board.printBoard();
			
			System.out.println();			
			System.out.println("----------------------------------");
			System.out.println();
		}
		score = computer.getScore();
		gui.updateScores(score);
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
				board.placePiece(letter, sq, isTransposed);

				computer.removePieceFromHand(letter);
				wn = wn.getPreviousLetter();
				
			} else {
				wn = wn.getPreviousLetter();
			}

			sq = sq.getNextLeft(isTransposed);

		}
	}
}
