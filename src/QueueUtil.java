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
		System.out.println();
	}
	
	public static void traffic_volumes(ArrayList<TrafficElement> A, int w) throws Exception{
		System.out.println("Finding the max traffic volume");
		MaxQueue<TrafficElement> Q = new MaxQueue<TrafficElement>();
		for (int i=0; i< A.size(); i++) {
			Q.enqueue(A.get(i));
			while (A.get(i).time - Q.peek().time > w) {
				Q.dequeue();
			}
			System.out.println("Max after inserting " + i + " is " + Q.max().volume);
		}
	}
	
}
