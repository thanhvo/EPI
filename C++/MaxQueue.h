#ifndef MAXQUEUE_H
#define MAXQUEUE_H

#include <queue>
#include <deque>

template <typename T>
class MaxQueue {
    private:
        queue<T> Q;
        deque<T> D;
    public:
        void enqueue(const T &x) {
            Q.emplace(x);
            while (D.empty() == false && D.back() < x) {
                D.pop_back();
            }
            D.emplace_back(x);
        }
        
        T dequeue(void) {
            if (Q.empty() == false) {
                T ret = Q.front();
                if (ret == D.front()) {
                    D.pop_front();
                }
                Q.pop();
                return ret;
            }
            throw length_error("empty queue");
        }
        
        const T &max(void) const {
            if(D.empty() == false) {
                return D.front();
            }
            throw length_error("empty queue");
        }
        
        const bool empty() {
            return Q.empty();
        }
    
};

#endif