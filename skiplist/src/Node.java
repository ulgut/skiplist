import java.util.ArrayList;

public class Node<Key extends Comparable<Key>, Value> {

	public ArrayList<Node<Key, Value>> prevs; //make private with accessor methods
	public ArrayList<Node<Key, Value>> nexts; //make private with accessor methods

	private Key key;
	private Value value;


	public Node(ArrayList<Node<Key, Value>> prevs, ArrayList<Node<Key, Value>> nexts, Key key,
				Value val) {
		this.prevs = prevs;
		this.nexts = nexts;
		this.key = key;
		this.value = val;
	}

	public Node(Key key, Value val) {
		this(null, null, key, val);
	}

	public Node() {
		this(null, null, null, null);
	}

	public boolean isLess(Node<Key, Value> other) {
		return this.key.compareTo(other.key) < 0;
	}

	public int height() {
		return nexts.size();
	}


	//rewrite with recursion
	@Override
	public String toString() {

		String str = "";

		for (int i = prevs.size() - 1; i >= 0; i--) {
			Node<Key, Value> currentNode = this;

			while (currentNode.nexts.get(i) != null) {
				str += "{" + currentNode.key + ", " + currentNode.value + "} --> ";
				currentNode = currentNode.nexts.get(i);
			}
			str += "\n";
		}

		return str;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public Value getValue() {
		return value;
	}

	public void setValue(Value value) {
		this.value = value;
	}

}