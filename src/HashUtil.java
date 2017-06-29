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
	
	public static List<String> getFrequentItems(String input, int k) {
		Scanner scanner = new Scanner(input);
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		int itemNum = 0;
		while (scanner.hasNext()) {			
			for (int i = 0; i < k; i++) {
				if (scanner.hasNext()) {
					String s = scanner.next();
					itemNum++;
					if (map.containsKey(s)) {
						map.put(s, map.get(s) +1);
					} else if (map.size() < k) {
						map.put(s, 1);
					}
				} else {
					break;
				}
			}
			if (!scanner.hasNext())
				break;
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
			if (map.get(s) >= itemNum/k) list.add(s);
		}
		return list;
	}

}
