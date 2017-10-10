
public class Pair<F, S> {
	public F first;
	public S second;
	
	public Pair(F f, S s) {
		first = f;
		second = s;
	}
	
	public String toString() {
		return "(" + first.toString() + "," + second.toString() + ")";
	}
	
	public boolean equals(Pair<F, S> p) {
		return first.equals(p.first) && second.equals(p.second);
	}
}
