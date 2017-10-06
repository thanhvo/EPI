import org.junit.*;
import static org.junit.Test.*;
import java.util.*;

public class IntractabilityTest {
	@Test
	public void test_ties_election() {
		int[] V = {1,3,5,7,2};
		System.out.println(Intractability.ties_election(V));
		int[] L = {1,3,5,7,12};
		System.out.println(Intractability.ties_election(L));
	}
}
