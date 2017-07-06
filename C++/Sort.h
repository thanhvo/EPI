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


#endif