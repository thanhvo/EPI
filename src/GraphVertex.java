import java.util.*;

public class GraphVertex {
	public int d;
	public int discovery, leaving;
	List<GraphVertex> edges;
	
	public enum Color {
		white, gray, black
	}
	
	public Color color;
	
	public GraphVertex() {
		d = -1;
		edges = new ArrayList<GraphVertex>();
		discovery = 0;
		leaving = Integer.MAX_VALUE;
	}
	
	public void addEdge(GraphVertex v) {
		edges.add(v);
	}
}
