package dictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import files.Reader;

public class Dic{
	
	private static Trie dictionary;
	static Integer[] valuess = {1,3,3,2,1,4,2,4,1,8,5,1,3,1,1,3,10,1,1,1,1,4,4,8,4,10};
    public final static AlphaStructure values= new AlphaStructure(valuess);
	
	public Dic(String dictionaryFileName){
		dictionary = new Trie();
		Reader.readDictionary(this, dictionaryFileName);
	}

	
	public static Integer getLetterValue(Character letter){
		return values.get(letter);
	}
	
	
	/**
	 * @param word to be added to the dictionary
	 */
	public void addValidWord(String word){
		dictionary.insert(word.toUpperCase());
	}
	
	
	/**
	 * @param word whose score will be checked
	 * @return -1 if the word is not valid, and the score of the word if it is
	 */
	public int checkWord(String word){
		if(isValidWord(word)){
			return wordScore(word);
		}
		return -1;
	}

	/**
	 * @param word whose validity will be checked
	 * @return true if the word is a valid one, false if not
	 */
	public static boolean isValidWord(String word){
		return dictionary.search(word.toUpperCase());
	}
	
	/**
	 * @param prefix that will be checked if is contained in a valid word
	 * @return true if it is contained, false if not
	 */
	public boolean startsWith(String prefix){
		return dictionary.startsWith(prefix.toUpperCase());
	}
	
	/**
	 * @param word whose score will be calculated
	 * @return the score of the word
	 */
	public int wordScore(String word) {
        int sum = 0;
        word = word.toUpperCase();
        for(int i = 0; i < word.length();i++) {
        		sum+= values.get(word.charAt(i));
        }
        return sum;
    }
	
	public ArrayList<String> getPossibleWords(List<Character> availablePieces){
		ArrayList<String> awns = dictionary.getPossibleWords(availablePieces);
		return awns;
	}
	
	public HashMap<String, Integer> getWordValues(List<Character> availablePieces){
		HashMap<String, Integer> wordValues = new HashMap<String, Integer>();
		ArrayList<String> possibleWord = getPossibleWords(availablePieces);
		for(String word: possibleWord){
			Integer value = wordScore(word);
			wordValues.put(word, value);	
		}
		return wordValues;
	}
	

}
