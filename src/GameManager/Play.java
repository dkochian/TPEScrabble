package GameManager;

import java.io.IOException;

import Board.Board;
import Dictionary.Dic;
import Files.Writer;
import Game.Game;



public class Play {
		
	public static void main(String[] args) {
		long seconds = 1;
		Game game = new Game();
		if(game.getDictionary() != null){
			//Un ciclo para que busque el mejor tablero, y ese mejor tablero haga el write
			Board gameBoard = game.play(seconds);
			try {
				Writer.write("Board", gameBoard);
				System.exit(0);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		

	}

}
