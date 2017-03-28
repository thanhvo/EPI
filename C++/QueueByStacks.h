#ifndef QUEUEBYSTACKS_H
#define QUEUEBYSTACKS_H

#include <stack>

template <typename T>
class QueueByStacks {
    private:
        stack<T> A,B;
    public:
        void enqueue(const T &x) {
            A.emplace(x);
        }
        
        T dequeue(void) {
            if (B.empty()) {
                while (!A.empty()) {
                    B.emplace(A.top());
                    A.pop();
                }
            }
            if (!B.empty()) {
                T ret = B.top();
                B.pop();
                return ret;
            }
            throw length_error("empty queue");
        }
        
        bool empty() {
            return A.empty() && B.empty();
        }
};

#endif