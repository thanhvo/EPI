import java.util.*;

public class Star {
	private int id;
	private double x;
	private double y;
	private double z;
	
	public Star(int id, double x, double y, double z) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public double distanceToEarth() {
		return Math.sqrt(x* x + y * y + z* z);
	}
	
	public int getId() {
		return id;
	}
	
	public static List<Star> k_closest_stars(List<Star> starList, int k) {
		Comparator<Star> comparator = (s1, s2) -> (int)(s2.distanceToEarth() - s1.distanceToEarth());
		PriorityQueue<Star> heap = new PriorityQueue<Star>(k, comparator);
		Iterator<Star> it = starList.iterator();
		for (int i = 0; i < k; i++) {
			heap.add(it.next());
		}
		while (it.hasNext()) {
			Star star = it.next();
			Star top = heap.peek();
			if (star.distanceToEarth() < top.distanceToEarth()) {
				heap.poll();
				heap.add(star);
			}
		}
		return new ArrayList(heap);
	}
}
