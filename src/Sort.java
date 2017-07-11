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
	
	public static <T extends Comparable> List<T> intersect_sorted_arrays(List<T> A, List<T> B) {
		if (A == null || A.isEmpty() || B == null || B.isEmpty()) {
			return null;
		}
		List<T> C = new ArrayList<T>();
		Iterator<T> it1 = A.iterator();
		Iterator<T> it2 = B.iterator();
		T a = it1.next();
		T b = it2.next();
		do {
			if (a.compareTo(b) < 0) {
				if (it1.hasNext()) {
					a = it1.next();
				} else {
					a = null;
				}
			} else if (a.compareTo(b) > 0) {
				if (it2.hasNext()) {
					b = it2.next();
				} else {
					b = null;
				}
			} else {
				C.add(a);
				if (it1.hasNext()) {
					a = it1.next();
				} else {
					a = null;
				} 
				if (it2.hasNext()) {
					b = it2.next();	
				} else {
					b = null;
				}
			}
		} while (a != null && b != null);
		
		return C;
	}
	
	public static int getMaxConcurrentEvents(List<Event> events) {
		Collections.sort(events);		
		HashMap<Integer, Event> map = new HashMap<Integer, Event>();
		int max = 0;
		for (Event event: events) {
			boolean newLayer = true;
			for (int i = 1; i <= max; i++) {
				if (newLayer && !event.intersect(map.get(i))){
					newLayer = false;
					map.put(i, event);					
				}
			}
			if (newLayer) {
				map.put(++max, event);
			}
		}
		return max;
	}
	
	public static<TimeType extends Comparable> int find_max_concurrent_events(List<Interval<TimeType>> A) {
		// Build the end point array
		List<EndPoint<TimeType>> E = new ArrayList<EndPoint<TimeType>>();
		for (Interval<TimeType> i : A) {
			E.add(new EndPoint<TimeType>(i.start, true));
			E.add(new EndPoint<TimeType>(i.finish, false));
		}
		// Sort the end point array according to the time
		Collections.sort(E);
		// Find the maximum number of events overlapped
		int max_count = 0, count = 0;
		for (EndPoint<TimeType> e: E) {
			if (e.isStart) {
				max_count = Math.max(++count, max_count);
			} else {
				--count;
			}
		}
		return max_count;
	}
	
	public static<TimeType extends Comparable> List<Interval<TimeType>> union_intervals(List<Interval<TimeType>> A) {
		List<EndPoint<TimeType>> E = new ArrayList<EndPoint<TimeType>>();
		for (Interval<TimeType> i: A) {
			E.add(new EndPoint<TimeType>(i.start, true));
			E.add(new EndPoint<TimeType>(i.finish, false));
		}
		// Sort the end point array
		Collections.sort(E);
		int count = 0;
		TimeType start = null;
		List<Interval<TimeType>> B = new ArrayList<Interval<TimeType>>();
		for (EndPoint<TimeType> e: E) {
			if (e.isStart) {
				if (count == 0) {
					start = e.time;
				}
				count++;
			} else {
				count--;
				if (count == 0) {
					B.add(new Interval<TimeType>(start, e.time));
				}
			}
		}
		return B;
	}
	
	public static<TimeType extends Comparable> List<TimeType> find_minimun_visits(List<Interval<TimeType>> I) {
		Comparator<Interval<TimeType>> left_comparator = 
					(a, b) -> ((a.start != b.start) ? a.start.compareTo(b.start): a.finish.compareTo(b.finish));
		Comparator<Interval<TimeType>> right_comparator =
					(a, b) -> ((a.finish != b.finish) ? a.finish.compareTo(b.finish) : a.start.compareTo(b.start));
		TreeSet<Interval<TimeType>> L = new TreeSet<Interval<TimeType>>(left_comparator);
		TreeSet<Interval<TimeType>> R = new TreeSet<Interval<TimeType>>(right_comparator);
		for (Interval<TimeType> i : I) {
			L.add(i);
			R.add(i);
		}
		List<TimeType> S = new ArrayList<TimeType>();
		while (!L.isEmpty() && !R.isEmpty()) {
			TimeType b = R.first().finish;
			S.add(b);
			// Remove intervals which intersect with b
			Iterator<Interval<TimeType>> it = L.iterator();
			Interval<TimeType> i = it.next();
			while (i.start.compareTo(b) <= 0) {
				R.remove(i);
				it.remove();
				if (it.hasNext()) {
					i = it.next();
				} else {
					break;
				}
			}
		}
		return S;
	}
	
	public static boolean has_2_sum(int[] A, int sum) {
		int j = 0, k = A.length - 1;
		while (j <= k) {
			if (A[j] + A[k] == sum) {
				return true;
			} else if (A[j] + A[k] < sum) {
				++j;
			} else {
				--k;
			}
		}
		return false;
	}
	
	public static boolean has_3_sum(int[] A, int sum) {
		Arrays.sort(A);
		for (int a : A) {
			// find if the sum of two numbers in A equals to t - a
			if (has_2_sum(A, sum-a)) {
				return true;
			}
		}
		return false;
	}
}
