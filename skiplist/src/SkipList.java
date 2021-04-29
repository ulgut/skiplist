public interface SkipList<Key extends Comparable<Key>, Value> {
	Value get(Key key);

	void delete(Key key);

	void insert(Key key, Value val);

	String toString();

	boolean isEmpty();

	int size();
}
