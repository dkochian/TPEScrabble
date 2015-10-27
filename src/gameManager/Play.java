package gameManager;

import java.io.IOException;

import board.Board;
import files.Writer;
import game.Game;

public class Play {

	public static void main(String[] args) {
		String lettersFileName = null;
		String dictionaryFileName = null;
		String outPutFileName = null;
		boolean visual = false;
		long seconds = 0;

		try{
			dictionaryFileName = args[0];
			lettersFileName = args[1];
			outPutFileName = args[2];

		}catch(ArrayIndexOutOfBoundsException e){
			System.err.println("You did not introduce the necessary number of arguments in the command line");
			System.exit(0);
		}

		if(args.length == 6){
			if(! args[3].equals("-visual") && ! args[4].equals("-maxtime")){
				throw new IllegalArgumentException("You did not introduce the correct arguments in command line");
			}else{
				visual = true;
				try{
					seconds = (Long.valueOf(args[5]) * 1000) + System.currentTimeMillis();
				}catch(ClassCastException e){
					System.err.println("The seconds argument is not a correct number");
					System.exit(0);
				}
			}
			
		}else if(args.length == 4){
			if(! args[3].equals("-visual")){
				throw new IllegalArgumentException("You did not introduce the correct arguments in command line");	
			}else{
				visual = true;
			}
		}else if(args.length == 5){
			if(! args[3].equals("-maxtime")){
				throw new IllegalArgumentException("You did not introduce the correct arguments in command line");
			}
			try{
				seconds = (Long.valueOf(args[4]) * 1000) + System.currentTimeMillis();
			}catch(ClassCastException e){
				System.err.println("The seconds argument is not a correct number");
				System.exit(0);
			}
		}

		Game game = new Game(lettersFileName, dictionaryFileName, visual);
		Board bestBoard = null;
		
		if(seconds > 0){
			bestBoard = game.approximateSolution(seconds);
		}else{
			bestBoard = game.firstWordExact();
		}
		
		try {
			Writer.write(outPutFileName, bestBoard);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
