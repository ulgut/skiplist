public class SkipListC<Key extends Comparable<Key>, Value> implements SkipList<Key, Value> {

	private final double P = 0.5; // 50% chance we increase level.
	private Node<Key, Value> root; // starting level 1 at smallest key.
	private int height; // root height
	private int n;

	public SkipListC() { // initialize from a single key,value pair
		this.root = new Node<Key, Value>();
		this.height = 1;
		this.n = 0;
	}
	//test comment

	//    public SkipListC(Key[] keys, Value[] vals) { // initialize from a list of keys and values
	//        if (keys.length == 0 || keys.length != vals.length) {
	//            throw new InvalidParameterException("Cannot form a Skip List from these lists.");
	//        }
	//
	//        keys =
	//        for (int i = 0; i < keys.length; i++) {
	//            if (i == 0) {
	//                ///
	//            }
	//        }
	//    }

	public Value get(Key key) {
		return null;
	}

	public void delete(Key key) {
		return;
	}


	public void insert(Key key, Value val) {

		int levels = randLevel();
		System.out.println("Levels: " + levels);

		//Adjusts height of root / terminal . Make its own method
		while (levels > height) {
			root.nexts.add(new Node<>());
		}

		//creating new node
		Node<Key, Value> newNode = new Node<>(key, val);

		//building new node to proper height
		for (int i = 0; i < levels; i++) {
			System.out.println("Built new node at level: " + i);

			newNode.nexts.add(new Node<Key, Value>());
			newNode.prevs.add(new Node<Key, Value>());
		}

		int i = levels;

		//builds of a vertical stack of nodes from top to bottom and links them forward and back progressivley
		while (i > 0) {

			Node<Key, Value> backNode = root;

			//finds previous node at each level to link up to
			while (backNode.nexts.get(i) != null && backNode.nexts.get(i).isLess(newNode)) {
				backNode = backNode.nexts.get(i);
			}

			Node<Key, Value> frontNode = backNode.nexts.get(i);

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


	private int randLevel() {
		System.out.println("rand level called");
		if (Math.random() < P)
			return 1 + randLevel();
		else
			return 1; //make 1?
	}


	public boolean isEmpty() {
		return false;
	}

	public int size() {
		return n;
	}

	@Override
	public String toString() {
		return root.toString();
	}

	public static void main(String[] args) {
		SkipListC<Integer, String> sl = new SkipListC<>();


		sl.insert(5, "Hello");
		System.out.println(sl.toString());

	}
}