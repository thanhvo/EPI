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

template <typename T>
int count_len(shared_ptr<node_t<T>> L) {
    int len = 0;
    while(L) {
        len++;
        L = L->next;
    }
    return len;
}

template <typename T>
void advance_list_by_k(shared_ptr<node_t<T>> &L, int k) {
    while (k--)
        L = L->next;
}

template <typename T>
shared_ptr<node_t<T>> overlapping_no_cycle_lists(
    shared_ptr<node_t<T>> L1, shared_ptr<node_t<T>> L2) {
    int L1_len = count_len<T>(L1);
    int L2_len = count_len<T>(L2);
    advance_list_by_k(L1_len > L2_len ? L1 : L2, abs(L1_len - L2_len));
    while(L1 && L2 && L1 != L2) {
        L1 = L1->next;
        L2 = L2->next;
    }
    return L1;
}

template <typename T>
shared_ptr<node_t<T>> overlapping_lists(
    shared_ptr<node_t<T>> L1, shared_ptr<node_t<T>> L2) {
    shared_ptr<node_t<T>> s1 = has_cycle(L1), s2 = has_cycle(L2);
    if (!s1 && !s2) {
        return overlapping_no_cycle_lists(L1, L2);
    } else if (s1 && s2) {
        shared_ptr<node_t<T>> temp = s2;
        do {
            temp = temp->next;            
        } while(temp != s1 && temp != s2);
        return temp == s1 ? s1 : nullptr;
    }
    return nullptr;
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

template <typename T>
shared_ptr<node_t<T>> even_odd_merge(const shared_ptr<node_t<T>> &L) {
    shared_ptr<node_t<T>> odd = L ? L->next : nullptr;
    shared_ptr<node_t<T>> odd_curr = odd;
    shared_ptr<node_t<T>> pre_even_curr = nullptr, even_curr = L;
    while (even_curr && odd_curr) {
        even_curr->next = odd_curr->next;
        pre_even_curr = even_curr;
        even_curr = even_curr->next;
        if (even_curr) {
            odd_curr->next = even_curr->next;
            odd_curr = odd_curr->next;
        }
    }
    if (even_curr) {
        pre_even_curr = even_curr;
    }
    if (pre_even_curr) {
        pre_even_curr->next = odd;
    }
    return L;
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

template <typename T>
void remove_kth_last(shared_ptr<node_t<T>> &L, const int &k) {
    shared_ptr<node_t<T>> ahead = L;
    int num = k;
    while(ahead && num) {
        num--;
        ahead = ahead->next;
    }
    if (num) {
        throw length_error("Not enough node in the list");
    }
    shared_ptr<node_t<T>> pre = nullptr, curr = L;
    while (ahead) {
        pre = curr;
        curr = curr->next;
        ahead = ahead->next;
    }
    if (pre) 
        pre->next = curr->next;
    else 
        L = curr->next;
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

template <typename T>
shared_ptr<node_t<T>> reverse_linked_lists_recursively(
    const shared_ptr<node_t<T>> &head) {
    if (!head || !head->next)
        return head;
    shared_ptr<node_t<T>> new_head = reverse_linked_lists_recursively(head->next);
    head->next->next = head;
    head->next = nullptr;
    return new_head;
}

template<typename T>
shared_ptr<node_t<T>> reverse_linked_lists(
    const shared_ptr<node_t<T>> &head
) {
    shared_ptr<node_t<T>> prev = nullptr, curr = head;
    while (curr) {
        shared_ptr<node_t<T>> temp = curr->next;
        curr->next = prev;
        prev = curr;
        curr = temp;
    }
    return prev;    
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

template <typename T>
bool is_linked_list_a_palindrome( shared_ptr<node_t<T>> L) {
    shared_ptr<node_t<T>> slow = L, fast = L;
    while (fast) {
        fast = fast->next;
        if (fast) {
            fast = fast->next;
            slow = slow->next;
        }
    }
    shared_ptr<node_t<T>> reverse = reverse_linked_lists(slow);
    while (reverse && L) {
        if (reverse->data != L->data)
            return false;
        reverse = reverse->next;
        L = L->next;
    }
    return true;
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

template <typename T>
void connect_a_next_to_b_advance_a(
    shared_ptr <node_t<T>> &a,
    const shared_ptr <node_t<T>> &b
) {
    shared_ptr<node_t<T>> temp = a->next;
    a->next = b;
    a = temp;
}

template <typename T>
shared_ptr<node_t<T>> zipping_linked_lists(
    const shared_ptr<node_t<T>> &L
) {
    shared_ptr<node_t<T>> slow = L, fast = L, pre_slow = nullptr;
    while (fast) {
        fast = fast->next;
        if (fast) {
            pre_slow = slow;
            fast = fast->next;
            slow = slow->next;
        }        
    }
    if (!pre_slow) 
        return L;
    pre_slow->next = nullptr;
    shared_ptr<node_t<T>> reverse = reverse_linked_lists<T>(slow);
    shared_ptr<node_t<T>> curr = L;
    while (curr && reverse) {
        connect_a_next_to_b_advance_a(curr, reverse);
        if (curr) 
            connect_a_next_to_b_advance_a(reverse, curr);
    }
    return L;
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
	return 0;
}
