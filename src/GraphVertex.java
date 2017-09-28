import java.util.*;

public class GraphVertex {
	public int d;
	public int discovery, leaving;
	public int visitTime;
	List<GraphVertex> edges;
	List<GraphVertex> extendedContacts;
	
	public enum Color {
		white, gray, black
	}
	
	public Color color;
	
	public GraphVertex() {
		d = -1;
		edges = new ArrayList<GraphVertex>();
		extendedContacts = new ArrayList<GraphVertex>();
		discovery = 0;
		leaving = Integer.MAX_VALUE;
		visitTime = -1;
	}
	
	public void addEdge(GraphVertex v) {
		edges.add(v);
	}
}
