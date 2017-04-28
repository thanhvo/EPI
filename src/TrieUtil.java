import java.util.*;

public class TrieUtil {
	public static String find_shortest_prefix(String s, Set<String> D) {
		Trie T = new Trie();
		for (String word: D) {
			T.insert(word);
		}
		return T.getShortestUniquePrefix(s);
	}
}
