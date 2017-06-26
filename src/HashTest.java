import org.junit.*;
import static org.junit.Assert.*;

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
}
