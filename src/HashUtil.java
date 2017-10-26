import java.io.*;
import java.util.*;

public class HashUtil {
	public static Pair<Integer, Integer> closest_repeated_pair(String[] list) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		Pair<Integer, Integer> pair = null;
		for (int i = 0; i < list.length; i++) {
			if (map.containsKey(list[i])) {
				int j = map.get(list[i]);
				if (pair == null || pair.second - pair.first > i -j) {
					pair = new Pair(j, i);
				}
			}
			map.put(list[i], i);
		}
		return pair;
	}
	
	public static String anagram_hash(String s) {
		int[] count = new int[26];
		for (int i = 0; i < s.length(); i++) {
			count[s.charAt(i)-'a']++;
		}
		String hash = "";
		for (int i = 0; i < 26; i++) {
			hash += count[i];
			hash += (char)(i + 'a');
		}
		return hash;
	}
	
	public static Map<String, List<String>> get_anagram_sub_sets(List<String> list) {
		Map<String, List<String>> map =new HashMap<String, List<String>>();
		for (String s: list) {
			String key = anagram_hash(s);
			if (!map.containsKey(key)) {
				List<String> subset = new ArrayList<String>();
				subset.add(s);
				map.put(key, subset);
			} else {
				map.get(key).add(s);
			}
		}
		return map;
	}
	
	public static List<String> getDictionary(String filename) throws Exception{
		Scanner scanner = new Scanner(new File(filename));
		Set<String> set= new HashSet<String>();
		while (scanner.hasNext()) {
			String s = scanner.next();
			if (s.matches("[a-z]*"))
				set.add(s);
		}
		scanner.close();
		return new ArrayList(set);
	}
	
	public static Set<String> getDictionaryAsSet(String filename) throws Exception{
		Scanner scanner = new Scanner(new File(filename));
		Set<String> set= new HashSet<String>();
		while (scanner.hasNext()) {
			String s = scanner.next();
			if (s.matches("[a-z]*"))
				set.add(s);
		}
		scanner.close();
		return set;
	} 
	
	public static List<String> getFrequentItems(String input, int k) {
		Scanner scanner = new Scanner(input);
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		int itemNum = 0;
		while (scanner.hasNext()) {			
			for (int i = 0; i <= k; i++) {
				if (scanner.hasNext()) {
					String s = scanner.next();
					itemNum++;
					if (map.containsKey(s)) {
						map.put(s, map.get(s) +1);
					} else if (map.size() <= k) {
						map.put(s, 1);
					}
				} else {
					break;
				}
			}
			if (map.size() == k +1) {
				Iterator it = map.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>)it.next();
					String s = entry.getKey();
					if (map.get(s) > 1) {
						map.put(s,  map.get(s) -1);
					} else {
						it.remove();
					}
				}
			}
		}
		scanner.close();
		scanner = new Scanner(input);
		for (String s: map.keySet()) {
			map.put(s, 0);
		}
		while (scanner.hasNext()) {
			String s = scanner.next();
			if (map.containsKey(s)) {
				map.put(s, map.get(s) + 1);
			}
		}
		List<String> list = new ArrayList<String>();
		for(String s: map.keySet()) {
			if (map.get(s) >= (double)itemNum/k) list.add(s);
		}
		return list;
	}
	
	public static Pair<Integer, Integer> find_smallest_subarray_covering_subset(String[] A, String[] Q) {
		HashSet<String> dict = new HashSet<String>(Arrays.asList(Q));
		HashMap<String, Integer> count_Q = new HashMap<String, Integer>();
		int l = 0, r = 0;
		Pair<Integer, Integer> res= new Pair(-1, -1);
		while (r < A.length) {
			// Keep moving r until it reaches end or count_Q has |Q| items
			while (r < A.length && count_Q.size() < Q.length) {
				if (dict.contains(A[r])) {
					if (count_Q.containsKey(A[r])) {
						count_Q.put(A[r], count_Q.get(A[r])+1);
					} else {
						count_Q.put(A[r], 1);
					}					
				}
				++r;
			}
			if (count_Q.size() == Q.length && (res.first == -1 && res.second == -1)) {
				res.first = l;
				res.second = r -1;
			}
			// Keep moving l until it reaches end or count_Q has less than |Q| items
			while (l < A.length && count_Q.size() == Q.length) {
				if (dict.contains(A[l])) {
					if (count_Q.get(A[l]) == 1) {						
						count_Q.remove(A[l]);
						if ((res.first == -1 && res.second == -1) || r-1-l < res.second - res.first) {
							res.first = l;
							res.second = r -1;
						}
					} else {
						count_Q.put(A[l],count_Q.get(A[l]) -1);
					}
				}
				++l;
			}			
		}
		return res;
	}
	
	public static Pair<Integer, Integer> find_smallest_sequentially_covering_subset(String[] A, String[] Q) {
		HashMap<String, Integer> K = new HashMap<String, Integer>();
		int[] L = new int[Q.length];
		Arrays.fill(L, -1);
		int[] D = new int[Q.length];
		Arrays.fill(D, Integer.MAX_VALUE);
		// Initialize K
		for (int i = 0; i < Q.length; ++i) {
			K.put(Q[i], i);
		}
		Pair<Integer, Integer> res = new Pair(-1, A.length); // default value
		for (int i = 0; i < A.length; ++i) {
			if (K.containsKey(A[i])) {
				int id = K.get(A[i]);
				if (id == 0) { // first one, no predecessor
					D[0] = 1;
				} else if(D[id -1] != Integer.MAX_VALUE) {
					D[id] = i - L[id-1] + D[id-1];
				}
				L[id] = i;
				if (id == Q.length -1 && D[id] < res.second - res.first +1) {
					res.first = i - D[id] + 1;
					res.second = i;
				}
			}
		}
		return res;
	}

}
