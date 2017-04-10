
public class BTNode<T> {
	private boolean locked;
	private int numChildrenLocks;
	
	T data;
	BTNode<T> left;
	BTNode<T> right;
	BTNode<T> parent;
	public int size;
	public boolean visited = false;
	
	public BTNode(T __data) {
		data = __data;
		locked = false;
		numChildrenLocks = 0;
		left = null;
		right = null;
		parent = null;
	}
	
	public BTNode(T __data, BTNode<T> __left, BTNode<T> __right) {
		data = __data;
		left = __left;
		right = __right;
	}
	
	public boolean isLock() {
		return locked;
	}
	
	public void lock() {
		if (numChildrenLocks == 0 && locked == false) {
			BTNode<T> n = parent;
			while (n != null) {
				if (n.locked) {
					return;
				}
				n = n.parent;
			}
			locked = true;
			n = parent;
			while (n != null) {
				++n.numChildrenLocks;
				n = n.parent;
			}
		}
	}
	
	public void unlock() {
		if (locked) {
			locked = false;
			BTNode<T> n = parent;
			while (n != null) {
				--n.numChildrenLocks;
				n = n.parent;
			}
		}
	}
	
	
}
