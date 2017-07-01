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
		BTNode A = new BTNode(3);
		BTNode B = new BTNode(2);
		BTNode C = new BTNode(1);
		BTNode D = new BTNode(0);
		A.left = B;
		B.left = C;
		C.left = D;
		BTNode E = new BTNode(9);
		BTNode F = new BTNode(5);
		BTNode G = new BTNode(3);
		BTNode H = new BTNode(7);
		BTNode I = new BTNode(11);
		E.left = F;
		E.right = I;
		F.left = G;
		F.right = H;
		BTNode J = new BTNode(2);
		BTNode K = new BTNode(1);
		BTNode L = new BTNode(0);
		BTNode M = new BTNode(5);
		BTNode N = new BTNode(3);
		BTNode O = new BTNode(7);
		J.left = K;
		J.right = M;
		K.left = L;
		M.left = N;
		M.right = O;
		
		BTNode A1 = BTNode.getCanonical(A);
		BTNode E1 = BTNode.getCanonical(E);
		BTNode J1 = BTNode.getCanonical(J);
		
		BTNode[] list = {A, B, C, D, E, F, G, H, I, J, K, L, M, N, O};
		for (BTNode node: list) {
			BTNode canonical_node = BTNode.getCanonical(node);
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
		Pair<Integer, Integer> pair = HashUtil.find_smallest_subarray_covering_subset(A, Q);
		System.out.println(pair.first + " " + pair.second);
	}
}
