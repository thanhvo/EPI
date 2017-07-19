import org.junit.*;
import static org.junit.Assert.*;

import java.io.*;
import java.util.*;

public class HashTest {
	@Test
	public void test_closest_repeated_pair() {
		String[] list = {"All", "work", "and", "no", "play", "makes", "for", "no", "work", "no", "fun", "and", "no", "results"};
		Pair<Integer, Integer> pair = HashUtil.closest_repeated_pair(list);
		assertEquals(pair.first.intValue(), 7);
		assertEquals(pair.second.intValue(), 9);
		String[] list2 = {"hello", "world", "just","a","random","sentence"};
		assertEquals(HashUtil.closest_repeated_pair(list2), null);
	}
	
	@Test
	public void test_canonical_binary_tree() {
		CacheBTNode A = new CacheBTNode(3);
		CacheBTNode B = new CacheBTNode(2);
		CacheBTNode C = new CacheBTNode(1);
		CacheBTNode D = new CacheBTNode(0);
		A.left = B;
		B.left = C;
		C.left = D;
		CacheBTNode E = new CacheBTNode(9);
		CacheBTNode F = new CacheBTNode(5);
		CacheBTNode G = new CacheBTNode(3);
		CacheBTNode H = new CacheBTNode(7);
		CacheBTNode I = new CacheBTNode(11);
		E.left = F;
		E.right = I;
		F.left = G;
		F.right = H;
		CacheBTNode J = new CacheBTNode(2);
		CacheBTNode K = new CacheBTNode(1);
		CacheBTNode L = new CacheBTNode(0);
		CacheBTNode M = new CacheBTNode(5);
		CacheBTNode N = new CacheBTNode(3);
		CacheBTNode O = new CacheBTNode(7);
		J.left = K;
		J.right = M;
		K.left = L;
		M.left = N;
		M.right = O;
		
		CacheBTNode A1 = CacheBTNode.getCanonical(A);
		CacheBTNode E1 = CacheBTNode.getCanonical(E);
		CacheBTNode J1 = CacheBTNode.getCanonical(J);
		
		CacheBTNode[] list = {A, B, C, D, E, F, G, H, I, J, K, L, M, N, O};
		for (CacheBTNode node: list) {
			CacheBTNode canonical_node = CacheBTNode.getCanonical(node);
			assertEquals(node.data, canonical_node.data);
			//System.out.println(node + " " + canonical_node);
		}
	}
	
	@Test
	public void test_anagram() throws Exception{
		List<String> dictionary = HashUtil.getDictionary("/usr/share/dict/words");
		Map<String, List<String>> map = HashUtil.get_anagram_sub_sets(dictionary);
		PrintWriter printer = new PrintWriter(new File("/home/thanh/workspace/EPI/output/anagrams.txt"));
		for (String key : map.keySet()) {
			List<String> list = map.get(key);
			if (list.size() >= 2) {
				for (String s : map.get(key)) {
					printer.print(s + " ");
				}
				printer.println();
			}
		}
		printer.close();
	}
	
	@Test
	public void test_frequent_item() {
		String values = "a b c a b c a d e a b";
		for (String s: HashUtil.getFrequentItems(values, 4)) {
			System.out.print(s + " ");
		}
		System.out.println();
	}
	
	@Test
	public void test_find_smallest_subarray_covering_sub_set() {
		String[] A = {"a", "b", "c", "d", "a","b","e","f","a","b","d"};
		String[] Q = {"a", "b", "d"};
		Pair<Integer, Integer> p1 = HashUtil.find_smallest_subarray_covering_subset(A, Q);
		System.out.println(p1.first + " " + p1.second);
		Pair<Integer, Integer> p2 = HashUtil.find_smallest_sequentially_covering_subset(A, Q);
		System.out.println(p2.first + " " + p2.second);
	}
}
