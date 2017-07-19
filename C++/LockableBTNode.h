#ifndef LOCKABLEBTNODE_H
#define LOCKABLEBTNODE_H

#include <memory>
#include "BTNode.h"

using namespace std;

template<typename T>
class LockableBTNode: public BTNode<T> {
    private:
		bool locked;
		int numChildrenLocks;
    
    public:
        LockableBTNode(T __data): BTNode<T>(__data) {
            locked = false;
			numChildrenLocks = 0;
        }
        
        const bool &isLock(void) const {
			return locked;
		}
		
		void lock(void) {
			if (numChildrenLocks == 0 && locked == false) {
				shared_ptr<LockableBTNode<T>> n = static_pointer_cast<LockableBTNode<T>>(this->parent);
				/* Check if any ancestor is locked. If so, return right away */
				while (n) {
					if (n->locked) {
						return;
					}
					n = static_pointer_cast<LockableBTNode<T>>(n->parent);
				}
				locked = true;
				n = static_pointer_cast<LockableBTNode<T>>(this->parent);
				while (n) {
					++n->numChildrenLocks;
					n = static_pointer_cast<LockableBTNode<T>>(n->parent);
				}
			}
		}
		
		void unlock(void) {
			if (locked) {
				locked = false;
				shared_ptr<LockableBTNode<T>> n = static_pointer_cast<LockableBTNode<T>>(this->parent);
				while (n) {
					--n->numChildrenLocks;
					n = static_pointer_cast<LockableBTNode<T>>(n->parent);
				}
			}
		}
};

#endif
