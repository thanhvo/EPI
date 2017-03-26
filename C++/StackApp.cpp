#include <string>
#include <sstream>
#include <iostream>
#include <stack>
#include <array>
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

void transfer(const int &n, array<stack<int>, 3> &pegs, const int& from, const int &to, const int &use) {
    if (n > 0) {
        transfer(n-1, pegs, from, use, to);
        pegs[to].push(pegs[from].top());
        pegs[from].pop();
        cout << "Move from peg " << from << " to peg " << to << endl;
        transfer(n-1, pegs, use, to, from);        
    }
}

void move_tower_hanoi(const int &n) {
    array<stack<int>, 3> pegs;
    for (int i = n; i >= 1; --i)
        pegs[0].push(i);
    transfer(n, pegs, 0, 1, 2);
}

string normalized_path_names(const string &path) {
    vector<string> s;
    if (path.front() == '/') {
        s.emplace_back("/");
    }
    stringstream ss(path);
    string token;
    while(getline(ss,token,'/')) {
        if (token == "..") {
            if (s.empty() || s.back() == ".") {
                s.emplace_back(token);
            } else {
                if (s.back() == "/") {
                    throw invalid_argument("Path error");
                }
                s.pop_back();
            }
        } else if (token != "." && token != "") {
            for (const char &c: token) {
                if (c != '.' && isalnum(c) == false && c != '_') {
                    throw invalid_argument("Invalid directory name");
                }
            }
            s.emplace_back(token);
        }
    }
    string normalized_path("");
    if (s.empty() == false) {
        auto it = s.cbegin();
        normalized_path += *it++;
        while(it != s.cend()) {
            if (*(it-1) != "/") {
                normalized_path += "/";
            }
            normalized_path += *it++;
        }
    }
    return normalized_path;
}