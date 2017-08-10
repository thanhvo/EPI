import java.util.*;

public class ClientsCreditsInfo {
	private	int offset;
	private HashMap<String, Integer> credits;
	private TreeMap<Integer, HashSet<String>> inverse_credits;
	
	public ClientsCreditsInfo() {
		offset = 0;
		credits = new HashMap<String,Integer>();
		inverse_credits = new TreeMap<Integer, HashSet<String>>();
	}
		
	public void insert(String s, int c) {
		credits.put(s, c- offset);
		if (inverse_credits.containsKey(c-offset)) {
			inverse_credits.get(c-offset).add(s);
		} else {
			HashSet<String> set = new HashSet<String>();
			set.add(s);
			inverse_credits.put(c-offset, set);
		}
	}
	
	public void remove(String s) {
		if (credits.containsKey(s)) {
			int credit = credits.get(s);
			Set<String> set = inverse_credits.get(credit);
			set.remove(s);
			if (set.isEmpty()) inverse_credits.remove(credit);
			credits.remove(s);
		}
	}
	
	public int lookup(String s) {
		return (credits.containsKey(s) ? credits.get(s) + offset : -1);
	}
	
	public void addAll(int c) {
		offset += c;
	}
	
	String max() {
		return inverse_credits.isEmpty() ?  "" : inverse_credits.lastEntry().getValue().iterator().next();
	}	
}
