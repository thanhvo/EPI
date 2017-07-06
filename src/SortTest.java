import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;

public class SortTest {
	@Test
	public void test_indirect_sort() {
		String input = "/home/thanh/workspace/EPI/input/A Patriotic Fourth: What Does That Mean Now? - The New York Times.txt";
	    String output = "/home/thanh/workspace/EPI/output/A Patriotic Fourth: What Does That Mean Now? - The New York Times_2.txt";
	    Sort.indirect_sort(input, output);
	}
	
	@Test
	public void test_counting_sort() {
		ArrayList<Person<Integer>> people = new ArrayList<Person<Integer>>();
		people.add(new Person<Integer>(2,"Thanh"));
		people.add(new Person<Integer>(3,"ThuNgan"));
		people.add(new Person<Integer>(1,"Khanh"));
		people.add(new Person<Integer>(2, "Duong"));
		people.add(new Person<Integer>(3,"Hoa"));
		people.add(new Person<Integer>(1,"Phuong"));
				
	    Sort.counting_sort(people);
	    
	    for ( Person<Integer> p: people) {
	        System.out.print(p.name + " ");
	    }
	    System.out.println();
	}
}
