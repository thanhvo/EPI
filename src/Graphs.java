import java.awt.Color;
import java.util.*;

public class Graphs {
	// Check cur is within maze and is a white pixel
	private static boolean is_feasible(Coordinate cur, int[][] maze) {
		return cur.x >= 0 && cur.x < maze.length && cur.y >= 0 && cur.y < maze[cur.x].length && maze[cur.x][cur.y] == 0;
	}
	// Perform DFS to find a feasible path 
	private static boolean search_maze_helper(int[][] maze, Coordinate cur, Coordinate e, List<Coordinate> path) {
		if (cur.equals(e)) {
			return true;
		}
		int[][] shift = {{0,1},{0,-1},{1,0},{-1,0}};
		for (int[] s : shift) {
			Coordinate next = new Coordinate(cur.x + s[0], cur.y + s[1]);
			if (is_feasible(next, maze)) {
				maze[next.x][next.y] = 1;
				path.add(next);
				if (search_maze_helper(maze, next, e, path)) {
					return true;
				}
				path.remove(path.size() -1);
			}
		}
		return false;
	}
	
	public static List<Coordinate> search_maze(int[][] maze, Coordinate s, Coordinate e) {
		List<Coordinate> path = new ArrayList<Coordinate>();
		maze[s.x][s.y] = 1;
		path.add(s);
		if (search_maze_helper(maze, s, e, path) == false) {
			path.remove(path.size() -1);
		}
		return path; // empty path means no path from s to e
	}
	
	public static int production_sequence(String s, String t, Set<String> dict) {
		if (s == null || t == null || s.length() != t.length() || !dict.contains(t)) 
			return -1;
		if (s == t) return 0;
		Queue<Pair<String, Integer>> queue = new LinkedList();
		Set<String> visited = new HashSet<String>();
		queue.add(new Pair(s, 0));
		while (!queue.isEmpty()) {
			Pair<String, Integer> p = queue.poll();
			String cur = p.first;
			int len = p.second;
			for (int i = 0; i < cur.length(); i++) {
				for (char c = 'a'; c <= 'z'; c++) {
					if (c != cur.charAt(i)) {
						String next = cur.substring(0, i) + c + cur.substring(i+1);						
						if (!visited.contains(next) && dict.contains(next)) {
							if (next.equals(t)) return (len +1);
							visited.add(next);
							queue.add(new Pair(next, len +1));
						}
					}
				}
			}
		}
		return -1;
	}
	
	private static boolean BFS(GraphVertex s) {
		Queue<GraphVertex> q = new LinkedList();
		q.add(s);
		while (!q.isEmpty()) {
			for (GraphVertex t : q.peek().edges) {
				if (t.d == -1) { // unvisited vertex
					t.d = q.peek().d + 1;
					q.add(t);
				} else if (t.d == q.peek().d) {
					return false;
				}
			}
			q.poll();
		}
		return true;
	}
	
	public static boolean is_any_placement_feasible(List<GraphVertex> G) {
		for (GraphVertex v : G) {
			if (v.d == -1) { // unvisited vertex
				v.d = 0;
				if (BFS(v) == false) {
					return false;
				}
			}
		}
		return true;
	}
	
	private static boolean DFS(GraphVertex cur, GraphVertex pre) {
		// Visiting a grey vertex means a cycle
		if (cur.color == GraphVertex.Color.gray) {
			return true;
		}
		cur.color = GraphVertex.Color.gray; // marks current vertex as a grey one
		// Traverse the neighbor vertices 
		for (GraphVertex next: cur.edges) {
			if (next != pre && next.color != GraphVertex.Color.black) {
				if (DFS(next, cur)) {
					return true;
				}
			}
		}
		cur.color = GraphVertex.Color.black; // marks current vertex as black
		return false;
	}
	
	public static boolean is_graph_2_exists(List<GraphVertex> G) {
		if (G.isEmpty() == false) {
			return DFS(G.get(0), (GraphVertex)null);
		}
		return false;
	}
	
	private static boolean DFS(GraphVertex cur, GraphVertex pre, int time) {
		cur.discovery = ++time;
		cur.leaving = Integer.MAX_VALUE;
		for (GraphVertex next : cur.edges) {
			if (next != pre) {
				if (next.discovery != 0) { // back edge
					cur.leaving = Math.min(cur.leaving, next.discovery);
				} else { // forward edge
					if (DFS(next, cur, time) == false) {
						return false;
					}
					cur.leaving = Math.min(cur.leaving, next.leaving);
				}
			}
		}
		return (pre == null || cur.leaving < cur.discovery);
	}
	
	public static boolean is_graph_2_for_all(List<GraphVertex> G) {
		if (!G.isEmpty()) {
			return DFS(G.get(0), null, 0);
		}
		return true;
	}
	
	private static void DFS(GraphVertex cur, int time, List<GraphVertex> contacts) {
		for (GraphVertex next : cur.edges) {
			if (next.visitTime != time) {
				next.visitTime = time;
				contacts.add(next);
				DFS(next, time, contacts);
			}
		}
	}
	
	public static void transitive_closure(List<GraphVertex> G) {
		// Build extended contacts for each vertex
		for (int i = 0; i < G.size(); ++i) {
			if (G.get(i).visitTime != i) {
				G.get(i).visitTime = i;
				DFS(G.get(i), i, G.get(i).extendedContacts);
			}
		}
	}
	
	private static void DFS(GraphVertex u) {
		for (GraphVertex v : u.edges) {
			if (v.group == -1) {
				v.group = u.group;
				DFS(v);
			}
		}
	}
	
