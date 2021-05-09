import java.util.ArrayList;

public class SeqNode<Key extends Comparable<Key>, Value> {

  public ArrayList<SeqNode<Key, Value>> prevs; //make private with accessor methods
  public ArrayList<SeqNode<Key, Value>> nexts; //make private with accessor methods

  private Key key;

  private Value value;

  enum Type {
    root,
    node,
    cap
  }


  private Type type;


  public SeqNode(ArrayList<SeqNode<Key, Value>> prevs, ArrayList<SeqNode<Key, Value>> nexts, Key key,
                 Value val, Type type) {
    this.prevs = prevs;
    this.nexts = nexts;
    this.key = key;
    this.value = val;
    this.type = type;
  }

  //initializes a Node with empty prevs and nexts
  public SeqNode(Key key, Value val, Type t) {
    this(new ArrayList<SeqNode<Key, Value>>(), new ArrayList<SeqNode<Key, Value>>(), key, val, t);
  }

  public SeqNode(Key key, Value val) {
    this(new ArrayList<SeqNode<Key, Value>>(), new ArrayList<SeqNode<Key, Value>>(), key, val, Type.node);
  }

  public SeqNode(Type t) {
    this(null, null, t);
  }

  public SeqNode() {
    this(Type.node);
  }

  public boolean isLess(SeqNode<Key, Value> other) {
    if (this.type == Type.root)
      return true;
    else if (this.type == Type.cap)
      return false;
    else
      return this.key.compareTo(other.key) < 0;
  }

  public boolean isLess(Key otherKey) {
    System.out.println("\n +++++++++++ isLess just called. this.key is " + this.key + " with type = " + this.getType() + " | otherKey = " + otherKey);
    if (this.type == Type.root) {
      System.out.println("Is less Branch 1");
      return true;
    } else if (this.type == Type.cap) {
      System.out.println(" Is less Branch 2");
      return false;
    } else {
      System.out.println("Is less Branch 3");
      return this.key.compareTo(otherKey) < 0;
    }
  }

  public boolean equals(Key otherKey) {
    System.out.println("Called equals. this key = " + this.key + " vs the other key = " + otherKey);
    if (this.key == null)
      return false;
    else {
      return this.key.compareTo(otherKey) == 0;
    }
  }

  public int height() {
    return nexts.size();
  }

  @Override
  public String toString() {

    String str = "";

    for (int i = nexts.size() - 1; i >= 0; i--) {
      SeqNode<Key, Value> currentNode = this;

      while (currentNode.type != Type.cap) {
        str += "{" + currentNode.key + ", " + currentNode.value + "} --> ";
        currentNode = currentNode.nexts.get(i);
      }
      str += "{" + currentNode.getKey() + ", " + currentNode.getValue() + "}";
      str += "\n";

    }

    return str;
  }

  public Key getKey() {
    return key;
  }

  public Value getValue() {
    return value;
  }

  public Type getType() {
    return type;
  }

  public void setValue(Value value) {
    this.value = value;
  }

}
