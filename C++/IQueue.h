#ifndef IQUEUE_H
#define IQUEUE_H

#include <cmath>

class IQueue {
    private:
        unsigned val, size;
    public:
        IQueue(): val(0), size(0) {}
        void enqueue(const unsigned &x) {
            val = val * 10 + x;
            ++size;
        }
        unsigned dequeue(void) {
            if (size) {
                unsigned ret = 0;
                ret = val/pow(10.0, size -1);
                val -= pow(10.0, size-1) * ret;
                size--;
                return ret;
            }
            throw length_error("empty queue");
        }
        bool empty() {
            return size == 0;
        }
};

#endif