#ifndef QUEUE_H
#define QUEUE_H

#include <vector>

using namespace std;

template<typename T>
class CircularQueue {
    private:
        size_t head, tail, count;
        vector<T> data;
    public:
        CircularQueue(const size_t &cap = 8): head(0), tail(0), count(0), data(vector<int>(cap)) {}
        
        void enqueue(const T &x) {
            if (count == data.size()) {
                data.resize(data.size() << 1);
                // Update the new vector
                if (tail <= head) {
                    tail = tail + (data.size()>>1);
                }
                for (size_t i = data.size() >> 1; i < tail; i++) {
                    data[i] = data[i%(data.size() >> 1)];
                }
            }
            data[tail] = x;
            tail = (tail + 1) % data.size();
            count++;
        }
        
        T dequeue(void) {
            if (count) {
                --count;
                T ret = data[head];
                head = (head + 1) % data.size();
                return ret;
            }
            throw length_error("empty queue");
        }
        
        const size_t &size(void) const {
            return count;
        }
        
        const bool empty() const {
            return count == 0;
        }
};

#endif