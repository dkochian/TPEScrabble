package Dictionary;

import Files.Reader;

public class Dic {
	
	private static Trie dictionary;
	public final static String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final static int[] values = {1,3,3,2,1,4,2,4,1,8,5,1,3,1,1,3,10,1,1,1,1,4,4,8,4,10};
	
	public Dic(){
		dictionary = new Trie();
		Reader.readDictionary(this);
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
	private int wordScore(String word) {
	
        int sum = 0;
        
        for(int i = 0; i < word.length();i++) {
        	 sum += values[alphabet.indexOf(word.charAt(i))];
        }
        
        return sum;
    }
}
