#include <iostream>
#include "MaxQueue.h"
#include "TrafficElement.h"

using namespace std;

void traffic_volumes(const vector<TrafficElement> &A, const int &w) {
        MaxQueue<TrafficElement> Q;
        for (unsigned int i = 0; i < A.size(); i++) {
            Q.enqueue(A[i]);
            while(A[i].time - Q.front().time > w) {
                Q.dequeue();
            }
            cout << "Max after inserting " << i << " is " << Q.max().volume << endl;
        }
}
