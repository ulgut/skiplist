# SkipList
### Writers: Cole Dumas and Jesse Tuglu
### Repo Contains: Linked and Sequential Implementations of Immutable Skiplists + <a href="https://jessetuglu.github.io/skiplist">Typescript Visualization</a>
![alt text](https://github.com/jessetuglu/skiplist/blob/main/sl.png?raw=true)
### Java:

- In this repository are two implementations of the skiplist.
    - First, a sequential version, which uses 1 node per <key, value> pair that contains arraylists of pointers to the corresponding previous and next nodes at each level.
        - This implementation is immutable, so once a node is created it's key and value cannot be changed.
        - Any new <key, value> pair that matches the key of an already existing node will be rejected.
    - Secondly, a linked implementation where each <key, value> pair has an _i levels_ number of nodes, one at each level, each with their own pointers to the corresponding previous and next nodes.
        - This implementation is mutable.
        - Any new <key, value> pair that matches the key will override the existing value.
#### SkipList Interface
```java
    public interface SkipList<Key extends Comparable<Key>, Value> {
        Value get(Key key);

        void delete(Key key);

        void insert(Key key, Value val);

        void insert(Key[] keys, Value[] vals);

        String toString();

        boolean isEmpty();

        boolean contains(Key key);

        int size();
    }
```
### Typescript (<a href="https://jessetuglu.github.io/skiplist">Typescript Visualization</a>):
See readme inside /vis for more information and running the project locally.

