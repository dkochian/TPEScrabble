package dictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Trie {

	// despues lo pongo private lo hago asi para ver el metodo de valid words
	/**
	 * Will not contain a char value, its children will be all the nodes
	 * containing the first letters in each word.
	 */
	protected TrieNode root;

	public Trie() {
		root = new TrieNode();
	}

	/**
	 * @param word  to be added to the trie
	 */
	public void insert(String word) {
		HashMap<Character, TrieNode> children = root.children;

		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);

			TrieNode t;
			if (children.containsKey(c)) {
				t = children.get(c);
			} else {
				t = new TrieNode(c);
				children.put(c, t);
			}

			children = t.children;

			if (i == word.length() - 1) {
				t.endNode = true;
			}
		}
	}

	/**
	 * @param word
	 *            that will be searched in the trie
	 * @return true if the word is found, false if not
	 */
	public boolean search(String word) {
		TrieNode t = searchNode(word);

		if (t != null && t.endNode) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @param prefix
	 * @return if there is any word in the trie that starts with the given
	 *         prefix.
	 */
	public boolean startsWith(String prefix) {
		if (searchNode(prefix) == null)
			return false;
		else
			return true;
	}

	/**
	 * @param str
	 * @return true if the string was found (is a valid word), false if not.
	 */
	private TrieNode searchNode(String str) {
		Map<Character, TrieNode> children = root.children;
		TrieNode t = null;
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (children.containsKey(c)) {
				t = children.get(c);
				children = t.children;
			} else {
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
		boolean endNode;

		public TrieNode() {
		}

		public TrieNode(char c) {
			this.c = c;
		}
	}

	/**
	 * 
	 * @param letters that the words could contain
	 * @return hashset which are possible with the available letters
	 */
	protected ArrayList<String> getPossibleWords(List<Character> letters) {
		ArrayList<String> awns = new ArrayList<String>();

		for (TrieNode rootChild : root.children.values()) {
			ArrayList<Character> auxLetters = new ArrayList<Character>( letters);
			getPossibleWords(rootChild, awns, auxLetters, new StringBuffer());
		}
		return awns;
	}

	
	
	private void getPossibleWords(TrieNode node, ArrayList<String> words, List<Character> letters, StringBuffer word){
		if(node.endNode && letters.contains(node.c)){
			word.append(node.c);
			words.add(word.toString());
			return;
		}else if(!letters.contains(node.c)){
			return;
		}
		if(node.children.isEmpty())
			return;
		word.append(node.c);
		
		int index = letters.indexOf(node.c);
		letters.remove(index);
		for(TrieNode child: node.children.values()){
			StringBuffer aux = new StringBuffer();
			aux.append(word);
			getPossibleWords(child, words, letters, aux);
		}
	}

}
