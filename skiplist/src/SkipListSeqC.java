import java.util.NoSuchElementException;

public class SkipListSeqC<Key extends Comparable<Key>, Value> implements SkipList<Key, Value> {

	private final double P = 1 / Math.E; // Optimal P-level
	private SeqNode<Key, Value> root; // starting level 1 at smallest key. Acts as -inf
	private SeqNode<Key, Value> cap; // mirror of root that acts as +inf.
	private int height; // root height
	private int n;
	private int comparisons = 0;

	public SkipListSeqC() { // initialize from a single key,value pair
		this.root = new SeqNode<Key, Value>(SeqNode.Type.root);
		this.cap = new SeqNode<Key, Value>(SeqNode.Type.cap);
		this.height = 1;
		this.n = 0;
	}

	public SkipListSeqC(Key[] keys, Value[] vals) { // initialize from a list of keys and values
		this();
		this.insert(keys, vals);
	}

	private SeqNode<Key, Value> search(Key key) {
		System.out.println("This is the key in search: " + key);
		SeqNode<Key, Value> currentNode = root;
		int i = root.height() - 1;

		while (currentNode.getType() != SeqNode.Type.cap && i >= 0) {
			System.out.println("This is the key in search: " + key);
			System.out.println("currentNode.nexts.get(i) has key = " + currentNode.nexts.get(i).getKey());
			if (currentNode.nexts.get(i).isLess(key)) { //most common case
				currentNode = currentNode.nexts.get(i);
				comparisons++;
			} else if (currentNode.nexts.get(i).equals(key)) {
				comparisons++; //needed here?
				return currentNode.nexts.get(i);
			} else { // if !(currentNode.nexts.get(i).isLess(key)) then go down a level
				comparisons++;
				i--;
			}
		}
		throw new NoSuchElementException("No Value for key:" + key);
	}

	public Value get(Key key) {
		return search(key).getValue();
	}

	//should we delete multiples? Should we allow for multiples at all?
	public void delete(Key key) {
		SeqNode<Key, Value> node = search(key);

		//working up linking the before and after nodes together
		for (int i = 0; i < node.nexts.size(); i++) {
			SeqNode<Key, Value> beforeNode = node.prevs.get(i);
			SeqNode<Key, Value> afterNode = node.nexts.get(i);

			beforeNode.nexts.set(i, afterNode);
			afterNode.prevs.set(i, beforeNode);
		}
		n--;
	}

	private void increaseEnds(int levels) {
		while (levels > root.height()) {
			root.nexts.add(new SeqNode<>());
			cap.prevs.add(new SeqNode<>());

			//linking the root and cap
			root.nexts.set(root.nexts.size() - 1, cap);
			cap.prevs.set(root.nexts.size() - 1, root);
		}
	}

	public void insert(Key key, Value val) {

		int levels = levels();

		//Adjusts height of root / terminal . Same for cap
		increaseEnds(levels);


		//creating new node
		SeqNode<Key, Value> newNode = new SeqNode<Key, Value>(key, val);

		//building new node to proper height
		for (int i = 0; i < levels; i++) {
			newNode.nexts.add(new SeqNode<Key, Value>());
			newNode.prevs.add(new SeqNode<Key, Value>());
		}

		int i = levels - 1;

		//builds of a vertical stack of nodes from top to bottom and links them forward and back progressively
		while (i >= 0) {
			SeqNode<Key, Value> backNode = root;

			//clean up conditional
			while (i < backNode.height() && backNode.nexts.get(i) != cap && backNode.nexts.get(i).isLess(newNode)) {
				backNode = backNode.nexts.get(i);
			}

			if (backNode.nexts.get(i).equals(key)) {
				throw new IllegalArgumentException("Cannot add Node with key: " + key + ". A node with key already exits");
			}

			//Setting the front node
			SeqNode<Key, Value> frontNode = cap;
			if (backNode.nexts.get(i) != null)
				frontNode = backNode.nexts.get(i);

			//linking backNode to newNode
			newNode.prevs.set(i, backNode);
			backNode.nexts.set(i, newNode);

			//linking frontNode to newNode
			frontNode.prevs.set(i, newNode);
			newNode.nexts.set(i, frontNode);

			i--;
		}
		n++;
	}

	public void insert(Key[] keys, Value[] vals) {
		if (keys.length != vals.length)
			throw new IndexOutOfBoundsException("Invalid Input! keys and vals arrays must have same number of elements");
		for (int i = 0; i < keys.length; i++) {
			try {
				insert(keys[i], vals[i]);
			} catch (IllegalArgumentException e) {
				System.out.println("Cannot add this node, key already present");
			}
		}
	}

	//generates a number of levels for a node to have
	private int levels() {
		if (Math.random() < P)
			return 1 + levels();
		else
			return 1; // Min number is 1
	}


	public boolean isEmpty() {
		return n == 0;
	}


	public boolean contains(Key key) {
		try {
			search(key);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public int size() {
		return n;
	}

	@Override
	public String toString() {
		return root.toString();
	}

	public int getComparisons() {
		return comparisons;
	}

	public void resetComparisons() {
		comparisons = 0;
	}
}
