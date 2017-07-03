#ifndef LRUCACHE_H
#define LRUCACHE_H

#include <unordered_map>
#include <list>

using namespace std;

template <typename KeyType, typename ValueType, size_t capacity>
class LRUCache {
    private:
        typedef unordered_map<KeyType, pair<typename list<KeyType>::iterator, ValueType>> Table;
        Table cache;
        list<KeyType> data;
        // Move the most recent accessed item to the front
        void moveToFront(const KeyType &key, const typename Table::iterator &it) {
            data.erase(it->second.first);
            data.emplace_front(key);
            it->second.first = data.begin();
        }
    public:
        const bool lookup(const KeyType &key, ValueType *value) {
            /*cout << "cache.size() =" << cache.size() << endl;
            cout << "key = " << key << endl;
            for (auto p: cache) {
                cout << p.first << endl;
            }*/
            auto it = cache.find(key);            
            if (it == cache.end()) {
                return false;
            }
            *value = it->second.second;
            moveToFront(key, it);
            return true;
        }
        
        void insert(const KeyType &key, const ValueType &value) {
            auto it = cache.find(key);
            if (it != cache.end()) {
                moveToFront(key, it);
            } 
            else {
                // Remove the least recently used
                if (cache.size() == capacity) {
                    cache.erase(data.back());
                    data.pop_back();
                }
                data.emplace_front(key);
                pair<typename list<KeyType>::iterator, ValueType> p(data.begin(), value); 
                cache.emplace(key, p);
            }
        }
        
        const bool erase(const KeyType &key) {
            auto it = cache.find(key);
            if (it == cache.end()) {
                return false;
            }
            data.erase(it->second.first);
            cache.erase(it);
            return true;
        }
};

#endif