#include <string>
#include <sstream>
#include <stack>
#include <iostream>
#include "StackApp.h"
#include "BSTNode.h"

using namespace std;

int eval_RPN(const string &s) {
    stack<int> eval_stack;
    stringstream ss(s);
    string symbol;
    while(getline(ss,symbol,',')) {
        if (symbol == "+" || symbol == "-" || symbol == "*" || symbol == "/") {
            if (eval_stack.empty()) {
                throw invalid_argument("Invalid Input");
            }
            int y = eval_stack.top();
            eval_stack.pop();
            if (eval_stack.empty())
                throw invalid_argument("Invalid Input");
            int x = eval_stack.top();
            eval_stack.pop();
            switch(symbol.front()) {
                case '+':
                    eval_stack.emplace(x+y);
                    break;
                case '-':
                    eval_stack.emplace(x-y);
                    break;
                case '*':
                    eval_stack.emplace(x*y);
                    break;
                case '/':
                    eval_stack.emplace(x/y);
                    break;
            } 
        } else {
            eval_stack.emplace(stoi(symbol));
        }
    }
    int ret = eval_stack.top();
    eval_stack.pop();
    if (!eval_stack.empty())
        throw invalid_argument("Invalid Input");
    return ret;
}

