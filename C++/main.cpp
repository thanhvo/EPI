#include <iostream>
#include <memory>
#include <cassert>
#include "Node.h"
#include "LinkedList.h"

using namespace std;

template<typename T>
void print(const shared_ptr<node_t<T>> &head) {
    shared_ptr<node_t<T>> node = head;
    while(node) {
        cout << node->data << "\t";
        node = node->next;
    }
    cout << endl;
}

void test_merge_lists() {
    shared_ptr<node_t<int> > zero(make_shared<node_t<int>>(0));
    shared_ptr<node_t<int> > one(make_shared<node_t<int>>(1));
    shared_ptr<node_t<int> > two(make_shared<node_t<int>>(2));
    shared_ptr<node_t<int> > three(make_shared<node_t<int>>(3));
    shared_ptr<node_t<int> > four(make_shared<node_t<int>>(4));
    shared_ptr<node_t<int> > five(make_shared<node_t<int>>(5));
    zero->next = two;
    two->next = three;
    one->next = four;
    four->next = five;
    print(merge_sorted_linked_lists(zero, one));
}

void test_has_cycle() {
    shared_ptr<node_t<int> > head(make_shared<node_t<int>>(0));
    shared_ptr<node_t<int> > one(make_shared<node_t<int>>(1));
    shared_ptr<node_t<int> > two(make_shared<node_t<int>>(2));
    shared_ptr<node_t<int> > three(make_shared<node_t<int>>(3));
    shared_ptr<node_t<int> > four(make_shared<node_t<int>>(4));
    shared_ptr<node_t<int> > five(make_shared<node_t<int>>(5));
    head->next = one;
    one->next = two;
    two->next = three;
    three->next = four;
    four->next = five;
    five->next = three;    
    shared_ptr<node_t<int> > n = has_cycle(head);    
    assert(n->data == 3);
}

void test_median() {
    shared_ptr<node_t<int> > zero(make_shared<node_t<int>>(0));
    shared_ptr<node_t<int> > one(make_shared<node_t<int>>(1));
    shared_ptr<node_t<int> > two(make_shared<node_t<int>>(2));
    shared_ptr<node_t<int> > three(make_shared<node_t<int>>(3));
    shared_ptr<node_t<int> > four(make_shared<node_t<int>>(4));
    shared_ptr<node_t<int> > five(make_shared<node_t<int>>(5));
    zero->next = one;
    one->next = two;
    two->next = three;
    three->next = four;
    four->next = five;
    five->next = zero;
    assert(find_median_sorted_circular_linked_list(zero)==2.5);
    assert(find_median_sorted_circular_linked_list(one)==2.5);
    assert(find_median_sorted_circular_linked_list(four)==2.5);    
}

void test_overlapping_lists() {
    
    shared_ptr<node_t<int> > zero(make_shared<node_t<int>>(0));
    shared_ptr<node_t<int> > one(make_shared<node_t<int>>(1));
    shared_ptr<node_t<int> > two(make_shared<node_t<int>>(2));
    shared_ptr<node_t<int> > three(make_shared<node_t<int>>(3));
    shared_ptr<node_t<int> > four(make_shared<node_t<int>>(4));
    shared_ptr<node_t<int> > five(make_shared<node_t<int>>(5));
    shared_ptr<node_t<int> > six(make_shared<node_t<int>>(6));
    shared_ptr<node_t<int> > seven(make_shared<node_t<int>>(7));
    shared_ptr<node_t<int> > eight(make_shared<node_t<int>>(8));
    shared_ptr<node_t<int> > nine(make_shared<node_t<int>>(9));
    
    /* Test case 1: 2 lists with no cycle */
    zero->next = two;
    one->next = three;
    two->next = four;
    three->next = four;
    four->next = five;
    five->next = six;
    assert(overlapping_no_cycle_lists(zero, one)->data == 4);
    
    /* Test case 2: 2 lists both have cycles */
    zero->next = one;
    one->next = five;
    five->next = six;
    six->next = seven;
    seven->next = eight;
    eight->next = nine;
    nine->next = five;
    two->next = three;
    three->next = nine;
    assert(overlapping_lists(zero, one)->data == 5);
    
}

