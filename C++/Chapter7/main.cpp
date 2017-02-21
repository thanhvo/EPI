#include <iostream>
#include <memory>
#include <cassert>

using namespace std;

template <typename T>
class node_t {
    public:
        T data;
        node_t(T __data) {
            data = __data;
            next = nullptr;
        }
        shared_ptr <node_t<T> > next;        
};

template<typename T>
void print(const shared_ptr<node_t<T>> &head) {
    shared_ptr<node_t<T>> node = head;
    while(node) {
        cout << node->data << "\t";
        node = node->next;
    }
    cout << endl;
}

template <typename T>
void append_node(shared_ptr<node_t<T>> &head,
                shared_ptr<node_t<T>> &tail,
                shared_ptr<node_t<T>> &n) {
    head ? tail->next = n : head = n;
    tail = n;    
}

template<typename T>
void append_node_and_advance(
    shared_ptr<node_t<T>> &head,
    shared_ptr<node_t<T>> &tail,
    shared_ptr<node_t<T>> &n) {
    append_node(head, tail, n);
    n = n->next;
}

template<typename T>
shared_ptr<node_t<T>> merge_sorted_linked_lists(
    shared_ptr<node_t<T>> F,
    shared_ptr<node_t<T>> L) {
    shared_ptr<node_t<T>> sorted_head = nullptr, tail = nullptr;
    while(F && L ) 
        append_node_and_advance(sorted_head, tail, F->data < L->data ? F : L);            
    if (F)
        append_node(sorted_head, tail, F);
    if (L)
        append_node(sorted_head, tail, L);
    return sorted_head;
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

template <typename T> shared_ptr<node_t<T> > has_cycle(
    const shared_ptr<node_t<T> > &head) {
    shared_ptr<node_t<T> > fast = head, slow = head;
    while(slow && slow->next && fast && fast->next && fast->next->next) {
        slow = slow->next, fast = fast->next->next;
        if (slow == fast) {
            int cycle_len = 0;
            do {
                ++cycle_len;
                fast = fast->next;
            } while(slow != fast);
            slow = head, fast = head;
            while(cycle_len--) {
                fast = fast->next;
            }
            while(slow != fast) {
                slow = slow->next;
                fast = fast->next;
            }
            return slow;
        }
    }
    return nullptr;    
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

template <typename T>
double find_median_sorted_circular_linked_list(
    const shared_ptr<node_t<T>> &r_node) {
    if (!r_node)
        return 0.0;
    shared_ptr<node_t<T>> curr = r_node, start = r_node;
    int count = 0;
    bool is_identical = true;
    do {
        if (curr->data != curr->next->data) {
            is_identical = false;
        }
        ++count; 
        curr = curr->next;
        if (start->data <= start->next->data)
            start = start->next;
    } while(curr != r_node);
    if (is_identical == true)
        return curr->data;
    start = start->next;
    for (int i = 0; i < (count -1) >> 1; i++) 
        start = start->next;
    return count & 1 ? start->data : 0.5 * (start->data + start->next->data);
            
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

int main(int argc, char **argv)
{
	test_has_cycle();
    test_median();
    test_merge_lists();
	return 0;
}
