import java.util.NoSuchElementException;

public class SkipListC<Key extends Comparable<Key>, Value> implements SkipList<Key, Value> {

  private final double P = 0.5; // 50% chance we increase level.
  private Node<Key, Value> root; // starting level 1 at smallest key. Acts as -inf
  private Node<Key, Value> cap; // mirror of root that acts as +inf.
  private int height; // root height
  private int n;
  private int comparisons = 0;

  public SkipListC() { // initialize from a single key,value pair
    this.root = new Node<Key, Value>(Node.Type.root);
    this.cap = new Node<Key, Value>(Node.Type.cap);
    this.height = 1;
    this.n = 0;
  }

  public SkipListC(Key[] keys, Value[] vals) { // initialize from a list of keys and values
    this();
    this.insert(keys, vals);
  }

  public Value get(Key key) {
    Node<Key, Value> currentNode = root;
    int i = root.height() - 1;

    //clean up conditional?
    while (currentNode.getType() != Node.Type.cap && i >= 0) {
      System.out.printf("i is %d \n", i);
      if (currentNode.nexts.get(i).isLess(key)) { //most common case
        System.out.println("Current node: " + currentNode.getKey());
        System.out.println("Next node: " + currentNode.nexts.get(i).getKey());
        currentNode = currentNode.nexts.get(i);
        comparisons++;
      } else if (currentNode.nexts.get(i).equals(key)) {
        return currentNode.nexts.get(i).getValue();
      } else { // if !(currentNode.nexts.get(i).isLess(key)) then go down a level
        System.out.println("About to decrement i");
        comparisons++;
        i--;
      }
    }
    throw new NoSuchElementException("There is no value to match this Key: " + key);
  }

  public void delete(Key key) {
    return;
  }

  private void increaseEnds(int levels) {
    while (levels > root.height()) {
      root.nexts.add(new Node<>());
      cap.prevs.add(new Node<>());

      //linking the root and cap
      root.nexts.set(root.nexts.size() - 1, cap);
      cap.prevs.set(root.nexts.size() - 1, root);
    }
  }

  public void insert(Key key, Value val) {

    int levels = levels();
    System.out.println("Levels: " + levels);

    //Adjusts height of root / terminal . Same for cap
    increaseEnds(levels);


    //creating new node
    Node<Key, Value> newNode = new Node<Key, Value>(key, val);

    //building new node to proper height
    for (int i = 0; i < levels; i++) {
      System.out.println("Built new node at level: " + i);

      newNode.nexts.add(new Node<Key, Value>());
      System.out.println("newNode.nexts.size is " + newNode.nexts.size());
      newNode.prevs.add(new Node<Key, Value>());
    }

    int i = levels - 1;

    //builds of a vertical stack of nodes from top to bottom and links them forward and back progressively
    while (i >= 0) {
      Node<Key, Value> backNode = root;

      //clean up conditional
      while (i < backNode.height() && backNode.nexts.get(i) != cap && backNode.nexts.get(i).isLess(newNode)) {
        backNode = backNode.nexts.get(i);
      }

      //Setting the front node
      Node<Key, Value> frontNode = cap;
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
    else
      for (int i = 0; i < keys.length; i++) {
        insert(keys[i], vals[i]);
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

  public int size() {
    return n;
  }

  @Override
  public String toString() {
    return root.toString();
  }


  public static void main(String[] args) {
    SkipListC<Integer, String> sl = new SkipListC<>();

    sl.insert(6, "Testing");
    sl.insert(5, "Hello");
    sl.insert(1, "Before Hello");
    sl.insert(-10000, "wicked early");
    sl.insert(5, "Second 5");
    sl.insert(1000, "Wicked big");
    sl.insert(500, "pretty big");
    sl.insert(30, "Medium");
    System.out.println(sl);

    SkipListC<Integer, String> sl2 = new SkipListC<>();

    int n = 500;
    Integer[] keys = new Integer[n];
    String[] vals = new String[n];

    for (int i = 0; i < n; i++) {
      keys[i] = (int) (1000 * Math.random());
      vals[i] = "This is val #" + i;
    }

    System.out.println("\n\n Printing the SkipList:");
    sl2.insert(keys, vals);
    sl2.insert(400, "My Key");
    System.out.println(sl2);
    System.out.println("sl2 size: " + sl2.size());
    System.out.println("Testing out get(700): " + sl2.get(400));
    System.out.printf("There were %d comparisons in the search", sl2.comparisons);
    sl2.comparisons = 0;
  }
}
