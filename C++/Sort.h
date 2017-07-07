#ifndef SORT_H
#define SORT_H

#include <string>
#include <vector>
#include <iostream>
#include <fstream>
#include <algorithm>
#include <unordered_map>

using namespace std;

template <typename T>
void indirect_sort(const string &input_file_name, const string &output_file_name) {
    // Store file records into A
    ifstream ifs(input_file_name.c_str());
    vector<T> A;
    T x;
    while (ifs >> x) {
        A.emplace_back(x);
    }
    // Intialize P
    vector<T*> P;
    for (T &a : A) {
        P.emplace_back(&a);
    }
    
    // Indirect sort file
    sort(P.begin(), P.end(), [](const T* a, const T* b) -> bool {
        return *a < *b;
    });
    
    // Output file
    ofstream ofs(output_file_name.c_str());
    for (const T* p : P) {
        ofs << *p << endl;
    }
}

template <typename KeyType>
class Person {
    public:
        KeyType key;
        string name;
        Person (KeyType __key, string __name): key(__key), name(__name) {}
};

template <typename KeyType>
void counting_sort(vector<Person<KeyType>> &people) {
    unordered_map<KeyType, int> key_to_count;
    for (const Person<KeyType> &p : people) {
        ++key_to_count[p.key];
    }
    unordered_map<KeyType, int> key_to_offset;
    int offset = 0;
    for (const auto p : key_to_count) {
        key_to_offset[p.first] = offset;
        offset += p.second;
    }
    while (key_to_offset.size()) {
        auto from = key_to_offset.begin();
        auto to = key_to_offset.find(people[from->second].key);
        swap(people[from->second], people[to->second]);
        // Use key_to_count to see when we are finished with a particular key
        if (--key_to_count[to->first]) {
            ++to->second;
        } else {
            key_to_offset.erase(to);
        }
    }
}

template <typename T>
vector<T> intersect_arrs(const vector<T> &A, const vector<T> &B) {
    vector<T> intersect;
    unsigned int i =0, j= 0;
    while (i < A.size() && j < B.size()) {
        if (A[i] == B[j] && (i == 0 || A[i] != A[i-1])) {
            intersect.emplace_back(A[i]);
            ++i, ++j;            
        } else if (A[i] < B[j]) {
            ++i;
        } else { // A[i] > B[j]
            ++j;
        }
    }
    return intersect;
}

template <typename TimeType>
class Interval {
    public:
        TimeType start, finish;
        Interval(TimeType __start, TimeType __finish): start(__start), finish(__finish) {}
};

template <typename TimeType>
class EndPoint {
    public: 
        TimeType time;
        bool isStart;
        const bool operator<(const EndPoint &e) const {
            return time != e.time ? time < e.time : (isStart && !e.isStart);
        }
};

template <typename TimeType>
int find_max_concurrent_events(const vector<Interval<TimeType>> &A) {
    // Build the endpoint array
    vector<EndPoint<TimeType>> E;
    for (const Interval<TimeType> &i: A) {
        E.emplace_back(EndPoint<TimeType>{i.start, true});
        E.emplace_back(EndPoint<TimeType>{i.finish, false});
    }
    // Sort the end point array according to the time
    sort(E.begin(), E.end());
    // Find the maximum number of events overlapped
    int max_count = 0, count = 0;
    for (const EndPoint<TimeType> &e: E) {
        if (e.isStart) {
            max_count = max(++count, max_count);
        } else {
            --count;
        }
    }
    return max_count;
}


#endif