package gameManager;

import java.io.IOException;

import dictionary.Dic;
import files.Writer;
import game.Game;
import board.Board;



public class Play {

	public static void main(String[] args) {

		String lettersFileName;
		String dictionaryFileName;
		String outPutFileName;
		boolean visual = false;
		long seconds;

		try{
			lettersFileName = args[0];
			dictionaryFileName = args[1];
			outPutFileName = args[2];

		}catch(ArrayIndexOutOfBoundsException e){
			System.err.println("You did not introduce the necessary number of arguments in the command line");
		}

		if(args.length == 6){
			if(! args[3].equals("-visual") && ! args[4].equals("-maxtime")){
				throw new IllegalArgumentException("You did not introduce the correct arguments in command line");
			}
			try{
				seconds = Long.valueOf(args[5]);
			}catch(ClassCastException e){
				System.err.println("The seconds argument is not a correct number");
			}
		}else if(args.length == 4){
			if(! args[3].equals("-visual")){
				throw new IllegalArgumentException("You did not introduce the correct arguments in command line");
			}	
		}else if(args.length == 5){
			if(! args[3].equals("-maxtime")){
				throw new IllegalArgumentException("You did not introduce the correct arguments in command line");
			}
			try{
				seconds = Long.valueOf(args[4]);
			}catch(ClassCastException e){
				System.err.println("The seconds argument is not a correct number");
			}
		}

		Game game = new Game();
		game.firstWordExact();
		//game.approximateSolution(System.currentTimeMillis() + 6000);

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
