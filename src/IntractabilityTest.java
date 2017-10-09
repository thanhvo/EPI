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
}
