#ifndef SORT_H
#define SORT_H

#include <string>
#include <vector>
#include <iostream>
#include <fstream>
#include <algorithm>
#include <unordered_map>
#include <set>

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
class Simple_Interval {
    public:
        TimeType start, finish;
        Simple_Interval(TimeType __start, TimeType __finish): start(__start), finish(__finish) {}
};

template <typename TimeType>
class EndPoint {
    public: 
        TimeType time;
        bool isStart;
        bool isClose;
        EndPoint(TimeType __time, bool __isStart, bool __isClose): time(__time), isStart(__isStart), isClose(__isClose){}
        const bool operator<(const EndPoint &e) const {
            return time != e.time ? time < e.time : (isStart && !e.isStart);
        }    
};

template <typename TimeType>
class Interval {
    public:
        EndPoint<TimeType> left, right;
        Interval(EndPoint<TimeType> __left, EndPoint<TimeType> __right): left(__left), right(__right) {}
        const bool operator < (const Interval &i) const {
            return left.time != i.left.time ? left.time < i.left.time : (left.isClose && !i.left.isClose);
        }
};


template <typename TimeType>
ostream& operator<<(ostream &stream, const Interval<TimeType> &i) {
    if (i.left.isClose)
        stream << "[";
    else 
        stream << "(";
    stream << i.left.time << "," << i.right.time; 
    if (i.right.isClose)
        stream << "]";
    else
        stream << ")";
    return stream;
} 

template <typename TimeType>
int find_max_concurrent_events(const vector<Simple_Interval<TimeType>> &A) {
    // Build the endpoint array
    vector<EndPoint<TimeType>> E;
    for (const Simple_Interval<TimeType> &i: A) {
        E.emplace_back(EndPoint<TimeType>{i.start, true, true});
        E.emplace_back(EndPoint<TimeType>{i.finish, false, true});
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


template <typename TimeType>
vector<Interval<TimeType>> union_intervals(vector<Interval<TimeType>> I) {
    // Empty input
    if (I.empty()) {
        return {};
    }
    // Sort intervals according to their left endpoints
    sort(I.begin(), I.end());
    Interval<TimeType> curr(I.front());
    vector<Interval<TimeType>> uni;
    for (unsigned int i = 1; i < I.size(); ++i) {
        if (I[i].left.time < curr.right.time || (I[i].left.time == curr.right.time 
            && (I[i].left.isClose || curr.right.isClose))) {
            if (I[i].right.time > curr.right.time || 
            (I[i].right.time == curr.right.time && I[i].right.isClose)) {
                curr.right = I[i].right;
            }
        } else {
            uni.emplace_back(curr);
            curr = I[i];
        }
    }
    uni.emplace_back(curr);
    return uni;
}

template <typename TimeType>
class LeftComp {
    public: 
        const bool operator() (const Simple_Interval<TimeType> &a, const Simple_Interval<TimeType> &b) const {
            return a.start != b.start ? a.start < b.start : a.finish < b.finish;
        }
};

template <typename TimeType>
class RightComp {
    public:
        const bool operator() (const Simple_Interval<TimeType> &a, const Simple_Interval<TimeType> &b) const {
            return a.finish != b.finish ? a.finish < b.finish : a.start < b.start;
        } 
};

template <typename TimeType>
vector <TimeType> find_minimum_visits(const vector<Simple_Interval<TimeType>> &I) {
    set<Simple_Interval<TimeType>, LeftComp<TimeType>> L;
    set<Simple_Interval<TimeType>, RightComp<TimeType>> R;
    for (const Simple_Interval<TimeType> &i: I) {
        L.emplace(i), R.emplace(i);
    }
    vector <TimeType> S;
    while (L.size() && R.size()) {
        TimeType b = R.cbegin()->finish;
        S.emplace_back(R.begin()->finish);
        // Remove intervals which intersect with R.cbegin()
        auto it = L.cbegin();
        while (it != L.end() && it ->start <= b) {
            R.erase(*it);
            L.erase(it++);
        }
    }
    return S;
}  

#endif
