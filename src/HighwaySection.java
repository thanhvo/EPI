
public class HighwaySection {
	public int x, y, distance;
	
	public HighwaySection (int x, int y, int distance) {
		this.x = x;
		this.y = y;
		this.distance = distance;
	}
	
	public void print() {
		System.out.println(x + " " + y + " " + distance);
	}
	
}
