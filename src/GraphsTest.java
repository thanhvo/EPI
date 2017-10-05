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
	
	@Test 
	public void test_transitive_closure() {
		GraphVertex A = new GraphVertex();
		GraphVertex B = new GraphVertex();
		GraphVertex C = new GraphVertex();
		A.edges.add(B);
		A.edges.add(C);
		B.edges.add(C);
		List<GraphVertex> Graph = Arrays.asList(A, B, C);
		Graphs.transitive_closure(Graph);
		System.out.println("Extended contacts of A");
		for (GraphVertex vertex : A.extendedContacts) {
			System.out.print(vertex + " ");
		}
		System.out.println();
		System.out.println("Extended contacts of B");
		for (GraphVertex vertex : B.extendedContacts) {
			System.out.print(vertex + " ");
		}
		System.out.println();
		System.out.println("Extended contacts of C");
		for (GraphVertex vertex : C.extendedContacts) {
			System.out.print(vertex + " ");
		}
		System.out.println();
	}
	
	@Test
	public void test_graph_constraints() {
		ArrayList<Constraint> E = new ArrayList<Constraint>
			(Arrays.asList(new Constraint(0,1), new Constraint(1,2), new Constraint(3,4), new Constraint(3,5)));
	    ArrayList<Constraint> I = new ArrayList<Constraint>
			(Arrays.asList(new Constraint(0,3), new Constraint(1,5), new Constraint(2,4)));
	    assert(Graphs.are_constraints_satisfied(E, I));
	    E.add(new Constraint(2,3));
	    assert(!Graphs.are_constraints_satisfied(E, I));
	}
	
	@Test
	public void test_longest_path() {
		GraphVertex A = new GraphVertex();
		GraphVertex B = new GraphVertex();
		GraphVertex C = new GraphVertex();
		GraphVertex D = new GraphVertex();
		GraphVertex E = new GraphVertex();
		GraphVertex F = new GraphVertex();
		GraphVertex G = new GraphVertex();
	    A.edges = Arrays.asList(B, C, D);
	    B.edges = Arrays.asList(C, D);
	    C.edges.add(E);
	    D.edges.add(E);
	    E.edges.add(G);
	    F.edges.add(G);
	    List<GraphVertex> Graph = Arrays.asList(A, B, C, D, E, F, G);
	    assert(Graphs.find_longest_path(Graph) == 5);    
	}
	
	@Test
	public void test_dijstra() {
		System.out.println("Dijkstra algorithm to find the shortest path");
	    GraphVertex A = new GraphVertex(0),B = new GraphVertex(1), C = new GraphVertex(2), D = new GraphVertex(3),
	    		E = new GraphVertex(4), F = new GraphVertex(5), G = new GraphVertex(6), H = new GraphVertex(7),
	    		I = new GraphVertex(8), J = new GraphVertex(9), K = new GraphVertex(10), L = new GraphVertex(11),
	    		M = new GraphVertex(12), N = new GraphVertex(13);    
	    A.addEdge(C, 2);
	    A.addEdge(B, 3);
	    B.addEdge(A, 4);
	    B.addEdge(K, 1);
	    C.addEdge(E, 8);
	    D.addEdge(C, 5);
	    D.addEdge(H, 5);
	    E.addEdge(D, 7);
	    F.addEdge(G, 6);
	    G.addEdge(F, 7);
	    G.addEdge(H, 4);
	    I.addEdge(J, 6);
	    J.addEdge(F, 1);
	    J.addEdge(L, 7);
	    K.addEdge(I, 1);
	    L.addEdge(I, 9);
	    M.addEdge(N, 5);
	    N.addEdge(M, 12);
	    List<GraphVertex> Graph = Arrays.asList(A,B,C,D,E,F,G,H,I,J,K,L,M,N);
	    Graphs.Dijkstra_shortest_path(Graph, A, H);
	    //Graphs.Dijkstra_shortest_path(Graph, B, H);
	}
	
	@Test
	public void test_find_best_proposal() {
		System.out.println("Testing Floyd Warshall");
	    HighwaySection[] H = {new HighwaySection(1, 2, 1), new HighwaySection(1, 4, 2), new HighwaySection(2, 3, 2), 
	    					  new HighwaySection(4, 3, 3), new HighwaySection(3, 5, 1), new HighwaySection(5, 6, 2), 
	                          new HighwaySection(5, 8, 1), new HighwaySection(8, 7, 3), new HighwaySection(6, 7, 1)};
	    HighwaySection[] P = {new HighwaySection(4, 6, 3), new HighwaySection(3, 7, 1), new HighwaySection(2, 5, 2)};
	    HighwaySection p = Graphs.find_best_proposal(H, P, 1, 7, 9);
	    p.print();
	}
}
