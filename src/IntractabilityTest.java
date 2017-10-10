import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;

public class IntractabilityTest {
	@Test
	public void test_ties_election() {
		int[] V = {1,3,5,7,2};
		System.out.println(Intractability.ties_election(V));
		int[] L = {1,3,5,7,12};
		System.out.println(Intractability.ties_election(L));
	}
	
	@Test
	public void test_knapsack() {
		int W = 130;
		int[][] items = {{20,65}, {8,35}, {60,245}, {55, 195}, {40,65}, {70, 150}, {70, 150}, {85, 275},
				{25, 155}, {30, 120}, {65, 320}, {75, 75}, {10, 40}, {95, 200}, {50, 100}, {40, 220}, {10, 99}
		};
		assertEquals(Intractability.knapsack(W, items), 695);
	}
	
	@Test
	public void test_minimize_difference() {
		int[] items = {65, 35, 245, 195, 65, 150, 275, 155, 120, 320, 75, 40, 200, 100, 220, 99};
	    assertEquals(Intractability.minimize_difference(items), 65 + 275 + 320 + 200 + 220 + 99);
	}
	
	@Test
	public void test_defective_jugs() {
		List<Jug> jugs = Arrays.asList(new Jug(230, 240), new Jug(290, 310), new Jug(500, 520));
		assertEquals(Intractability.check_feasible(jugs, 2100, 2200), true);
		assertEquals(Intractability.check_feasible(jugs, 2100, 2150), false);
		assertEquals(Intractability.check_feasible(jugs, 2200, 2300), false);
	}
}
