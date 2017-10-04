import java.util.*;

public class GraphVertex {
	public int d;
	public int discovery, leaving;
	public int visitTime;
	public int group; // represents the connected component it belongs
	public int maxDistance;
	public boolean visited;
	public List<GraphVertex> edges;
	public List<GraphVertex> extendedContacts;
	public int distance; // the distance to the source
	public int edgeNum; // number of edges
	// Stores (vertex, distance) pairs
	public List<Pair<GraphVertex, Integer>> distanceEdges;
	public int id; // the id of this vertex
	public GraphVertex pred; // stores the predecessor in the shortest path
		
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
		pred = null;
		edgeNum = 0;
		distance = Integer.MAX_VALUE;
		distanceEdges = new ArrayList<Pair<GraphVertex, Integer>>();
	}
	
	public GraphVertex(int id) {
		d = -1;
		edges = new ArrayList<GraphVertex>();
		extendedContacts = new ArrayList<GraphVertex>();
		discovery = 0;
		leaving = Integer.MAX_VALUE;
		visitTime = -1;
		group = -1;
		maxDistance = 1;
		visited = false;
		pred = null;
		edgeNum = 0;
		distance = Integer.MAX_VALUE;
		distanceEdges = new ArrayList<Pair<GraphVertex, Integer>>();
		this.id = id;
	}
	
	public void addEdge(GraphVertex v) {
		edges.add(v);
	}
	
	public void addEdge(GraphVertex v, int dis) {
		distanceEdges.add(new Pair<GraphVertex, Integer>(v, dis));
		edgeNum++;
	}
}
