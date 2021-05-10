import java.util.NoSuchElementException;

public class SkipListLinkedC<Key extends Comparable<Key>, Value> implements SkipList<Key, Value> {

	private final double P = 1 / Math.E; // https://www.sciencedirect.com/science/article/pii/030439759400296U
	private LinkedNode<Key, Value> start;
	private LinkedNode<Key, Value> terminus;
	private int n;

	// initialize an empty skiplist
	public SkipListLinkedC() {
		this.start = new LinkedNode<Key, Value>(LinkedNode.Type.root, 1);
		this.terminus = new LinkedNode<Key, Value>(LinkedNode.Type.cap, 1);
		this.start.next = terminus;
		this.n = 0;
	}

	// initialize skiplist from array of keys/vals
	public SkipListLinkedC(Key[] keys, Value[] vals) { // initialize from a list of keys and values
		this();
		this.insert(keys, vals);
	}

	// returns the corresponding value for specified key, if any
	public Value get(Key key) {
		return search(key).getValue();
	}

	// deletes a node if inside the list
	public void delete(Key key) {
		LinkedNode<Key, Value> top = search(key);
		while (top != null && top.getLevel() > 0) {
			LinkedNode<Key, Value> tmp = top.bottom;
			top.dettach();
			top = tmp;
		}
		n--;
	}

	public boolean contains(Key key) {
		try {
			search(key);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	// main insertion method for new nodes
	public void insert(Key key, Value val) {
		try {
			LinkedNode<Key, Value> beginning = search(key); //returns the topmost matching value (if any)
			beginning.replace(val); //updates that nodes children
			return;
		} catch (NoSuchElementException e) {
		}

		int l = randomLevel();
		increaseEnds(l);
		LinkedNode<Key, Value> levelNode = getLevel(l);
		LinkedNode<Key, Value> tmp = new LinkedNode<Key, Value>(LinkedNode.Type.cap, levelNode.getLevel());

		while (levelNode != null && levelNode.getLevel() >= 0) {
			LinkedNode<Key, Value> node = new LinkedNode<Key, Value>(key, val, LinkedNode.Type.node, levelNode.getLevel());
			LinkedNode<Key, Value> backNode = levelNode;
			LinkedNode<Key, Value> frontNode = terminus;

			while (backNode.next != null && backNode.next.isLess(node) && backNode.next.getType() != LinkedNode.Type.cap) {
				backNode = backNode.next;
			}

			if (backNode.next != null)
				frontNode = backNode.next;

			backNode.next = node;
			node.prev = backNode;

			frontNode.prev = node;
			node.next = frontNode;

			levelNode = levelNode.bottom;
			if (tmp.getType() == LinkedNode.Type.cap) { //tmp is placeholder to enable stitching levels of nodes together.
				tmp = node;
				continue;
			}
			tmp.bottom = node;
			tmp = node;
		}
		n++;
	}

	// insert arrays of keys and values
	public void insert(Key[] keys, Value[] vals) {
		if (keys.length != vals.length)
			throw new IndexOutOfBoundsException("Invalid Input! Lengths must be the same.");
		for (int i = 0; i < keys.length; i++) {
			insert(keys[i], vals[i]);
		}
	}

	// returns if skiplist is empty
	public boolean isEmpty() {
		return n == 0;
	}

	// number of elements in skiplist
	public int size() {
		return n;
	}

	@Override
	public String toString() {
		String s = "";
		for (LinkedNode<Key, Value> level = start; level != null; level = level.bottom) {
			LinkedNode<Key, Value> currentNode = level;
			while (currentNode.getType() != LinkedNode.Type.cap) {
				s += "{" + currentNode.getKey() + ", " + currentNode.getValue() + "} -->";
				currentNode = currentNode.next;
			}
			s += "{" + currentNode.getKey() + ", " + currentNode.getValue() + "}";
			s += "\n";
		}
		return s;
	}

	// PRIVATE

	// search helper for get and delete methods
	private LinkedNode<Key, Value> search(Key key) {
		LinkedNode<Key, Value> levelNode = start;
		while (levelNode.next != null) {
			if (levelNode.next.getType() == LinkedNode.Type.cap) {
				if (levelNode.getLevel() == 1)
					break;
				levelNode = levelNode.bottom;
			} else if (levelNode.next.isLess(key)) {
				levelNode = levelNode.next;
			} else if (levelNode.next.equals(key)) {
				return levelNode.next;
			} else {
				levelNode = levelNode.bottom == null ? levelNode.next : levelNode.bottom;
			}
		}
		throw new NoSuchElementException("No Value for " + key);
	}

	// get the start node at the generated level for inserting a node
	private LinkedNode<Key, Value> getLevel(int l) {
		LinkedNode<Key, Value> level = start; //this will always be start node at max level
		while (level.getLevel() > l)
			level = level.bottom; //keep going down until we hit our entry point }
		return level;
	}

	// generates a new random level for an insert node
	private int randomLevel() {
		if (Math.random() < P)
			return 1 + randomLevel();
		return 1; // Min number is 1
	}

	// builds up the start and terminus nodes to match the generated height of a new node
	private void increaseEnds(int l) {
		while (l > start.getLevel()) {
			LinkedNode<Key, Value> startLevel = new LinkedNode<Key, Value>(start, null, null, null, null, LinkedNode.Type.root, start.getLevel() + 1);
			LinkedNode<Key, Value> termLevel = new LinkedNode<Key, Value>(terminus, startLevel, null, null, null, LinkedNode.Type.cap, startLevel.getLevel() + 1);
			startLevel.next = (termLevel);
			start = startLevel;
			terminus = termLevel;
		}
	}
}
