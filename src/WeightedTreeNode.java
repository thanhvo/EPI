import java.util.*;

public class WeightedTreeNode {
	List<Pair<WeightedTreeNode,Double>> edges;
	
	public WeightedTreeNode() {
		edges = null;
	}
	
	public void addChild(WeightedTreeNode child, double edge) {
		if (edges == null) {
			edges = new ArrayList<Pair<WeightedTreeNode, Double>>();
		}
		edges.add(new Pair(child,edge));
	}
}
