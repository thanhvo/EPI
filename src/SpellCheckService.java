import java.util.*;

public class SpellCheckService {
	private static Set<String> dict;
	private static final int WORD_NUM = 10;
	
	private static class DistanceWord implements Comparable<DistanceWord>{
		int distance;
		String word;
		
		@Override
		public int compareTo(DistanceWord word) {
			return (word.distance - distance);
		}
		
		public DistanceWord(int distance, String word) {
			this.distance = distance;
			this.word = word;
		}
	}
	
	public static void createDictionary() throws Exception {
		dict = HashUtil.getDictionaryAsSet("/usr/share/dict/words");
	}
	
	private static int distance(String w1, String w2) {
		int dis = Math.abs(w1.length() - w2.length());
		for (int i =0; i < Math.min(w1.length(), w2.length()); ++i) {
			if (Character.toUpperCase(w1.charAt(i)) != Character.toUpperCase(w2.charAt(i))) dis++;
		}
		return dis;
	}
	
	public static String[] closestInDictionary(String word) {
		if (dict.contains(word)) return null;
		PriorityQueue<DistanceWord> wordList = new PriorityQueue<DistanceWord>(WORD_NUM);
		for (String w : dict) {
			DistanceWord dWord = new DistanceWord(distance(word, w), w);
			if (wordList.size() == WORD_NUM) {
				DistanceWord farthest = wordList.peek();
				if (farthest.distance > dWord.distance) {
					wordList.poll();
					wordList.add(dWord);
				}
			} else {
				wordList.add(dWord);
			}
		}
		String[] results = new String[wordList.size()];
		int i = 0;
		for (DistanceWord dWord : wordList) {
			results[i++] = dWord.word;
		}
		return results;
	}
}
