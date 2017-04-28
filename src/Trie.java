import java.util.*;

public class Trie {
	private class TrieNode {
		public boolean isString;
		public Map<Character, TrieNode> l;
		public TrieNode(boolean isString) {
			this.isString = isString;
			l = new HashMap<Character, TrieNode>();
		}
	}
	private TrieNode root;
	
	public Trie() {
		root = new TrieNode(false);
	}
	
	public boolean insert(String s) {
		TrieNode p = root;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (!p.l.containsKey(c)) {
				p.l.put(c, new TrieNode(false));
			}
			p = p.l.get(c);
		}
		if (p.isString) {
			return false;
		} else {
			p.isString = true;
			return true;
		}			
	}
	
	public String getShortestUniquePrefix(String s) {
		TrieNode p = root;
		String prefix = "";
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			prefix += c;
			if (!p.l.containsKey(c)) {
				return prefix;
			}
			p = p.l.get(c);
		}
		return "";
	}
}
