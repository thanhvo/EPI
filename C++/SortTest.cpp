#include "Sort.h"
#include "SortTest.h"
#include <iostream>

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

void test_sort() {
    test_indirect_sort();
    test_counting_test();
}