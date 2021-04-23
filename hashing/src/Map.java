public interface Map<Key, Value> {
  boolean isEmpty();

  int size();

  void put(Key key, Value value);

  Value get(Key key);

  boolean contains(Key key);

  String toString();

  int collisions();                    // returns the total number of collisions in all secondary tables
}

