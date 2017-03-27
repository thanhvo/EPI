import java.util.*;

public class QueueUtil {
	
	public static<T extends Comparable> void printBinaryTree(BTNode<T> root) {
		if (root == null)
			return;
		Queue<BTNode<T>> queue = new LinkedList<BTNode<T>>();
		queue.add(root);
		while(!queue.isEmpty()) {
			BTNode<T> node = (BTNode<T>)queue.remove();
			if (node.left != null) {
				queue.add(node.left);
			}
			if (node.right != null) {
				queue.add(node.right);
			}
			System.out.print(node.data + " ");
		}	
	}
	
}
