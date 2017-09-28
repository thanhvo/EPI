import java.util.*;

public class GraphVertex {
	public int d;
	public int discovery, leaving;
	public int visitTime;
	public int group; // represents the connected component it belongs
	public int maxDistance;
	public boolean visited;
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
		group = -1;
		maxDistance = 1;
		visited = false;
	}
	
	public void addEdge(GraphVertex v) {
		edges.add(v);
	}
}
