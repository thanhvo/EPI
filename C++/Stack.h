#ifndef STACK_H
#define STACK_H

#include <stack>
#include <stdexcept>

using namespace std;

template <typename T>
class Stack {
    private:
        stack<pair<T,T>> s;
    public:
        Stack() {}
        
        const bool empty(void) const {
            return s.empty();            
        }
        
        const T &max(void) const {
            if (empty() == false) {
                return s.top().second;
            }
            throw length_error("Emty stack");
        }
        
        T pop(void) {
            if (empty() == false) {
                T ret = s.top().first;
                s.pop();
                return ret;
            }
            throw length_error("Empty stack");
        }
        
        void push(const T &x) {
            s.emplace(x, std::max(x, empty()? x: s.top().second));
        }
};

#endif