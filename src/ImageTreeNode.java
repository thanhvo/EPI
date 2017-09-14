import java.util.*;

public class ImageTreeNode {
	public int node_num; //stores the number of nodes in its subtree
	ImagePoint lower_left, upper_right;	
	// Stores the SW, NW, NE, and SE rectangles if color is mixed
	List<ImageTreeNode> children;
	public ImageTreeNode() {
		node_num = 1;
		children = new ArrayList<ImageTreeNode>();
	}
	
	public ImageTreeNode(int node_num, ImagePoint lower_left, ImagePoint upper_right) {
		this.node_num = node_num;
		this.lower_left = lower_left;
		this.upper_right = upper_right;
		children = new ArrayList<ImageTreeNode>();
	}
	
	private void print_with_prefix(String prefix) {
        System.out.println( prefix + node_num + "\t(" + lower_left.i + "," + lower_left.j + ")\t(" + upper_right.i + "," + upper_right.j + ")" );
        for (ImageTreeNode child: children) {
            if (child != null && child.node_num > 0)
            	child.print_with_prefix(prefix + "\t");
        }
    }
    void print() {
        print_with_prefix("");
    }
}
