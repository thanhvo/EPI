import org.junit.*;
import static org.junit.Assert.*;
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
}
