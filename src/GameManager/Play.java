package gameManager;

import java.io.IOException;

import dictionary.Dic;
import files.Writer;
import game.Game;
import board.Board;



public class Play {
		
	public static void main(String[] args) {
//		Dic test = new Dic();
//		System.out.println(test.checkWord("ho"));
//		System.out.println(test.startsWith("ho"));
		Game game = new Game();
		game.firstWord();
//		if(game.getDictionary() != null){
//			//Un ciclo para que busque el mejor tablero, y ese mejor tablero haga el write
//			//Board gameBoard = game.play();
//			try {
//			//	Writer.write("Board", gameBoard);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
		

	}

}
