package GameManager;

import java.io.IOException;

import Board.Board;
import Dictionary.Dic;
import Files.Writer;
import Game.Game;



public class Play {
		
	public static void main(String[] args) {
//		Dic test = new Dic();
//		System.out.println(test.checkWord("ho"));
//		System.out.println(test.startsWith("ho"));
		Game game = new Game();
		if(game.getDictionary() != null){
			//Un ciclo para que busque el mejor tablero, y ese mejor tablero haga el write
			Board gameBoard = game.play();
			try {
				Writer.write("", gameBoard);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		

	}

}
