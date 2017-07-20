import java.util.*;
import java.io.*;

public class BTNode<T> {
	T data;
	BTNode<T> left, right, parent;	
	
	public int size;
	public boolean visited = false;
	
	public boolean printable() {
		return (left == null || left.visited);
	}
	
	public BTNode(T __data) {
		data = __data;		
		left = null;
		right = null;
		parent = null;		
	}
	
	public BTNode(T __data, BTNode<T> __left, BTNode<T> __right) {
		data = __data;
		left = __left;
		right = __right;
		if (left != null)
			left.parent = this;
		if (right != null)
			right.parent = this;
		parent = null;			
	}
	
	public static<T> void printBT_using_visited_field(BTNode<T> root) {
		if (root == null)
			return;
		Stack<BTNode<T>> stack = new Stack<BTNode<T>>();
		stack.push(root);
		root.visited = true;
		while(!stack.isEmpty()) {
			BTNode<T> node = stack.pop();			
			if (node.right != null && !node.right.visited) {
				stack.push(node.right);
				node.right.visited = true;
			}
			if (node.printable()) {				
				System.out.print(node.data + " ");				
			} else {
				stack.push(node);
				if (node.left != null && !node.left.visited) {
					stack.push(node.left);
					node.left.visited = true;
				}
			}
		}
		System.out.println();
	}
	
	public static<T> void printBT(BTNode<T> root) {
		Stack<BTNode<T>> stack = new Stack<BTNode<T>>();
		BTNode<T> curr = root;
		while (!stack.isEmpty() || curr != null) {
			if (curr != null) {
				stack.push(curr);
				curr = curr.left;
			} else {
				curr = stack.pop();
				System.out.print(curr.data + " ");
				curr = curr.right;
			}
		}
		System.out.println();
	}
	
	public void printTree() throws IOException {
        if (right != null) {
            right.printTree(true, "");
        }
        printNodeValue();
        if (left != null) {
            left.printTree(false, "");
        }
    }
    
	private void printNodeValue() throws IOException {
        if (data == null) {
            System.out.print("<null>");
        } else {
            System.out.print(data.toString());
        }
        System.out.print('\n');
    }
    
    // Use string and not stringbuffer on purpose as we need to change the indent at each recursion
    private void printTree(boolean isRight, String indent) throws IOException {
        if (right != null) {
            right.printTree(true, indent + (isRight ? "        " : " |      "));
        }
        System.out.print(indent);
        if (isRight) {
            System.out.print(" /");
        } else {
            System.out.print(" \\");
        }
        System.out.print("----- ");
        printNodeValue();
        if (left != null) {
            left.printTree(false, indent + (isRight ? " |      " : "        "));
        }
    }
}
