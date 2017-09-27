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
			return DFS(G.get(0), null);
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
}