void test_even_odd_merge() {
    shared_ptr<node_t<int> > zero(make_shared<node_t<int>>(0));
    shared_ptr<node_t<int> > one(make_shared<node_t<int>>(1));
    shared_ptr<node_t<int> > two(make_shared<node_t<int>>(2));
    shared_ptr<node_t<int> > three(make_shared<node_t<int>>(3));
    shared_ptr<node_t<int> > four(make_shared<node_t<int>>(4));
    shared_ptr<node_t<int> > five(make_shared<node_t<int>>(5));
    shared_ptr<node_t<int> > six(make_shared<node_t<int>>(6));
    shared_ptr<node_t<int> > seven(make_shared<node_t<int>>(7));
    shared_ptr<node_t<int> > eight(make_shared<node_t<int>>(8));
    shared_ptr<node_t<int> > nine(make_shared<node_t<int>>(9));
    shared_ptr<node_t<int> > ten(make_shared<node_t<int>>(10));
    
    /* Test case 1: even number of nodes */
    zero->next = one;
    one->next = two;
    two->next = three;
    three->next = four;
    four->next = five;
    five->next = six;
    six->next = seven;
    seven->next = eight;
    eight->next = nine;
    print(even_odd_merge(zero));
    
    /* Test case 2: odd number of nodes */
    zero->next = one;
    one->next = two;
    two->next = three;
    three->next = four;
    four->next = five;
    five->next = six;
    six->next = seven;
    seven->next = eight;
    eight->next = nine;
    nine->next = ten;
    print(even_odd_merge(zero));
}

void test_remove_kth_last() {
    shared_ptr<node_t<int> > zero(make_shared<node_t<int>>(0));
    shared_ptr<node_t<int> > one(make_shared<node_t<int>>(1));
    shared_ptr<node_t<int> > two(make_shared<node_t<int>>(2));
    shared_ptr<node_t<int> > three(make_shared<node_t<int>>(3));
    shared_ptr<node_t<int> > four(make_shared<node_t<int>>(4));
    shared_ptr<node_t<int> > five(make_shared<node_t<int>>(5));
    shared_ptr<node_t<int> > six(make_shared<node_t<int>>(6));
    shared_ptr<node_t<int> > seven(make_shared<node_t<int>>(7));
    shared_ptr<node_t<int> > eight(make_shared<node_t<int>>(8));
    shared_ptr<node_t<int> > nine(make_shared<node_t<int>>(9));
    shared_ptr<node_t<int> > ten(make_shared<node_t<int>>(10));
    
    zero->next = one;
    one->next = two;
    two->next = three;
    three->next = four;
    four->next = five;
    five->next = six;
    six->next = seven;
    seven->next = eight;
    eight->next = nine;
    nine->next = ten;
    
    print(zero);
    remove_kth_last(zero, 5);
    print(zero);
}

void test_reverse_linked_lists() {
    cout << "Testing reverse linked lists" << endl;
    shared_ptr<node_t<int> > zero(make_shared<node_t<int>>(0));
    shared_ptr<node_t<int> > one(make_shared<node_t<int>>(1));
    shared_ptr<node_t<int> > two(make_shared<node_t<int>>(2));
    shared_ptr<node_t<int> > three(make_shared<node_t<int>>(3));
    shared_ptr<node_t<int> > four(make_shared<node_t<int>>(4));
    shared_ptr<node_t<int> > five(make_shared<node_t<int>>(5));
    shared_ptr<node_t<int> > six(make_shared<node_t<int>>(6));
    shared_ptr<node_t<int> > seven(make_shared<node_t<int>>(7));
    shared_ptr<node_t<int> > eight(make_shared<node_t<int>>(8));
    shared_ptr<node_t<int> > nine(make_shared<node_t<int>>(9));
    shared_ptr<node_t<int> > ten(make_shared<node_t<int>>(10));
    
    zero->next = one;
    one->next = two;
    two->next = three;
    three->next = four;
    four->next = five;
    five->next = six;
    six->next = seven;
    seven->next = eight;
    eight->next = nine;
    nine->next = ten;
    
    print(zero);
    shared_ptr<node_t<int>> new_head = reverse_linked_lists(zero);
    print(new_head);
    print(reverse_linked_lists_recursively(new_head));
    
}

