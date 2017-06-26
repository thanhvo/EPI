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

}
