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
	 * @param dictionaryFileName
	 */
	public static void readDictionary(Dic dictionary, String dictionaryFileName){
		BufferedReader br = null;
		try {			
			String sCurrentLine;

			br = new BufferedReader(new FileReader("./" + dictionaryFileName));

			while ((sCurrentLine = br.readLine()) != null) {
				if(sCurrentLine.length() >= 2 && sCurrentLine.length() <= 7){
					dictionary.addValidWord(sCurrentLine.toUpperCase());
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
	 * @param lettersFileName
	 * @return a List containing the letters which will be used in the game
	 */
	public static List<Character> readLetters(String lettersFileName){
		List<Character> letters = new ArrayList<Character>();
		BufferedReader br = null;
		try {
			
			int sCurrentLetter;

			br = new BufferedReader(new FileReader("./" + lettersFileName));

			while ((sCurrentLetter = br.read()) != -1) {
				
				if(letters.size() == 80){
					continue;
				}
				
				if(sCurrentLetter >= 97 && sCurrentLetter <= 122){
					sCurrentLetter -= 32;
				}else if(sCurrentLetter < 65 || sCurrentLetter > 90){
					continue;
				}
				letters.add((char) sCurrentLetter);
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