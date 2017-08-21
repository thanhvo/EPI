import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;

public class AlgorithmsTest {
	@Test
	public void test_drawing_skylines() {
		List<Skyline<Integer, Integer>> skylines = Arrays.asList(new Skyline(1,2,3), new Skyline(2,4,2), new Skyline(3,4,3), new Skyline(1,5,1));
	    List<Skyline<Integer, Integer>> drawn_skylines = Algorithms.draw_skylines(skylines);
	    for (Skyline<Integer, Integer> skyline : drawn_skylines) 
	        skyline.print();
	}
	
	@Test
	public void test_count_inversions() {
		List<Integer> v = Arrays.asList(4,3,1,5,8,7,10,9);
		assertEquals(Algorithms.count_inversions(v), 5);
	}
	
	@Test
	public void test_find_closest_pair_points() {
		ArrayList<Point> P = new ArrayList<Point>(Arrays.asList(new Point(1,2), new Point(3,4), new Point(2,3), new Point(5,8), new Point(4,4)));
		Pair<Point, Point> pair = Algorithms.find_closest_pair_points(P);
		pair.first.print();
		pair.second.print();
	}
}
