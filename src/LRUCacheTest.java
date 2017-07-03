import org.junit.*;
import static org.junit.Assert.*;

public class LRUCacheTest {
	@Test
	public void ISBN_cache() {
		LRUCache<String, Integer> book_cache = new LRUCache<String, Integer>(10);
		book_cache.add("Pride and Prejudice", 10);
		book_cache.add("Wuthering Heights", 20);
		book_cache.add("Sherlock Homes", 30);
		book_cache.add("Harry Potter", 50);
		book_cache.add("The Lord of the Rings", 50);
		book_cache.add("The Hobbit", 30);
		book_cache.add("Ivanhoe", 10);
		book_cache.add("Life of Pi", 20);
		book_cache.add("Romeo and Juliet", 10);
		book_cache.add("Hamlet", 10);		
		assertEquals(book_cache.get("Pride and Prejudice").intValue(), 10);
		assertEquals(book_cache.get("Twilight"), null);
		book_cache.add("Twilight", 20);
		book_cache.add("The hunger games", 20);
		assertEquals(book_cache.get("Pride and Prejudice").intValue(), 10);
		assertEquals(book_cache.get("Wuthering Heights"), null);
		assertEquals(book_cache.get("Twilight").intValue(), 20);
	}
}
