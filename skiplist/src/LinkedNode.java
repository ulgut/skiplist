public class LinkedNode<Key extends Comparable<Key>, Value> {
	public LinkedNode<Key, Value> bottom;
	public LinkedNode<Key, Value> prev;
	public LinkedNode<Key, Value> next;

	private Key key;
	private Value value;
	private LinkedNode.Type type;
	private int level;

	enum Type {
		root,
		node,
		cap

	}

	public LinkedNode(LinkedNode<Key, Value> bottom, LinkedNode<Key, Value> prev, LinkedNode<Key, Value> next, Key key, Value value, Type t, int level) {
		this.bottom = bottom;
		this.prev = prev;
		this.next = next;
		this.key = key;
		this.value = value;
		this.type = t;
		this.level = level;
	}

	public LinkedNode(Type t, int level) {
		this(null, null, null, null, null, t, level);
	}

	public LinkedNode(Key key, Value val, Type t, int level) {
		this(null, null, null, key, val, t, level);
	}

	public int getLevel() {
		return this.level;
	}

	public Key getKey() {
		return this.key;
	}

	public Value getValue() {
		return this.value;
	}

	public Type getType() {
		return this.type;
	}

	// compares two nodes
	public boolean isLess(LinkedNode<Key, Value> other) {
		if (this.type == LinkedNode.Type.root)
			return true;
		else if (this.type == LinkedNode.Type.cap)
			return false;
		return this.key.compareTo(other.key) < 0;
	}

	// another node's key
	public boolean isLess(Key otherKey) {
		if (this.type == Type.root)
			return true;
		else if (this.type == Type.cap)
			return false;
		return this.key.compareTo(otherKey) < 0;
	}

	// removes node from skiplist

	public void dettach() {
		LinkedNode<Key, Value> left = prev;
		LinkedNode<Key, Value> right = next;
		left.next = right;
		right.prev = left;
		wipe();
	}
	// checks if two nodes are equal

	public boolean equals(Key otherKey) {
		if (this.key == null)
			return false;
		return this.key.compareTo(otherKey) == 0;
	}

	@Override
	public String toString() {
		String s = "";
		s += "{" + this.key + ", " + this.value + ", " + this.type + "}";
		return s;
	}

	public void replace(Value val) { //replaces a chain
		LinkedNode<Key, Value> tmp = this;
		while (tmp != null) {
			tmp.value = val;
			tmp = tmp.bottom;
		}
	}
	// PRIVATE

	// gives node instance back to garbage
	private void wipe() {
		this.prev = null;
		this.bottom = null;
		this.next = null;
		this.value = null;
		this.key = null;
	}
}