	public static boolean are_constraints_satisfied(List<Constraint> E, List<Constraint> I) {
		HashMap<Integer, GraphVertex> G = new HashMap<Integer, GraphVertex>();
		// Build graph G according ot E
		for (Constraint e : E) {
			if (!G.containsKey(e.a)) G.put(e.a, new GraphVertex());
			if (!G.containsKey(e.b)) G.put(e.b, new GraphVertex());
			G.get(e.a).edges.add(G.get(e.b));
			G.get(e.b).edges.add(G.get(e.a));
		}
		// Assign group index for each connected component 
		int group_count = 0;
		for (GraphVertex vertex : G.values()) {
			if (vertex.group == -1) { // is a unvisited vertex
				vertex.group = group_count++;
				// assign a group index
				DFS(vertex);
			}
		}
		// Examine each inequality constraint to see if there is a violation
		for (Constraint i : I) {
			if (G.get(i.a).group == G.get(i.b).group) {
				return false;
			}
		}
		return true;
	}
	
	private static void DFS(GraphVertex cur, Stack<GraphVertex> vertex_order) {
		cur.visited = true;
		for (GraphVertex next: cur.edges) {
			if (next.visited == false) {
				DFS(next, vertex_order);
			}
		}
		vertex_order.push(cur);
	}
	
	private static Stack<GraphVertex> build_topological_ordering(List<GraphVertex> G) {
		Stack<GraphVertex> vertex_order = new Stack<GraphVertex>();
		for (GraphVertex g: G) {
			if (g.visited == false) {
				DFS(g, vertex_order);
			}
		}
		return vertex_order;
	}
	
	private static int find_longest_path(Stack<GraphVertex> vertex_order) {
		int max_distance = 0;
		while (vertex_order.empty() == false) {
			GraphVertex u = vertex_order.pop();
			max_distance = Math.max(max_distance, u.maxDistance);
			for (GraphVertex v : u.edges) {
				v.maxDistance = Math.max(v.maxDistance, u.maxDistance + 1);
			}
		}
		return max_distance;
	}
	
	public static int find_longest_path(List<GraphVertex> G) {
		Stack<GraphVertex> vertex_order = build_topological_ordering(G);
		return find_longest_path(vertex_order);
	}
	
	private static void output_shortest_path(GraphVertex v) {
		if (v != null) {
			output_shortest_path(v.pred);
			System.out.print(v.id + " ");
		}
	}
	
	public static void Dijkstra_shortest_path(List<GraphVertex> G, GraphVertex s, GraphVertex t) {
		// Initialization the distance of starting point
		s.distance = 0;
		s.edgeNum = 0;
		Comparator<GraphVertex> comparator = (v1, v2)-> (v1.distance < v2.distance ? -1 : v1.distance > v2.distance ? 1 : 
			v1.edgeNum < v2.edgeNum ? -1: v1.edgeNum > v2.edgeNum ? 1 : 0);
		PriorityQueue<GraphVertex> node_set = new PriorityQueue<GraphVertex>(comparator);
		node_set.add(s);
		do {
			GraphVertex u = null;
			// Extract the minimum distance vertex from heap
			while (!node_set.isEmpty()) {
				u = node_set.poll();
				if (!u.visited) { // found an unvisited node
					break;
				}
			}
			if (u != null) { // u is a valid vertex
				u.visited = true; // mark u as a visited node
				// Relax neighboring vertices of u
				for (Pair<GraphVertex, Integer> p : u.distanceEdges) {
					int v_distance = u.distance + p.second;
					int v_num_edges = u.edgeNum + 1;
					if (p.first.distance > v_distance || p.first.distance == v_distance && p.first.edgeNum > v_num_edges) {
						node_set.remove(p.first);
						p.first.pred = u;
						p.first.distance = v_distance;
						p.first.edgeNum = v_num_edges;
						node_set.add(p.first);
					}
				} 				
			} else { // u is not a valid vertex
				break;
			}
		} while (t.visited == false); // until t is visited
		// Output the shortest path with fewest edges
		output_shortest_path(t);
		System.out.println();
	}
	
	private static void Floyd_Warshall(int[][] G) {
		for (int k = 0; k < G.length; ++k) {
			for (int i = 0; i < G.length; ++i) {
				for (int j = 0; j < G.length; ++j) {
					if (G[i][k] != Integer.MAX_VALUE && G[k][j] != Integer.MAX_VALUE &&
						G[i][j] > G[i][k] + G[k][j]) {
						G[i][j] = G[i][k] + G[k][j];
					}
				}
			}
		}
	}
	
	public static HighwaySection find_best_proposal(HighwaySection[] H, HighwaySection[] P, int a, int b, int n) {
		// G stores the shortest path distance between all pairs
		int[][] G = new int[n][n];
		for (int i = 0; i < n; i++) {
			G[i][i] = 0;
			for (int j = 0; j < n; j++) {
				if (i != j) G[i][j] = Integer.MAX_VALUE;				
			}
		}
		// Build graph G based on existing highway sections H
		for (HighwaySection h : H) {
			G[h.x][h.y] = G[h.y][h.x] = h.distance;
		}
		// Perform Floyd Warshall to build the shortest path between vertices
		Floyd_Warshall(G);
		int min_dis_a_b = G[a][b];
		HighwaySection best_proposal = null;
		for (HighwaySection p : P) {
			if (G[a][p.x] != Integer.MAX_VALUE && G[p.y][b] != Integer.MAX_VALUE) {
				if (min_dis_a_b > G[a][p.x] + p.distance + G[p.y][b]) {
					min_dis_a_b = G[a][p.x] + p.distance + G[p.y][b];
					best_proposal = p;
				}
				if (min_dis_a_b > G[a][p.y] + p.distance + G[p.x][b]) {
					min_dis_a_b = G[a][p.y] + p.distance + G[p.x][b];
					best_proposal = p;
				}
			}
		}
		return best_proposal;
	}
}
