import java.util.*;
import java.io.*;

public class Sort {
	public static void indirect_sort(String input_file_name, String output_file_name) {
		List<String> list = new ArrayList<String>();
		
		// Store file records to the list
		try (
			Scanner scanner = new Scanner(new File(input_file_name));
		) {
			while (scanner.hasNext()) {
				list.add(scanner.next());
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		// Indirect sort file
		Collections.sort(list);
		
		// Output file
		try {
			File file = new File(output_file_name);
			PrintWriter printer = new PrintWriter(file);		
			for (String s : list) {
				printer.println(s);
			}
			printer.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		} 
	}
	
	public static<T> void swap(T[] A, int i, int j) {
		T temp = A[i];
		A[i] = A[j];
		A[j] = temp;
	}
	
	public static<T> void swap (ArrayList<T> A, int i, int j) {
		T temp = A.get(i);
		A.set(i, A.get(j));
		A.set(j, temp);
	}
	
	public static<KeyType> void counting_sort(ArrayList<Person<KeyType>> people) {
		HashMap<KeyType, Integer> key_to_count = new HashMap<KeyType, Integer>();
		for (Person<KeyType> p: people) {
			if (!key_to_count.containsKey(p.key)) {
				key_to_count.put(p.key, 1);
			} else {
				key_to_count.put(p.key, key_to_count.get(p.key) + 1);
			}
		}
		HashMap <KeyType, Integer> key_to_offset = new HashMap<KeyType, Integer>();
		int offset = 0;
		for (KeyType key : key_to_count.keySet()) {
			key_to_offset.put(key, offset);
			offset += key_to_count.get(key);
		}
		while (key_to_offset.size() > 0) {
			int from = key_to_offset.entrySet().iterator().next().getValue();
			KeyType key = people.get(from).key;
			int to = key_to_offset.get(key);
			swap(people, from, to);
			// Use key_to_count to see when we are finished with a particular key
			key_to_count.put(key, key_to_count.get(key)-1);
			if (key_to_count.get(key) > 0) {
				key_to_offset.put(key, key_to_offset.get(key) + 1);
			} else {
				key_to_offset.remove(key);
			}
		}
	}
}
