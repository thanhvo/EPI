import org.junit.*;
import static org.junit.Assert.*;

import java.io.File;
import java.util.*;

public class GraphsTest {
	@Test
	public void test_search_maze() {
		System.out.println("Search maze");
		int[][] maze = {
				{0,0,0,1,1},
			    {0,0,1,1,1},
			    {0,0,0,1,1},
			    {1,1,0,0,1},
			    {1,0,0,0,0}
		};
		Coordinate start = new Coordinate(0,0);
		Coordinate end = new Coordinate(4,4);
		List<Coordinate> path = Graphs.search_maze(maze, start, end);
			for (Coordinate c : path) {
			        System.out.print(c + " ");			        
			}
			System.out.println();
	}
	
	private Set<String> getDictionary(String filename) throws Exception {
		Scanner scanner = new Scanner(new File(filename));
		Set<String> set= new HashSet<String>();
		while (scanner.hasNext()) {
			String s = scanner.next();
			if (s.matches("[a-z]*"))
				set.add(s);
		}
		scanner.close();
		return set;
	}
	
	@Test
	public void test_production_sequence() throws Exception {
		System.out.println("Production sequence");
		Set<String> dictionary = getDictionary("/usr/share/dict/words");
		//assertEquals(Graphs.production_sequence("cat", "car", dictionary), 1);
		assertEquals(Graphs.production_sequence("cat", "far", dictionary), 2);
		//assertEquals(Graphs.production_sequence("cat", "fat", dictionary), 1);
		//assertEquals(Graphs.production_sequence("cat", "fap", dictionary), -1);
	}
	
	@Test
	public void test_board_placement() {
		GraphVertex A = new GraphVertex();
		GraphVertex B = new GraphVertex();
		GraphVertex C = new GraphVertex();
		GraphVertex D = new GraphVertex();
		GraphVertex E = new GraphVertex();
		GraphVertex F = new GraphVertex();
		GraphVertex G = new GraphVertex();
		GraphVertex H = new GraphVertex();
		GraphVertex I = new GraphVertex();
		GraphVertex J = new GraphVertex();
		GraphVertex K = new GraphVertex();
		GraphVertex L = new GraphVertex();
		GraphVertex M = new GraphVertex();
		GraphVertex N = new GraphVertex();
		GraphVertex O = new GraphVertex();
		GraphVertex P = new GraphVertex();
		GraphVertex Q = new GraphVertex();
		GraphVertex R = new GraphVertex();
		GraphVertex S = new GraphVertex();
		GraphVertex T = new GraphVertex();
	    A.addEdge(B);
	    A.addEdge(I);
	    B.addEdge(A);
	    B.addEdge(C);
	    B.addEdge(J);
	    C.addEdge(B);
	    C.addEdge(D);
	    C.addEdge(K);
	    D.addEdge(C);
	    D.addEdge(E);
	    D.addEdge(L);
	    E.addEdge(D);
	    E.addEdge(F);
	    F.addEdge(E);
	    F.addEdge(G);
	    F.addEdge(L);
	    F.addEdge(M);
	    G.addEdge(F);
	    G.addEdge(H);
	    I.addEdge(A);
	    I.addEdge(J);
	    I.addEdge(N);
	    J.addEdge(B);
	    J.addEdge(I);
	    J.addEdge(K);
	    J.addEdge(O);
	    K.addEdge(C);
	    K.addEdge(J);
	    K.addEdge(L);
	    L.addEdge(D);
	    L.addEdge(F);
	    L.addEdge(K);
	    L.addEdge(Q);
	    M.addEdge(F);
	    M.addEdge(H);
	    M.addEdge(S);
	    N.addEdge(I);
	    N.addEdge(O);
	    O.addEdge(J);
	    O.addEdge(N);
	    O.addEdge(P);
	    P.addEdge(O);
	    P.addEdge(Q);
	    Q.addEdge(L);
	    Q.addEdge(P);
	    Q.addEdge(R);
	    R.addEdge(Q);
	    R.addEdge(S);
	    S.addEdge(M);
	    S.addEdge(R);
	    S.addEdge(T);
	    T.addEdge(H);
	    T.addEdge(S);
	    List<GraphVertex> Graph = new ArrayList<GraphVertex>();
	    Graph.add(A);
	    assert(Graphs.is_any_placement_feasible(Graph));    
	}
	
	@Test
	public void test_cyclic_graph() {
		GraphVertex A = new GraphVertex();
		GraphVertex B = new GraphVertex();
		GraphVertex C = new GraphVertex();
		GraphVertex D = new GraphVertex();
		GraphVertex E = new GraphVertex();
		GraphVertex F = new GraphVertex();
		GraphVertex G = new GraphVertex();
	    A.edges.add(B);
	    A.edges.add(C);
	    B.edges.add(A);
	    B.edges.add(D);
	    B.edges.add(E);
	    C.edges.add(A);
	    C.edges.add(F);
	    C.edges.add(G);
	    D.edges.add(B);
	    E.edges.add(B);
	    F.edges.add(C);
	    G.edges.add(C);
	    List<GraphVertex> Graph = new ArrayList<GraphVertex>();
	    Graph.add(A);
	    // Test case 1: non-cyclic graph
	    //assertEquals(Graphs.is_graph_2_exists(Graph), false);
	    // Test case 2: add a new edge to make the graph cyclic
	    F.edges.add(G);
	    G.edges.add(F);
	    //assert(Graphs.is_graph_2_exists(Graph));
	    // Test case 3: not connected for all 
	    //assert(Graphs.is_graph_2_for_all(Graph) == false);
	    // Test case 4: add edges to make the graph connected for all
	    B.edges.add(C);
	    C.edges.add(B);
	    D.edges.add(E);
	    E.edges.add(D);
	    F.edges.add(G);
	    G.edges.add(F);
	    assert(Graphs.is_graph_2_for_all(Graph));
	}
	
	@Test
	public void test_small_graph() {
		GraphVertex A = new GraphVertex();
		GraphVertex B = new GraphVertex();
		GraphVertex C = new GraphVertex();
		A.edges.add(B);
		A.edges.add(C);
		B.edges.add(A);
		//B.edges.add(C);
		C.edges.add(A);
		//C.edges.add(B);
		List<GraphVertex> Graph = new ArrayList<GraphVertex>();
		Graph.add(A);
		assert(Graphs.is_graph_2_for_all(Graph) == false);
	}
}
