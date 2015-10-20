package Player;

import java.util.ArrayList;
import java.util.List;

import Board.Board;
import Board.Square;
import Dictionary.Dic;
import Game.LetterNode;
import Game.Move;

public class Player {
	private List<Character> piecesOnHand;
	private Board board;
	private int score = 0;
	private List<Move> moves;
	private Dic dictionary;

	public Player(Board board, List<Character> letters, Dic dictionary) {
		this.board = board;
		this.piecesOnHand = letters;
		this.moves = new ArrayList<>();
		this.dictionary = dictionary;
	}
	
	public boolean placedAllPieces() {
		return piecesOnHand.size() == 0;
	}
	
	public Move generateMove(){
	Move newMove = null;
		
//		List<Character> piecesOnHandAux = new ArrayList<>();
//		piecesOnHandAux.addAll(piecesOnHand);
//		List<Node> nodes = new ArrayList<Node>();
//		for(char each : piecesOnHand){
//			nodes.add(new Node(each));
//		}
		
		//for(Node each : nodes){
			
			//StringBuffer word = new StringBuffer();
		
		Move newWord = null;
		if(hola == 0){

			hola += 1;
			String word = "HOLA";

			LetterNode lc = new LetterNode(null, null, 'H');
			LetterNode newLc = new LetterNode(lc, null, 'O');
			lc.setNextLetter(newLc);
			lc = lc.getNextLetter();
			newLc = new LetterNode(lc, null, 'L');
			lc.setNextLetter(newLc);
			lc = lc.getNextLetter();
			newLc = new LetterNode(lc, null, 'A');
			lc.setNextLetter(newLc);
			lc = lc.getNextLetter();

			newWord = new Move(word.toString().toCharArray(), lc, null, false);//generateWord(each, null, word, nodes);
		}else if(hola == 1){
			hola += 1;
			String word = "CASA";
			LetterNode lc = new LetterNode(null, null, 'C');
			LetterNode newLc = new LetterNode(lc, null, 'A');
			lc.setNextLetter(newLc);
			lc = lc.getNextLetter();
			newLc = new LetterNode(lc, null, 'S');
			lc.setNextLetter(newLc);
			lc = lc.getNextLetter();
			newLc = new LetterNode(lc, null, 'A');
			lc.setNextLetter(newLc);
			lc = lc.getNextLetter();

			newWord = new Move(word.toString().toCharArray(), lc, null, false);//generateWord(each, null, word, nodes);
		}else if(hola == 2){
			hola += 1;
			String word = "CHAU";
			LetterNode lc = new LetterNode(null, null, 'C');
			LetterNode newLc = new LetterNode(lc, null, 'H');
			lc.setNextLetter(newLc);
			lc = lc.getNextLetter();
			newLc = new LetterNode(lc, null, 'A');
			lc.setNextLetter(newLc);
			lc = lc.getNextLetter();
			newLc = new LetterNode(lc, null, 'U');
			lc.setNextLetter(newLc);
			lc = lc.getNextLetter();

			newWord = new Move(word.toString().toCharArray(), lc, null, false);//generateWord(each, null, word, nodes);
		}
			
		if(newWord != null){

			newMove = generateMove(newWord);

			if(newMove != null){
				moves.add(newMove);
				return newMove;
			}
		}
		//			for(Node each2 : nodes){
		//				each2.visited = false;
		//			}
		//}
		return null;
	}
	
	
	
	

	private Move generateWord(Node letter, LetterNode lc, StringBuffer word, List<Node> nodes){
		//System.out.println(word);
		Move move = null;

		
		StringBuffer wordAux = new StringBuffer();
		wordAux.append(word);
		wordAux.append(letter.c);
		System.out.println(String.valueOf(wordAux));
		
		if(Dic.isValidWord(wordAux.toString())){
			System.out.println("entra");
			move = new Move(word.toString().toCharArray(), lc, null, false);
			return move;
		}

		if(dictionary.startsWith(wordAux.toString())){
			//System.out.println(word);
			if(lc == null){
				lc = new LetterNode(null, null, letter.c);
			}else{
				LetterNode newLc = new LetterNode(lc, null, letter.c);
				lc.setNextLetter(newLc);
				lc = lc.getNextLetter();
			}
			letter.visited = true;
			word.append(letter.c);
			
			for(Node each : nodes){

				if( ! each.visited){
					//System.out.println(word + "---------------");
					generateWord(each, lc, word, nodes);
				}
//				System.out.println(letter);
				
				
			}
		}
		return move;
	}
	
