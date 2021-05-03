import java.util.ArrayList;

public class Node<Key extends Comparable<Key>, Value> {

  public ArrayList<Node<Key, Value>> prevs; //make private with accessor methods
  public ArrayList<Node<Key, Value>> nexts; //make private with accessor methods

  private Key key;
  private Value value;

  enum Type {
    root,
    node,
    cap
  }


  private Type type;


  public Node(ArrayList<Node<Key, Value>> prevs, ArrayList<Node<Key, Value>> nexts, Key key,
              Value val, Type type) {
    this.prevs = prevs;
    this.nexts = nexts;
    this.key = key;
    this.value = val;
    this.type = type;
  }

  //initializes a Node with empty prevs and nexts
  public Node(Key key, Value val, Type t) {
    this(new ArrayList<Node<Key, Value>>(), new ArrayList<Node<Key, Value>>(), key, val, t);
  }

  public Node(Key key, Value val) {
    this(new ArrayList<Node<Key, Value>>(), new ArrayList<Node<Key, Value>>(), key, val, Type.node);
  }

  public Node(Type t) {
    this(null, null, t);
  }

  public Node() {
    this(Type.node);
  }

  public boolean isLess(Node<Key, Value> other) {
    if (this.type == Type.root)
      return true;
    else if (this.type == Type.cap)
      return false;
    else
      return this.key.compareTo(other.key) < 0;
  }

  public int height() {
    return nexts.size();
  }

  //rewrite with recursion
  @Override
  public String toString() {

    String str = "";

    for (int i = nexts.size() - 1; i >= 0; i--) {
      Node<Key, Value> currentNode = this;

      while (currentNode.type != Type.cap) {
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

  public Type getType() {
    return type;
  }
}
