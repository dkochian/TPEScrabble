package dictionary;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class Trie {

	/**
	 * Will not contain a char value, its children will be all the nodes containing the first letters in each word.
	 */
	private TrieNode root;

	public Trie() {
		root = new TrieNode();
	}

	/**
	 * @param word to be added to the trie
	 */
	public void insert(String word) {
		HashMap<Character, TrieNode> children = root.children;

		for(int i=0; i<word.length(); i++){
			char c = word.charAt(i);

			TrieNode t;
			if(children.containsKey(c)){
				t = children.get(c);
			}else{
				t = new TrieNode(c);
				children.put(c, t);
			}

			children = t.children;

			//set leaf node
			if(i == word.length()-1){
				t.isEndNode = true; 
			}
		}
	}

	/**
	 * @param word that will be searched in the trie
	 * @return true if the word is found, false if not
	 */
	public boolean search(String word) {
		TrieNode t = searchNode(word);

		if(t != null && t.isEndNode()){
			return true;
		}else{
			return false;
		}
	}


	/**
	 * @param prefix
	 * @return if there is any word in the trie that starts with the given prefix.
	 */
	public boolean startsWith(String prefix) {
		if(searchNode(prefix) == null) 
			return false;
		else
			return true;
	}

	/**
	 * @param str
	 * @return true if the string was found (is a valid word), false if not.
	 */
	private TrieNode searchNode(String str){
		Map<Character, TrieNode> children = root.children; 
		TrieNode t = null;
		for(int i=0; i<str.length(); i++){
			char c = str.charAt(i);
			if(children.containsKey(c)){
				t = children.get(c);
				children = t.children;
			}else{
				return null;
			}
		}
		return t;
	}

	/**
	 * Class representing each node in the trie
	 */
	private static class TrieNode {
		private char c;
		private HashMap<Character, TrieNode> children = new HashMap<Character, TrieNode>();
		boolean isEndNode;

		public TrieNode() {}

		public TrieNode(char c){
			this.c = c;
		}

		public boolean isEndNode(){
			return isEndNode;
		}
	}

	protected HashSet<String> possibleWords(List<Character> possibleLetters){
		
		HashSet<String> words = new HashSet<String>();
		System.out.println(possibleLetters.toString());
		for(TrieNode node: root.children.values())
			possibleWords(node, possibleLetters, words, new StringBuffer());
		return words;
	}

	private void possibleWords(TrieNode node, List<Character> letters, HashSet<String> words, StringBuffer currWord){
		System.out.println(currWord.toString());
		if( !letters.contains(node.c))
			return;
		
		currWord.append(node.c);
		if(node.isEndNode())
			words.add(currWord.toString());
		
		if(node.children.isEmpty())
			return;
		
		letters.remove(node.c);
		for(TrieNode child: node.children.values()){
			possibleWords(child, letters, words, currWord);
		}

	}
}
