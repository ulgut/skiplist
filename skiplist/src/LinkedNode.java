public class LinkedNode<Key extends Comparable<Key>, Value> {
	private LinkedNode<Key, Value> top;
	private LinkedNode<Key, Value> bottom;
	private LinkedNode<Key, Value> prev;
	private LinkedNode<Key, Value> next;

	private Key key;
	private Value value;

	enum Type {
		root,
		node,
		cap
	}

	private LinkedNode.Type type;

	public LinkedNode(LinkedNode<Key, Value> top, LinkedNode<Key, Value> bottom, LinkedNode<Key, Value> prev, LinkedNode<Key, Value> next, Key key, Value value, Type t) {
		this.top = top;
		this.bottom = bottom;
		this.prev = prev;
		this.next = next;
		this.key = key;
		this.value = value;
		this.type = t;
	}

	public LinkedNode(Type t) {
		this(null, null, null, null, null, null, t);
	}

	public LinkedNode(Key key, Value val, Type t) {
		this(null, null, null, null, key, val, t);
	}

	public LinkedNode<Key, Value> getBottom() {
		return this.bottom;
	}

	public LinkedNode<Key, Value> getNext() {
		return this.next;
	}

	public Key getKey() {
		return this.key;
	}

	public Value getValue() {
		return this.value;
	}

	public void setNext(LinkedNode<Key, Value> next) {
		this.next = next;
	}

	public void setPrev(LinkedNode<Key, Value> prev) {
		this.prev = prev;
	}

	public void setTop(LinkedNode<Key, Value> top) {
		this.top = top;
	}

	public void setBottom(LinkedNode<Key, Value> bottom) {
		this.bottom = bottom;
	}

	public Type getType() {
		return this.type;
	}

	public LinkedNode getPrev() {
		return this.prev;
	}

	public boolean isLess(LinkedNode<Key, Value> other) {
		if (this.type == LinkedNode.Type.root)
			return true;
		else if (this.type == LinkedNode.Type.cap)
			return false;
		else
			return this.key.compareTo(other.key) < 0;
	}

	private void wipe() {
		this.top = null;
		this.prev = null;
		this.bottom = null;
		this.next = null;
		this.value = null;
		this.key = null;
	}

	public void dettach() {
		LinkedNode<Key, Value> left = prev;
		LinkedNode<Key, Value> right = next;
		left.setNext(right);
		right.setPrev(left);
		wipe();
	}

	public boolean equals(Key otherKey) {
		if (this.key == null)
			return false;
		else {
			return this.key.compareTo(otherKey) == 0;
		}
	}

	@Override

	public String toString() {
		String s = "";
		s += "{" + this.key + ", " + this.value + ", " + this.type + "}";
		return s;
	}

}
