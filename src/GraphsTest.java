import org.junit.*;
import static org.junit.Assert.*;

import java.io.File;
import java.util.*;

public class GraphsTest {
	@Test
	public void test_search_maze() {
		System.out.println("Search maze");
		int[][] maze = {
				{0,0,0,1,1},
			    {0,0,1,1,1},
			    {0,0,0,1,1},
			    {1,1,0,0,1},
			    {1,0,0,0,0}
		};
		Coordinate start = new Coordinate(0,0);
		Coordinate end = new Coordinate(4,4);
		List<Coordinate> path = Graphs.search_maze(maze, start, end);
			for (Coordinate c : path) {
			        System.out.print(c + " ");			        
			}
			System.out.println();
	}
	
	private Set<String> getDictionary(String filename) throws Exception {
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
	
	@Test
	public void test_production_sequence() throws Exception {
		System.out.println("Production sequence");
		Set<String> dictionary = getDictionary("/usr/share/dict/words");
		//assertEquals(Graphs.production_sequence("cat", "car", dictionary), 1);
		assertEquals(Graphs.production_sequence("cat", "far", dictionary), 2);
		//assertEquals(Graphs.production_sequence("cat", "fat", dictionary), 1);
		//assertEquals(Graphs.production_sequence("cat", "fap", dictionary), -1);
	}
}
