public class SkipListLinkedC<Key extends Comparable<Key>, Value> implements SkipList<Key, Value> {

	private final double P = 1 / Math.E; // https://www.sciencedirect.com/science/article/pii/030439759400296U
	private LinkedNode<Key, Value> start;
	private LinkedNode<Key, Value> terminus;
	private int levels;
	private int n;


	public SkipListLinkedC() {
		this.start = new LinkedNode<Key, Value>(LinkedNode.Type.root);
		this.terminus = new LinkedNode<Key, Value>(LinkedNode.Type.cap);
		this.start.setNext(terminus);
		this.levels = 1;
		this.n = 0;
	}

	private LinkedNode<Key, Value> search(Key key) {
		if (n == 0) {
			return null; // fast path
		}
		LinkedNode<Key, Value> node = new LinkedNode<Key, Value>(key, null, LinkedNode.Type.node);
		LinkedNode<Key, Value> levelNode = start;
		while (levelNode.getNext() != null) {
			if (levelNode.getNext().getType() == LinkedNode.Type.cap) {
				levelNode = levelNode.getBottom();
			} else if (levelNode.getNext().isLess(node)) {
				levelNode = levelNode.getNext();
			} else if (levelNode.getNext().equals(node.getKey())) {
				return levelNode.getNext();
			} else {
				if (levelNode.getBottom() != null) {
					levelNode = levelNode.getBottom();
				} else {
					levelNode = levelNode.getNext();
				}
			}
		}
		return null;
	}


	public Value get(Key key) {
		LinkedNode<Key, Value> result = search(key);
		if (result != null)
			return result.getValue();
		return null;
	}

	public void delete(Key key) {
		LinkedNode<Key, Value> node = new LinkedNode<>(key, null, LinkedNode.Type.node); //more memory efficient
		LinkedNode<Key, Value> levelNode = start;
		while (levelNode != null && levelNode.getNext() != null) {
			if (levelNode.getNext().getType() == LinkedNode.Type.cap) {
				levelNode = levelNode.getBottom();
			} else if (levelNode.getNext().isLess(node)) {
				levelNode = levelNode.getNext();
			} else if (levelNode.getNext().equals(node.getKey())) {
				levelNode.getNext().dettach();
				levelNode = levelNode.getBottom();
			} else {
				levelNode = levelNode.getBottom() == null ? levelNode.getNext() : levelNode.getBottom();
			}
		}
		n--;
	}

	public void insert(Key key, Value val) {
		int l = randomLevel();
		increaseEnds(l);

		LinkedNode<Key, Value> levelNode = getLevel(l);
		LinkedNode<Key, Value> tmp = new LinkedNode<>(LinkedNode.Type.cap);
		int i = l;

		while (i >= 1) {
			LinkedNode<Key, Value> node = new LinkedNode<>(key, val, LinkedNode.Type.node);
			LinkedNode<Key, Value> backNode = levelNode;
			LinkedNode<Key, Value> frontNode = terminus;

			while (backNode.getNext() != null && backNode.getNext().isLess(node) && backNode.getNext().getType() != LinkedNode.Type.cap) {
				backNode = backNode.getNext();
			}

			if (backNode.getNext() != null) {
				frontNode = backNode.getNext();
			}

			backNode.setNext(node);
			node.setPrev(backNode);

			frontNode.setPrev(node);
			node.setNext(frontNode);

			levelNode = levelNode.getBottom();
			if (tmp.getType() == LinkedNode.Type.cap) { //tmp is placeholder to enable stitching levels of nodes together.
				tmp = node;
				i--;
				continue;
			}
			tmp.setBottom(node);
			tmp = node;
			i--;
		}
		n++;
	}

	public void insert(Key[] keys, Value[] vals) {
		if (keys.length != vals.length)
			throw new IndexOutOfBoundsException("Invalid Input! Lengths must be the same.");
		else
			for (int i = 0; i < keys.length; i++) {
				insert(keys[i], vals[i]);
			}
	}

	public SkipListLinkedC(Key[] keys, Value[] vals) { // initialize from a list of keys and values
		this();
		this.insert(keys, vals);
	}

	public boolean isEmpty() {
		return n == 0;
	}

	public void wipe() {
		//implement?
	}

	public boolean contains(Key key) {
		return search(key) != null;
	}

	public int size() {
		return n;
	}


	// private methods

	private LinkedNode<Key, Value> getLevel(int l) {
		LinkedNode<Key, Value> level = start; //this will always be start node at max level
		int maxLevel = levels;
		while (maxLevel > l) {
			level = level.getBottom(); //keep going down until we hit our entry point
			maxLevel--;
		}
		return level;
	}

	private int randomLevel() {
		if (Math.random() < P)
			return 1 + randomLevel();
		else
			return 1; // Min number is 1
	}

	@Override

	public String toString() {
		String s = "";
		int i = levels;
		for (LinkedNode<Key, Value> level = start; i >= 1; level = level.getBottom()) {
			LinkedNode<Key, Value> currentNode = level;
			while (currentNode.getType() != LinkedNode.Type.cap) {
				s += "{" + currentNode.getKey() + ", " + currentNode.getValue() + "} --> ";
				currentNode = currentNode.getNext();
			}
			s += "{" + currentNode.getKey() + ", " + currentNode.getValue() + "}";
			s += "\n";
			i--;
		}
		return s;
	}

	private void increaseEnds(int l) {
		while (l > levels) {
			LinkedNode<Key, Value> startLevel = new LinkedNode<Key, Value>(start, null, null, null, null, LinkedNode.Type.root);
			LinkedNode<Key, Value> termLevel = new LinkedNode<Key, Value>(terminus, startLevel, null, null, null, LinkedNode.Type.cap);
			startLevel.setNext(termLevel);
			start = startLevel;
			terminus = termLevel;
			levels++;
		}
	}


	// testing/main method

	public static void main(String[] args) {
		Integer[] keys = {93, 91, 49, 69, 80, 99, 100, 101};
		String[] vals = {"Turing", "Julie", "Jacob", "James", "Jack", "Julian", "Djikstra", "Jesse"};
		System.out.println("TESTING INSERT\n");
		SkipListLinkedC<Integer, String> sl = new SkipListLinkedC<Integer, String>(keys, vals);
		System.out.println(sl);
		System.out.println("\nEND TESTING INSERT");

		System.out.println("TESTING GET\n");
		for (int i = 0; i < keys.length; i++) {
			System.out.println(vals[i].equals(sl.get(keys[i])) ? ("TEST " + keys[i] + " PASSED") : ("TEST " + keys[i] + " FAILED!!!!!!!!!!!!!!!!!!"));
		}
		System.out.println("\nTESTING GET END");

		System.out.println("DELETING");
		for (int i = 0; i < keys.length; i++) {
			sl.delete(keys[i]);
		}
		System.out.println("CONFIRMING DELETE\n");
		System.out.println((sl.size() == 0) + ", Size: " + sl.size());
		for (int i = 0; i < keys.length; i++) {
			if (sl.contains(keys[i]))
				System.out.println("FAILED DELETE WITH KEY: " + keys[i]);
			else
				System.out.println("PASSED DELETE WITH KEY: " + keys[i]);
		}
		System.out.println("\nCONFIRMING DELETE END");
		System.out.println(sl);
	}
}
