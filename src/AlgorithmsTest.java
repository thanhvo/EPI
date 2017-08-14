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
}
