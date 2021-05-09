# SkipList
### Writers: Cole Dumas and Jesse Tuglu
### Repo Contains: Linked and Sequential Implementations of Immutable Skiplists + Visualization
![alt text](https://github.com/jessetuglu/skiplist/blob/main/sl.png?raw=true)
### Java:
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
### Typescript:
See readme inside /vis
### Benchmarks:

