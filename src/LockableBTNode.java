
public class LockableBTNode<T> extends BTNode<T> {
	private boolean locked;
	private int numChildrenLocks;
	
	public LockableBTNode(T __data) {
		super(__data);
		locked = false;
		numChildrenLocks = 0;
	}
	
	public boolean isLock() {
		return locked;
	}
	
	public void lock() {
		if (numChildrenLocks == 0 && locked == false) {
			LockableBTNode<T> n = (LockableBTNode<T>)parent;
			while (n != null) {
				if (n.locked) {
					return;
				}
				n = (LockableBTNode<T>)n.parent;
			}
			locked = true;
			n = (LockableBTNode<T>)parent;
			while (n != null) {
				++n.numChildrenLocks;
				n = (LockableBTNode<T>)n.parent;
			}
		}
	}
	
	public void unlock() {
		if (locked) {
			locked = false;
			LockableBTNode<T> n = (LockableBTNode<T>)parent;
			while (n != null) {
				--n.numChildrenLocks;
				n = (LockableBTNode<T>)n.parent;
			}
		}
	}	
	

}
