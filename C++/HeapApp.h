#ifndef HEAPAPP_H
#define HEAPAPP_H

#include <queue>
#include <utility>
#include <cmath>
#include <sstream>
#include <string>
#include <iostream>
#include <algorithm>
#include <unordered_set>

using namespace std;

template <typename T> 
class Compare {
    public:
        const bool operator()(const pair<T,int> &lhs, const pair<T,int> &rhs) const {
            return lhs.second < rhs.second;
        }
};

template <typename T>
class HeapStack: public priority_queue<pair<T, int>, vector<pair<T, int>>, Compare<T>> {
    private:
        int order;
        typedef priority_queue<pair<T, int>, vector<pair<T, int>>, Compare<T>> PQ;
    public:
        HeapStack(): order(0) {}
        const T& top() const {
            return PQ::top().first;
        }
        void push(const T& x) {
            PQ::emplace(x, order++);
        }
};

template <typename T>
class HeapQueue: public priority_queue<pair<T, int>, vector<pair<T, int>>, Compare<T>> {
    private:
        int order;
        typedef priority_queue<pair<T, int>, vector<pair<T, int>>, Compare<T>> PQ;
    public:
        HeapQueue(): order(0) {}
        const T &front() const {
            return PQ::top().first;
        }
        void push(const T &x) {
            PQ::emplace(x, order--);      
        }
};

template <typename T>
vector<T> merge_arrays(const vector<vector<T>> &S) {
    priority_queue <pair<T, int>, vector<pair<T, int>>, Compare<T> > min_heap;
    vector<int> S_idx(S.size(), 0);
    for (unsigned int i = 0; i< S.size(); i++) {
        if (S[i].size() > 0) {
            min_heap.emplace(S[i][0], i);
            S_idx[i] = 1;
        }
    }
    vector<T> ret;
    while (!min_heap.empty()) {
        pair<T,int> p = min_heap.top();
        ret.emplace_back(p.first);
        if (S_idx[p.second] < (int)S[p.second].size()) {
            min_heap.emplace(S[p.second][S_idx[p.second]++], p.second);
        }
        min_heap.pop();
    }
    return ret;
}

template <typename T>
vector <T> sort_k_increasing_descreasing_array(const vector<T> &A) {
    // Decompose A into a set of sorted arrays
    vector<vector<T>> S;
    bool is_increasing = true;
    unsigned int start_idx = 0;
    for (unsigned int i=1; i < A.size(); ++i) {
        if ((A[i-1] < A[i] && !is_increasing) || 
            (A[i-1] >= A[i] && is_increasing)) {
                if (is_increasing) {
                    S.emplace_back(A.cbegin() + start_idx, A.cbegin() + i);
                } else {
                    S.emplace_back(A.crbegin() + A.size() - i, A.crbegin() + A.size() - start_idx);
                }
                start_idx = i;
                is_increasing = !is_increasing;
            }
    }
    if (start_idx < A.size()) {
        if (is_increasing) {
            S.emplace_back(A.cbegin() + start_idx, A.cend());
        } else {
            S.emplace_back(A.crbegin(), A.crbegin() + A.size() - start_idx);
        }
    }
    return merge_arrays(S);
}

class Star{
    public: 
        int ID;
        double x,y,z;
        const double distance() const {
            return sqrt(x*x + y*y + z*z);
        }
        const bool operator<(const Star &s) const {
            return distance() < s.distance();
        }
};

vector<Star> find_closest_k_stars(istringstream &sin, const int &k);

class Num {
    public:
        int a_, b_;
        double val_;
        Num(const int &a, const int &b): a_(a), b_(b), val_(a+b*sqrt(2)) {}
        const bool operator<(const Num &n) const {
            return val_ > n.val_;
        }
        const bool operator==(const Num &n) const {
            return a_==n.a_ && b_==n.b_;
        }
};

class HashNum {
    public: 
            const size_t operator() (const Num &n) const {
                return hash<int>()(n.a_) ^ hash<int>()(n.b_);
            }
};

vector<Num> generate_first_k(const int &k);

template <typename T>
void find_kth_largest_stream(istringstream &sin, const int &k) {
    priority_queue<T, vector<T>, greater<T>> min_heap;
    T x;
    for (int i = 0; i < k && sin >> x; i++) {
        min_heap.emplace(x);
        cout << min_heap.top() << endl;
    }
    while (sin >> x) {
        if (min_heap.top() < x) {
            min_heap.pop();
            min_heap.emplace(x);
        }
        cout << min_heap.top() << endl;
    }
}

template <typename T>
void approximate_sort(istringstream &sin, const int &k) {
    priority_queue<T, vector<T>, greater<T>> min_heap;
    T x;
    for (int i = 0; i < k && sin >> x; ++i) {
        min_heap.push(x);
    }
    while (sin >> x) {
        min_heap.push(x);
        cout << min_heap.top() << endl;
        min_heap.pop();
    }
    while(min_heap.size()) {
        cout << min_heap.top() << endl;
        min_heap.pop();
    }
}

template <typename T>
double find_median(vector<T> &A) {
    int half = A.size() >> 1;
    nth_element(A.begin(), A.begin() + half, A.end());
    if (A.size() & 1) {
        return A[half];
    } else {
        T x = A[half];
        nth_element(A.begin(), A.begin() + half -1, A.end());
        return 0.5 * (x + A[half-1]);
    }
}

template <typename T>
class Comp {
    private:
            double m_;
    public:
            Comp(const double &m): m_(m) {};
            const bool operator() (const T &a, const T &b) const {
                return fabs(a - m_) < fabs(b - m_);
            }
};

template <typename T>
vector <T> find_k_closest_to_median(vector<T> A, const int &k) {
    nth_element(A.begin(), A.begin() + k -1, A.end(), Comp<T>{find_median(A)});
    return {A.cbegin(), A.cbegin() + k};
}

template <typename T>
void online_median(istringstream &sin) {
    priority_queue<T, vector<T>, greater<T>> H;
    priority_queue<T, vector<T>, less<T>> L;
    T x;
    while(sin >> x) {
        if (L.empty() == false && x > L.top()) {
            H.emplace(x);
        } else {
            L.emplace(x);
        }
        if (H.size() > L.size() + 1) {
            L.emplace(H.top());
            H.pop();
        }
        if (H.size() == L.size()) {
            cout << 0.5 * (H.top() + L.top()) << endl;
        } else {
            cout << (H.size() > L.size() ? H.top() : L.top()) << endl;
        }
    }
}

#endif