package gameManager;

import java.io.IOException;

import board.Board;
import dictionary.Dic;
import files.Writer;
import game.Game;



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
