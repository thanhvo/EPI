import java.util.*;

public class GraphVertex {
	public int d;
	List<GraphVertex> edges;
	
	public GraphVertex() {
		d = -1;
		edges = new ArrayList<GraphVertex>();
	}
	
	public void addEdge(GraphVertex v) {
		edges.add(v);
	}
}
