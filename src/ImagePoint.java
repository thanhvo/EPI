
public class ImagePoint implements Comparable<ImagePoint> {
	public int i, j;
	
	public ImagePoint(int i, int j) {
		this.i = i;
		this.j = j;
	}
	
	public int compareTo(ImagePoint that) {
		return (i > that.i || j > that.j ? 1 : i == that.i && j == that.j ? 0 : -1);  
	}
	
	public void print() {
		System.out.println(i + " " + j);
	}
}
