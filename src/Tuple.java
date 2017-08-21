
public class Tuple<F, S, T> {
	public F first;
	public S second;
	public T third;
	
	public Tuple() {
		first = null;
		second = null;
		third = null;
	}
	
	public Tuple(F f, S s, T t) {
		first = f;
		second = s;
		third = t;
	}
}
