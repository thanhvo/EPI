public class Lowest<T extends Comparable<T>> implements Comparable<T> {
	public int compareTo(T other) {
		return -1;
	}
}