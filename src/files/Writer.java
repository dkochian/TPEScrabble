package files;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import board.Board;

public class Writer {
	
	/**
	 * Writes the game in a text file
	 * @param filename of the resulting file
	 * @param board that will be written in the file
	 * @throws IOException
	 */
	public static void write (String filename, Board board) throws IOException{

		try {

			File file = new File("./" + filename);

			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			char[][] printBoard = board.getBoard();
			
			bw.write("El mejor tablero que se generó es el siguiente :");
			bw.newLine();
			bw.newLine();
			
			for (int i = - 1; i < Board.BOARD_SIZE; i++) {
				if(i >= 0){
					bw.write(String.valueOf(i +1));
					bw.write("\t");
				}
				for(int j = 0; j < Board.BOARD_SIZE; j++){
					if(i < 0){
						bw.write("\t");
						bw.write(String.valueOf(j +1));
					}else{
						bw.write(printBoard[i][j] + " " );
						bw.write("\t");
					}
				}
				bw.newLine();
			}
			bw.newLine();
			bw.write("El puntaje es de " + String.valueOf(board.getScore()) +" puntos.");
			
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
