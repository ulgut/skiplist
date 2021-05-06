import java.util.NoSuchElementException;

public class SkipListLinkedC<Key extends Comparable<Key>, Value> implements SkipList<Key, Value> {

	private final double P = 1 / Math.E; // https://www.sciencedirect.com/science/article/pii/030439759400296U
	private LinkedNode<Key, Value> start;
	private LinkedNode<Key, Value> terminus;
	private int n;


	public SkipListLinkedC() {
		this.start = new LinkedNode<Key, Value>(LinkedNode.Type.root, 1);
		this.terminus = new LinkedNode<Key, Value>(LinkedNode.Type.cap, 1);
		this.start.setNext(terminus);
		this.n = 0;
	}

	private LinkedNode<Key, Value> search(Key key) {
		LinkedNode<Key, Value> node = new LinkedNode<Key, Value>(key, null, LinkedNode.Type.node, 0);
		LinkedNode<Key, Value> levelNode = start;
		while (levelNode.getNext() != null) {
			if (levelNode.getNext().getType() == LinkedNode.Type.cap) {
				if (levelNode.getLevel() == 1)
					break;
				levelNode = levelNode.getBottom();
			} else if (levelNode.getNext().isLess(node)) {
				levelNode = levelNode.getNext();
			} else if (levelNode.getNext().equals(node.getKey())) {
				return levelNode.getNext();
			} else {
				levelNode = levelNode.getBottom() == null ? levelNode.getNext() : levelNode.getBottom();
			}
		}
		throw new NoSuchElementException("No Value for " + key);
	}


	public Value get(Key key) {
		return search(key).getValue();
	}

	public void delete(Key key) {
		LinkedNode<Key, Value> top = search(key);
		while (top != null && top.getLevel() > 0) {
			LinkedNode<Key, Value> tmp = top.getBottom();
			top.dettach();
			top = tmp;
		}
		n--;
	}

	public void insert(Key key, Value val) {
		int l = randomLevel();
		increaseEnds(l);

		LinkedNode<Key, Value> levelNode = getLevel(l);
		LinkedNode<Key, Value> tmp = new LinkedNode<Key, Value>(LinkedNode.Type.cap, levelNode.getLevel());

		while (levelNode != null && levelNode.getLevel() >= 0) {
			LinkedNode<Key, Value> node = new LinkedNode<Key, Value>(key, val, LinkedNode.Type.node, levelNode.getLevel());
			LinkedNode<Key, Value> backNode = levelNode;
			LinkedNode<Key, Value> frontNode = terminus;

			while (backNode.getNext() != null && backNode.getNext().isLess(node) && backNode.getNext().getType() != LinkedNode.Type.cap) {
				backNode = backNode.getNext();
			}

			if (backNode.getNext() != null)
				frontNode = backNode.getNext();

			backNode.setNext(node);
			node.setPrev(backNode);

			frontNode.setPrev(node);
			node.setNext(frontNode);

			levelNode = levelNode.getBottom();
			if (tmp.getType() == LinkedNode.Type.cap) { //tmp is placeholder to enable stitching levels of nodes together.
				tmp = node;
				continue;
			}
			tmp.setBottom(node);
			tmp = node;
		}
		n++;
	}

	public void insert(Key[] keys, Value[] vals) {
		if (keys.length != vals.length)
			throw new IndexOutOfBoundsException("Invalid Input! Lengths must be the same.");
		else
			for (int i = 0; i < keys.length; i++)
				insert(keys[i], vals[i]);
	}

	public SkipListLinkedC(Key[] keys, Value[] vals) { // initialize from a list of keys and values
		this();
		this.insert(keys, vals);
	}

	public boolean isEmpty() {
		return n == 0;
	}

	public int size() {
		return n;
	}


	// private methods

	private LinkedNode<Key, Value> getLevel(int l) {
		LinkedNode<Key, Value> level = start; //this will always be start node at max level
		while (level.getLevel() > l)
			level = level.getBottom(); //keep going down until we hit our entry point }
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
		for (LinkedNode<Key, Value> level = start; level != null; level = level.getBottom()) {
			LinkedNode<Key, Value> currentNode = level;
			while (currentNode.getType() != LinkedNode.Type.cap) {
				s += "{" + currentNode.getKey() + ", " + currentNode.getValue() + ", " + currentNode.getLevel() + "} -->";
				currentNode = currentNode.getNext();
			}
			s += "{" + currentNode.getKey() + ", " + currentNode.getValue() + "}";
			s += "\n";
		}
		return s;
	}

	private void increaseEnds(int l) {
		while (l > start.getLevel()) {
			LinkedNode<Key, Value> startLevel = new LinkedNode<Key, Value>(start, null, null, null, null, LinkedNode.Type.root, start.getLevel() + 1);
			LinkedNode<Key, Value> termLevel = new LinkedNode<Key, Value>(terminus, startLevel, null, null, null, LinkedNode.Type.cap, startLevel.getLevel() + 1);
			startLevel.setNext(termLevel);
			start = startLevel;
			terminus = termLevel;
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
		System.out.println(sl);
		System.out.println("CONFIRMING DELETE\n");
		System.out.println((sl.size() == 0) + ", Size: " + sl.size());
		System.out.println(sl);

		for (int i = 0; i < keys.length; i++) {
			try {
				String res = sl.get(keys[i]);
				System.out.println("TEST FAILD:" + res);
			} catch (NoSuchElementException e) {
				System.out.println("TEST PASSED!");
			}
		}
		System.out.println("\nCONFIRMING DELETE END");
		System.out.println(sl);
	}
}
