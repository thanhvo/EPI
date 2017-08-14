
public class Skyline<CoordType, HeightType> {
	public CoordType left, right;
	public HeightType height;
	
	public Skyline(CoordType left, CoordType right, HeightType height) {
		this.left = left;
		this.right = right;
		this.height = height;
	}
	
	public void print() {
		System.out.println("left = " + left + " right = " + right + " height = " + height);
	}
}