#include <string>
#include <cassert>
#include <iostream>
#include <sstream>
#include <iterator>
#include "HashUtil.h"
#include "Plane.h"
#include "LRUCache.h"

void test_find_closest_petition() {
    vector<string> s = {"All", "work", "and", "no", "play", "makes", "for", "no", "work", "no", "fun", "and", "no", "results"};
    assert(find_closest_repetition(s) == 2);
}

void test_anagrams() {
    string values = "dears dares reads impinged impeding ogre gore ergo rowdies dowries weirdos pole lope"
                " review viewer ginkgos gingkos strange garnets aides ideas aside gutsiest gustiest"
                " damson nomads tacit attic edges sedge pulsing plugins endows snowed cones scone sated stead dates";
    stringstream ss(values);
    istream_iterator<string> begin(ss);
    istream_iterator<string> end;
    vector<string> v(begin, end);
    find_anagrams(v);
}

void test_find_line_with_most_points() {
    vector<Point> v = {{1,2}, {2,3}, {3,4}, {0,0},{1,1},{5,4}};
    Line line = find_line_with_most_points(v);
    cout << line.slope.first << " " << line.slope.second << " " 
         << line.intercept.first << " " << line.intercept.second << endl; 
}

void test_search_frequent_items() {
    string values = "a b c a b c a d e a b";
    istringstream ss(values);
    vector<string> frequent_items = search_frequent_items(ss, 4);
    for (string s: frequent_items)
        cout << s << " ";
    cout << endl;
}

void test_find_smallest_subarray_covering_subset() {
    vector<string> A = {"a", "b", "c", "d", "a","b","e","f","a","b","d"};
    vector<string> Q = {"a", "b", "d"};
    pair<int, int> p1 = find_smallest_subarray_covering_subset(A, Q);
    cout << p1.first << " " << p1.second << endl;
    string values = "a b c d a b e f a b d";
    istringstream ss(values);
    pair<int, int> p2 = improved_find_smallest_subarray_covering_subset(ss, Q);
    cout << p2.first << " " << p2.second << endl;
    pair<int, int> p3 = find_smallest_sequentially_covering_subset(A, Q);
    cout << p3.first << " " << p3.second << endl;
}

void test_LRU_Cache() {
    LRUCache<string, int, 10> book_cache;
    book_cache.insert("Pride and Prejudice", 10);
    book_cache.insert("Wuthering Heights", 20);
    book_cache.insert("Sherlock Homes", 30);
    book_cache.insert("Harry Potter", 50);
    book_cache.insert("The Lord of the Rings", 50);
    book_cache.insert("The Hobbit", 30);
    book_cache.insert("Ivanhoe", 10);
    book_cache.insert("Life of Pi", 20);
    book_cache.insert("Romeo and Juliet", 10);
    book_cache.insert("Hamlet", 10);
    int price;
    assert(book_cache.lookup("Pride and Prejudice", &price));    
    assert(price == 10);
    assert(book_cache.lookup("Twilight", &price) == false);
    book_cache.insert("Twilight", 20);
    book_cache.insert("The hunger games", 20);
    assert(book_cache.lookup("Pride and Prejudice", &price));
    assert(price == 10);
    assert(book_cache.lookup("Wuthering Heights", &price) == false);
    assert(book_cache.lookup("Twilight", &price));
    assert(price == 20);
}

void test_hash() {
    test_find_closest_petition();
    test_anagrams();
    test_find_line_with_most_points();
    test_search_frequent_items();
    test_find_smallest_subarray_covering_subset();
    test_LRU_Cache();
}
