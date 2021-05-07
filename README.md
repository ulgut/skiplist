# SkipList
### Writers: Cole Dumas and Jesse Tuglu
### Repo Contains: Linked and Sequential Implementations of Skiplists + Visualization
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

   	    int size();
   }
```
#### Sequential
##### SeqNode.java
A sequential skiplist node
##### SkipListSeqC.java
A sequential skiplist
#### Linked
##### LinkedNode.java
A linked skiplist node
##### SkipListLinkedC.java
A linked skiplist
### Typescript Visualization
##### See readme inside /vis
