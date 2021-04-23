public interface OrderedMap<Key extends Comparable<Key>, Value {
  boolean isEmpty();
  int size();
  OrderedMap<Key, Value> put(Key key, Value value);
  Value get(Key key);
  String toString();
}
