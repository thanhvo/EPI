#include <memory>
#include <cassert>
#include "QueueTest.h"
#include "QueueApp.h"
#include "BTNode.h"
#include "CircularQueue.h"
#include "IQueue.h"
#include "QueueByStacks.h"
#include "MaxQueue.h"

using namespace std;

void test_print_binary_tree_level_order() {
    shared_ptr<BTNode<int>> n0(make_shared<BTNode<int>>(0));
    shared_ptr<BTNode<int>> n1(make_shared<BTNode<int>>(1));
    shared_ptr<BTNode<int>> n2(make_shared<BTNode<int>>(1));
    shared_ptr<BTNode<int>> n3(make_shared<BTNode<int>>(2));
    shared_ptr<BTNode<int>> n4(make_shared<BTNode<int>>(2));
    shared_ptr<BTNode<int>> n5(make_shared<BTNode<int>>(2));
    shared_ptr<BTNode<int>> n6(make_shared<BTNode<int>>(2));
    shared_ptr<BTNode<int>> n7(make_shared<BTNode<int>>(3));
    shared_ptr<BTNode<int>> n8(make_shared<BTNode<int>>(3));
    shared_ptr<BTNode<int>> n9(make_shared<BTNode<int>>(3));
    shared_ptr<BTNode<int>> n10(make_shared<BTNode<int>>(3));
    n0->left = n1;
    n0->right = n2;
    n1->left = n3;
    n1->right = n4;
    n2->left = n5;
    n2->right = n6;
    n4->left = n7;
    n4->right = n8;
    n6->left = n9;
    n6->right = n10;
    print_binary_tree_level_order(n0);
}

void test_circular_queue() {
    CircularQueue<int> queue(3);
    queue.enqueue(1);
    queue.enqueue(2);
    queue.enqueue(3);
    queue.enqueue(4);
    assert(queue.dequeue() == 1);
    assert(queue.dequeue() == 2);
    assert(queue.dequeue() == 3);
    assert(queue.dequeue() == 4);
    assert(queue.empty());
}

void test_IQueue() {
    IQueue queue;
    queue.enqueue(1);
    queue.enqueue(4);
    queue.enqueue(7);
    queue.enqueue(6);
    assert(queue.dequeue() == 1);
    assert(queue.dequeue() == 4);
    assert(queue.dequeue() == 7);
    assert(queue.dequeue() == 6);
    assert(queue.empty());
}

void test_queue_by_stacks() {
    QueueByStacks<int> queue;
    queue.enqueue(1);
    queue.enqueue(2);
    queue.enqueue(3);
    queue.enqueue(4);
    assert(queue.dequeue() == 1);
    assert(queue.dequeue() == 2);
    assert(queue.dequeue() == 3);
    assert(queue.dequeue() == 4);
    assert(queue.empty());
}

void test_max_queue() {
    MaxQueue<int> queue;
    queue.enqueue(5);
    queue.enqueue(4);
    queue.enqueue(6);
    queue.enqueue(2);
    queue.enqueue(1);
    assert(queue.max() == 6);
    assert(queue.dequeue() == 5);
    assert(queue.max() == 6);
    assert(queue.dequeue() == 4);
    assert(queue.max() == 6);
    assert(queue.dequeue() == 6);
    assert(queue.max() == 2);
    assert(queue.dequeue() == 2);
    assert(queue.max() == 1);
    assert(queue.dequeue() == 1);
    assert(queue.empty());
}

void test_queue() {
    test_print_binary_tree_level_order();
    test_circular_queue();
    test_queue_by_stacks();
    test_max_queue();
}