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

	public void addValidWord(String word){
		dictionary.insert(word);
	}
	
	public int checkWord(String word){
		if(isValidWord(word)){
			return wordScore(word);
		}
		return -1;
	}

	public static boolean isValidWord(String word){
		return dictionary.search(word.toUpperCase());
	}
	
	public boolean startsWith(String prefix){
		return dictionary.startsWith(prefix.toUpperCase());
	}
	
	private int wordScore(String word) {
	
        int sum = 0;
        
        for(int i = 0; i < word.length();i++) {
        	 sum += values[alphabet.indexOf(word.charAt(i))];
        }
        
        return sum;
    }
}
