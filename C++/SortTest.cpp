#include "Sort.h"
#include "SortTest.h"
#include <iostream>
#include <cassert>

using namespace std;

void test_indirect_sort() {
    string input = "/home/thanh/workspace/EPI/input/A Patriotic Fourth: What Does That Mean Now? - The New York Times.txt";
    string output = "/home/thanh/workspace/EPI/output/A Patriotic Fourth: What Does That Mean Now? - The New York Times.txt";
    indirect_sort<string>(input, output);
}

void test_counting_test() {
    vector<Person<int>> people = {{2,"Thanh"}, {3,"ThuNgan"}, {1,"Khanh"}, {2, "Duong"}, {3,"Hoa"}, {1,"Phuong"}};
    counting_sort(people);
    for ( Person<int> p: people) {
        cout << p.name << endl;
    }
}

void test_intersect_arrs() {
    vector<int> a= {0,2,3,4,6,8,10};
    vector<int> b = {0,1,3,4,7,10};
    vector<int> c = intersect_arrs(a, b);
    for (auto i : c)
        cout << i << " ";
    cout << endl;
}

void test_find_max_concurrent_events() {
    vector<Simple_Interval<int>> events = {{1,5}, {6,10}, {11,13}, {14,15}, {2,7}, {8,9}, {12,15}, {4,5}, {9,17}};
    assert(find_max_concurrent_events(events) == 3);   
}

void test_union_intervals() { 
    vector<Interval<int>> intervals = {{{1, true, false},{5, false, true}},{{7, true, true},{10, false, true}}, {{11, true, true}, {13, false, true}},
                                       {{14, true, true}, {15, false, true}}, {{2, true, true}, {4, false, true}}, {{8, true, true}, {9, false, true}},
                                       {{12, true, true}, {15, false, true}}, {{4, true, true}, {5, false, true}}, {{9, true, true}, {17, false, true}}};
    vector<Interval<int>> uni = union_intervals(intervals);
    for (Interval<int> i : uni) {
        cout << i << " ";
    }
    cout << endl;
}

void test_find_minimum_visits() {
    vector<Simple_Interval<int>> intervals = {{1,5}, {6,10}, {11,13}, {14,15}, {2,7}, {8,9}, {12,15}, {4,5}, {9,17}};
    vector<int> visits = find_minimum_visits(intervals);
    for (int visit: visits) {
        cout << visit << " ";
    }
    cout << endl;
}

void test_sort() {
    test_indirect_sort();
    test_counting_test();
    test_intersect_arrs();
    test_find_max_concurrent_events();
    test_union_intervals();
    test_find_minimum_visits();
}