void test_palindorme() {
    cout << "Testing palindrome lists" << endl;
    shared_ptr<node_t<int> > zero(make_shared<node_t<int>>(0));
    shared_ptr<node_t<int> > one(make_shared<node_t<int>>(1));
    shared_ptr<node_t<int> > two(make_shared<node_t<int>>(2));
    shared_ptr<node_t<int> > three(make_shared<node_t<int>>(3));
    shared_ptr<node_t<int> > four(make_shared<node_t<int>>(4));
    shared_ptr<node_t<int> > five(make_shared<node_t<int>>(5));
    shared_ptr<node_t<int> > four2(make_shared<node_t<int>>(4));
    shared_ptr<node_t<int> > three2(make_shared<node_t<int>>(3));
    shared_ptr<node_t<int> > two2(make_shared<node_t<int>>(2));
    shared_ptr<node_t<int> > one2(make_shared<node_t<int>>(1));
    shared_ptr<node_t<int> > zero2(make_shared<node_t<int>>(0));
    
    zero->next = one;
    one->next = two;
    two->next = three;
    three->next = four;
    four->next = five;
    five->next = four2;
    four2->next = three2;
    three2->next = two2;
    two2->next = one2;
    one2->next = zero2;
    
    /* Test case 1: a palindrome list */
    assert(is_linked_list_a_palindrome(zero));      
}

void test_zipping() {
    cout << "Testing zipping linked lists" << endl;
    shared_ptr<node_t<int> > zero(make_shared<node_t<int>>(0));
    shared_ptr<node_t<int> > one(make_shared<node_t<int>>(1));
    shared_ptr<node_t<int> > two(make_shared<node_t<int>>(2));
    shared_ptr<node_t<int> > three(make_shared<node_t<int>>(3));
    shared_ptr<node_t<int> > four(make_shared<node_t<int>>(4));
    shared_ptr<node_t<int> > five(make_shared<node_t<int>>(5));
    shared_ptr<node_t<int> > six(make_shared<node_t<int>>(6));
    shared_ptr<node_t<int> > seven(make_shared<node_t<int>>(7));
    shared_ptr<node_t<int> > eight(make_shared<node_t<int>>(8));
    shared_ptr<node_t<int> > nine(make_shared<node_t<int>>(9));
    shared_ptr<node_t<int> > ten(make_shared<node_t<int>>(10));
    
    /* Test case 1: even number of nodes */
    zero->next = one;
    one->next = two;
    two->next = three;
    three->next = four;
    four->next = five;
    five->next = six;
    six->next = seven;
    seven->next = eight;
    eight->next = nine;
    print(zipping_linked_lists(zero));
    
    /* Test case 2: odd number of nodes */
    zero->next = one;
    one->next = two;
    two->next = three;
    three->next = four;
    four->next = five;
    five->next = six;
    six->next = seven;
    seven->next = eight;
    eight->next = nine;
    nine->next = ten;
    print(zipping_linked_lists(zero));
    
}

void test_copy_list() {
    cout << "Testing copy list" << endl;
    shared_ptr<node_t<int> > zero(make_shared<node_t<int>>(0));
    shared_ptr<node_t<int> > one(make_shared<node_t<int>>(1));
    shared_ptr<node_t<int> > two(make_shared<node_t<int>>(2));
    shared_ptr<node_t<int> > three(make_shared<node_t<int>>(3));
    zero->next = one;
    one->next = two;
    two->next = three;
    zero->jump = two;
    one->jump = three;
    two->jump = one;
    three->jump = three;    
    shared_ptr<node_t<int>> cZero = copy_list(zero);
    shared_ptr<node_t<int>> cOne = cZero->next;
    shared_ptr<node_t<int>> cTwo = cOne->next;
    shared_ptr<node_t<int>> cThree = cTwo->next;
    assert(cZero->jump->data == 2);
    assert(cOne->jump->data == 3);
    assert(cTwo->jump->data == 1);
    assert(cThree->jump->data == 3);
    assert(cZero->next->data == 1);
    assert(cOne->next->data == 2);
    assert(cTwo->next->data == 3);
    assert(cThree->next == nullptr);
}

int main(int argc, char **argv)
{
	test_has_cycle();
    test_median();
    test_merge_lists();
    test_overlapping_lists();
    test_even_odd_merge();
    test_remove_kth_last();
    test_reverse_linked_lists();
    test_palindorme();
    test_zipping();
    test_copy_list();
	return 0;
}
