package files;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dictionary.Dic;



public class Reader {
	
	/**
	 * Reads dictionary from text file
	 * @param dictionary where the valid words will be added
	 */
	public static void readDictionary(Dic dictionary){
		BufferedReader br = null;
		try {			
			String sCurrentLine;

			br = new BufferedReader(new FileReader("/Users/natinavas/Documents/ITBA/EDA/TPEScrabble/bin/dictionary.txt"));

			while ((sCurrentLine = br.readLine()) != null) {
				if(sCurrentLine.length() >= 2 && sCurrentLine.length() <= 7){
					dictionary.addValidWord(sCurrentLine.toUpperCase());
					System.out.println(sCurrentLine.toUpperCase());
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	
	/**
	 * Reads letter from text file
	 * @return a List containing the letters which
	 * will be used in the game
	 */
	public static List<Character> readLetters(){
		List<Character> letters = new ArrayList<Character>();
		BufferedReader br = null;
		try {

			int sCurrentLetter;

			br = new BufferedReader(new FileReader("/Users/natinavas/Documents/ITBA/EDA/TPEScrabble/bin/letters.txt"));

			while ((sCurrentLetter = br.read()) != -1) {
				
				if(sCurrentLetter >= 97 && sCurrentLetter <= 122){
					sCurrentLetter -= 32;
				}else if(sCurrentLetter < 65 || sCurrentLetter > 90){
					continue;
				}
				letters.add((char) sCurrentLetter);
				//System.out.println((char) sCurrentLetter);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return letters;
	}
	
}