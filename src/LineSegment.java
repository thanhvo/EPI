public class LineSegment<XaxisType extends Comparable, ColorType extends Comparable, HeightType extends Comparable> 
		implements Comparable<LineSegment<XaxisType, ColorType, HeightType>> {
		public XaxisType left, right;
		public ColorType color;
		public HeightType height;
		
		public LineSegment(XaxisType __left, XaxisType __right, ColorType __color, HeightType __height) {
			left = __left;
			right = __right;
			color = __color;
			height = __height;
		}
		
		public int compareTo(LineSegment<XaxisType, ColorType, HeightType> other) {
			return height.compareTo(other.height);
		}
}