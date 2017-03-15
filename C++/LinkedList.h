#include "Node.h"

using namespace std;

template <typename T>
void append_node(shared_ptr<node_t<T>> &head,
                shared_ptr<node_t<T>> &tail,
                shared_ptr<node_t<T>> &n
) {
    head ? tail->next = n : head = n;
    tail = n;    
}

template<typename T>
void append_node_and_advance(
    shared_ptr<node_t<T>> &head,
    shared_ptr<node_t<T>> &tail,
    shared_ptr<node_t<T>> &n
) {
    append_node(head, tail, n);
    n = n->next;
}

template<typename T>
shared_ptr<node_t<T>> merge_sorted_linked_lists(
    shared_ptr<node_t<T>> F,
    shared_ptr<node_t<T>> L
) {
    shared_ptr<node_t<T>> sorted_head = nullptr, tail = nullptr;
    while(F && L ) 
        append_node_and_advance(sorted_head, tail, F->data < L->data ? F : L);            
    if (F)
        append_node(sorted_head, tail, F);
    if (L)
        append_node(sorted_head, tail, L);
    return sorted_head;
}

template <typename T> shared_ptr<node_t<T> > has_cycle(
    const shared_ptr<node_t<T> > &head
) {
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

template <typename T>
double find_median_sorted_circular_linked_list(
    const shared_ptr<node_t<T>> &r_node
) {
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

template <typename T>
int count_len(shared_ptr<node_t<T>> L) 
{
    int len = 0;
    while(L) {
        len++;
        L = L->next;
    }
    return len;
}

template <typename T>
void advance_list_by_k(shared_ptr<node_t<T>> &L, int k) 
{
    while (k--)
        L = L->next;
}

template <typename T>
shared_ptr<node_t<T>> overlapping_no_cycle_lists(
    shared_ptr<node_t<T>> L1, shared_ptr<node_t<T>> L2
) {
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
    shared_ptr<node_t<T>> L1, shared_ptr<node_t<T>> L2
) {
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

template <typename T>
shared_ptr<node_t<T>> even_odd_merge(const shared_ptr<node_t<T>> &L) 
{
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

template <typename T>
void remove_kth_last(shared_ptr<node_t<T>> &L, const int &k) 
{
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

template <typename T>
shared_ptr<node_t<T>> reverse_linked_lists_recursively(
    const shared_ptr<node_t<T>> &head
) {
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

template <typename T>
bool is_linked_list_a_palindrome( shared_ptr<node_t<T>> L) 
{
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

template <typename T>
shared_ptr<node_t<T>> copy_list(
    const shared_ptr<node_t<T>> &L
) {
    if (!L)
        return nullptr;
    shared_ptr<node_t<T>> p = L;
    /* Phase 1: Create a list of copied nodes */
    while(p) {
        auto temp = shared_ptr<node_t<T>> (new node_t<T>(p->data, p->next, nullptr));
        p->next = temp;
        p = temp->next;
    }
    /* Phase 2: set the jump pointers for copied list */
    p = L;
    while (p) {
        if (p->jump) {
            p->next->jump = p->jump->next;
        }
        p = p->next->next;
    }
    /* Phase 3: set the original list back to original state */
    p = L;
    shared_ptr<node_t<T>> copied = p->next;
    while (p->next) {
        shared_ptr<node_t<T>> temp = p->next;
        p->next = temp->next;
        p = temp;
    }
    return copied;
}
