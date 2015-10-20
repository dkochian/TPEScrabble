package Files;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import Board.Board;
import Board.Square;

public class Writer {
	
	/**
	 * Writes the game in a text file
	 * @param filename of the resulting file
	 * @param board that will be written in the file
	 * @throws IOException
	 */
	public static void write (String filename, Board board) throws IOException{

		try {

			File file = new File(filename + ".txt");

			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			Square[][] printBoard = board.getBoard();
			
			bw.write("Scrabble..........");
			bw.newLine();
			bw.newLine();
			
			for (int i = 0; i < board.BOARD_SIZE; i++) {
				for(int j = 0; j < board.BOARD_SIZE; j++){
					bw.write(printBoard[i][j].getContent() + " " );
				}
				bw.newLine();
			}
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
