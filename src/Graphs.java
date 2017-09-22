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
}
