import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;

public class TrieTest {
	@Test
	public void test_shortest_prefix() {
		Set<String> D= new HashSet<String>();
		D.add("dog");
		D.add("be");
		D.add("cut");
		String s = "cat";
		assertEquals(TrieUtil.find_shortest_prefix(s, D), "ca");
		D.add("car");
		assertEquals(TrieUtil.find_shortest_prefix(s, D), "cat");
		D.add("cat");
		assertEquals(TrieUtil.find_shortest_prefix(s, D), "");
	}
}
