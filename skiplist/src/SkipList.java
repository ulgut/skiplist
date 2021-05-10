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
