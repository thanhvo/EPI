import java.util.*;

public class TreeNode {
	public List<TreeNode> children;
	
	public TreeNode() {
		children = new ArrayList<TreeNode>();
	}
	
	public void addChild(TreeNode child) {
		children.add(child);
	}
}
