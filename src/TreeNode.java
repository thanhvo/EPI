import java.util.*;

public class TreeNode {
	List<Pair<TreeNode,Double>> edges;
	
	public TreeNode() {
		edges = null;
	}
	
	public void addChild(TreeNode child, double edge) {
		if (edges == null) {
			edges = new ArrayList<Pair<TreeNode, Double>>();
		}
		edges.add(new Pair(child,edge));
	}
}
