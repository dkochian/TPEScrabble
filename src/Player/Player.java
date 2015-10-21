package player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import board.Board;
import board.Square;
import dictionary.Dic;
import game.LetterNode;
import game.Move;

public class Player {
	private List<Character> piecesOnHand;
	private List<Character> piecesOnBoard;
	private Board board;
	private int score = 0;
	private List<Move> moves;
	private Dic dictionary;
	private int hola = 0;	

	public Player(Board board, List<Character> letters, Dic dictionary) {
		this.board = board;
		this.piecesOnHand = letters;
		this.moves = new ArrayList<>();
		this.piecesOnBoard = new ArrayList<Character>();
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
	
	
	
	

//	private Move generateWord(Node letter, LetterNode lc, StringBuffer word, List<Node> nodes){//Este metodo no va
//		//System.out.println(word);
//		Move move = null;
//
//		
//		StringBuffer wordAux = new StringBuffer();
//		wordAux.append(word);
//		wordAux.append(letter.c);
//		System.out.println(String.valueOf(wordAux));
//		
//		if(Dic.isValidWord(wordAux.toString())){
//			System.out.println("entra");
//			move = new Move(word.toString().toCharArray(), lc, null, false);
//			return move;
//		}
//
//		if(dictionary.startsWith(wordAux.toString())){
//			//System.out.println(word);
//			if(lc == null){
//				lc = new LetterNode(null, null, letter.c);
//			}else{
//				LetterNode newLc = new LetterNode(lc, null, letter.c);
//				lc.setNextLetter(newLc);
//				lc = lc.getNextLetter();
//			}
//			letter.visited = true;
//			word.append(letter.c);
//			
//			for(Node each : nodes){
//
//				if( ! each.visited){
//					//System.out.println(word + "---------------");
//					generateWord(each, lc, word, nodes);
//				}
////				System.out.println(letter);
//				
//				
//			}
//		}
//		return move;
//	}
	
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
			newWord.setEndSquare(new Square(newWord.getLetterNode().getLetter(), row , column));
		}else{
			newWord.setEndSquare(new Square(newWord.getLetterNode().getLetter(), row, column ));
		}

		return newWord;
	}
	
	private Move generateHMove(Move newWord){
		boolean transposed = false;
		
		for (int row = 0; row < Board.BOARD_SIZE; row++) {
			for (int column = 0; column < Board.BOARD_SIZE; column++) {
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
		int rowAux = row;
		int columnAux = column;
		
		for(int i = indexLetterFound - 1 ; i >= 0; i--){
			Square sq = null;
			if(newWord.isTransposed()){
				sq = board.getSquare( -- rowAux, columnAux);
			}else{
				sq = board.getSquare( rowAux, -- columnAux);
			}
				
			if(i == 0 && ! sq.getNextLeft( newWord.isTransposed()).isEmpty()){
				return checkWord(sq, newWord.isTransposed());
			}
			if( ! sq.getNextLeft( ! newWord.isTransposed() ).isEmpty() || ! sq.getNextRight( ! newWord.isTransposed() ).isEmpty()){//Si es horizontal tiene que buscar arriba y abajo, si es vertical tiene q buscar izquierda y derecha
				return checkWord(sq, ! newWord.isTransposed());
			}

		}
		int rowAux2 = row;
		int columnAux2 = column;
		
		for(int i = indexLetterFound + 1; i < newWord.getWord().length; i++){
			Square sq = null;
			if(newWord.isTransposed()){
				sq = board.getSquare( ++ rowAux2, columnAux);
			}else{
				sq = board.getSquare( rowAux, ++ columnAux2);
			}
				
			if(i == newWord.getWord().length && ! sq.getNextLeft( newWord.isTransposed()).isEmpty()){
				return checkWord(sq, newWord.isTransposed());
			}
			if( ! sq.getNextLeft( ! newWord.isTransposed() ).isEmpty() || ! sq.getNextRight( ! newWord.isTransposed() ).isEmpty()){//Si es horizontal tiene que buscar arriba y abajo, si es vertical tiene q buscar izquierda y derecha
				return checkWord(sq, ! newWord.isTransposed());
			}
		}
		
		return true;
	}
	
	private boolean checkWord(Square sq, boolean transposed){//Aprovecho el while que estoy recorriendo para arriba de todo para appendear sino deberia hacer otro while despues para bajar todo de vuelta
		Square sqAux = sq;
		StringBuffer word = new StringBuffer();
		while( ! sqAux.isEmpty()){
			word.append(sqAux.getContent());
			sqAux = sqAux.getNextLeft(transposed);
		}
		word = word.reverse();
		while( ! sq.isEmpty()){
			word.append(sqAux.getContent());
			sq = sq.getNextLeft(transposed);
		}
		
		return Dic.isValidWord(word.toString());
	}
	
	private Move generateVMove(Move newWord){
		boolean transposed = true;

		for (int row = 0; row < Board.BOARD_SIZE; row++) {
			for (int column = 0; column < Board.BOARD_SIZE; column++) {
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
		this.piecesOnBoard.add(letter);
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
	
	public void addScore(){
		score = 0;
		for(Character each : piecesOnBoard){
			score+= Dic.values.get(each);
			//score += Dic.values[Dic.alphabet.indexOf(each)];
		}
	}
	
	/**
	 * @return hashset containing the valid words contained in the dictionary which
	 * can be formed with the letters the player has
	 */
	public HashSet<String> possibleWords(){
		return dictionary.possibleWords(piecesOnHand);
	}
	
	public int getScore(){
		addScore();
		return this.score;
	}
	
	private static class Node{
		private char c;
		private boolean visited;
		
		public Node(char c){
			this.c = c;
			visited = false;
		}
		
		public String toString(){
			return String.valueOf(c);
		}
	}
}