	private Move generateMove(Move newWord){
		if(board.isCenterFree()){
			int prom;
			if(newWord.getWord().length % 2 == 0){
				prom = ((newWord.getWord().length)/2) - 1;
			}else{
				prom = ((newWord.getWord().length)/2);
			}
			newWord.setTransposed(false);
			return generateMove(newWord,7,7 + prom) ;	 
		}

		Move newHMove = generateHMove(newWord);
		
		if( newHMove != null){
			return newHMove;
		}

		return generateVMove(newWord);
	}
	
	private Move generateMove(Move newWord, int row, int column){
		if(newWord.isTransposed()){
			newWord.setEndSquare(new Square(newWord.getLetterNode().getLetter(), row /*+ prom*/, column));
		}else{
			newWord.setEndSquare(new Square(newWord.getLetterNode().getLetter(), row, column /*+ prom*/));
		}

		return newWord;
	}
	
	private Move generateHMove(Move newWord){
		boolean transposed = false;
		this.board.calculateAllValidPieces(transposed);
		
		for (int row = 0; row < board.BOARD_SIZE; row++) {
			for (int column = 0; column < board.BOARD_SIZE; column++) {
				Square square = board.getSquare(row, column);
				
				if( square.containsLetter() &&  ! square.getNextRight(transposed).containsLetter() && ! square.getNextLeft(transposed).containsLetter()){
					
					Character letter = square.getContent();
					
					for(int i = 0; i < newWord.getWord().length; i++){
						newWord.setTransposed(transposed);
						if(letter.equals(newWord.getWord()[i]) && verify(newWord, i, row, column)){
						
							
							newWord = generateMove(newWord, row, column + (newWord.getWord().length - i - 1));
							return newWord;
						}
					}
				}
			}
		}
		return null;
	}
	
	private boolean verify(Move newWord, int indexLetterFound, int row, int column){
		for(int i = indexLetterFound - 1 ; i >= 0; i--){
			Character letter = newWord.getWord()[i];
			if(newWord.isTransposed()){
				if(letter.equals(board.getSquare( -- row, column).getContent())){
					return false;
				}
			}else{
				if(letter.equals(board.getSquare(row, -- column).getContent())){
					return false;
				}
			}
		}
		
		for(int i = indexLetterFound + 1; i < newWord.getWord().length; i++){
			Character letter = newWord.getWord()[i];
			if(newWord.isTransposed()){
				if(letter.equals(board.getSquare( ++ row, column).getContent())){
					return false;
				}
			}else{
				if(letter.equals(board.getSquare(row, ++ column).getContent())){
					return false;
				}
			}
		}
		
		return true;
	}
	
	private Move generateVMove(Move newWord){
		boolean transposed = true;
		this.board.calculateAllValidPieces(transposed);

		for (int row = 0; row < board.BOARD_SIZE; row++) {
			for (int column = 0; column < board.BOARD_SIZE; column++) {
				Square square = board.getSquare(row, column);
				
				if( square.containsLetter() &&  ! square.getNextRight(transposed).containsLetter() && !square.getNextLeft(transposed).containsLetter()){
					Character letter = square.getContent();
					for(int i = 0; i < newWord.getWord().length; i++){
						newWord.setTransposed(transposed);
						if(letter.equals(newWord.getWord()[i]) && verify(newWord, i, row, column)){
							
							newWord = generateMove(newWord, row + (newWord.getWord().length - i - 1), column);
							return newWord;
						}
					}
				}
			}
		}
		return null;
	}
	
	
	

	public boolean removePieceFromHand(char letter) {
		int charIndex = findLetterIndexInTilesOnHand(letter);

		if (charIndex < 0)
			return false;

		this.piecesOnHand.remove(charIndex);
		return true;
	}
	
	private int findLetterIndexInTilesOnHand(char letter) {
		int size = piecesOnHand.size();
		for (int i = 0; i < size; i++) {
			if (letter == piecesOnHand.get(i)) {
				return i;
			}
		}
		return -1;
	}
	
	public void addScore(int score){
		this.score += score;
	}
	
	public int getScore(){
		return this.score;
	}
	
	private static class Node{
		private char c;
		private boolean visited;
		
		public Node(char c) {
			this.c = c;
			visited = false;
		}
		
		public String toString(){
			return String.valueOf(c);
		}
	}
}
