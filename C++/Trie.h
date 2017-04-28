#ifndef TRIE_H
#define TRIE_H

#include <unordered_map>
#include <memory>

using namespace std;

class Trie {
    private:
        class TrieNode {
            public:
                bool isString;
                unordered_map<char, shared_ptr<TrieNode>> l; 
                TrieNode(bool __isString): isString(__isString) {}
        };
        shared_ptr<TrieNode> root;
    public:
        Trie(): root(shared_ptr<TrieNode>(new TrieNode(false))) {}
        bool insert(const string &s) {
            shared_ptr<TrieNode> p = root;
            for (const char &c: s) {
                if (p->l.find(c) == p->l.cend()) {
                    p->l[c] = shared_ptr<TrieNode>(new TrieNode {false});
                }
                p = p->l[c];
            }
            if (p->isString) {
                return false;
            } else {
                p->isString = true;
                return true;
            }
        }
        
        string getShortestUniquePrefix(const string &s) {
            shared_ptr<TrieNode> p = root;
            string prefix;
            for (const char &c: s) {
                prefix += c;
                if (p->l.find(c) == p->l.cend()) {
                    return prefix;
                }
                p = p->l[c];
            }
            return {};
        }
};

string find_shorest_prefix(const string &s, const unordered_set<string> &D) {
    Trie T;
    for (const string &word: D) {
        T.insert(word);
    }
    return T.getShortestUniquePrefix(s);
}

#endif