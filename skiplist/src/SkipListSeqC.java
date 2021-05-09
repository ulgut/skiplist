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
    SeqNode<Key, Value> currentNode = root;
    int i = root.height() - 1;

    //clean up conditional?
    while (currentNode.getType() != SeqNode.Type.cap && i >= 0) {
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
    throw new NoSuchElementException("No Value for " + key);
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
      System.out.println("Inside delete, beforeNode.key = " + beforeNode.getKey() + " beforeNode.type = " + beforeNode.getType() + " and afterNode.key = " + afterNode.getKey() + " afterNode.type = " + afterNode.getType());

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
        this.delete(key);
        System.out.print("\n============Just Replaced a node with a new node of the same Key ==============\n");
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


  public boolean contains(Key key) {
    return false; //IMPLEMENT
  }

  public int size() {
    return n;
  }

  @Override
  public String toString() {
    return root.toString();
  }


  public static void main(String[] args) {
    SkipListSeqC<Integer, String> sl = new SkipListSeqC<>();
    sl.insert(0, "Testing");
    sl.insert(6, "Testing");
    sl.insert(5, "Hello");
    sl.insert(1, "Before Hello");
    sl.insert(-10000, "wicked early");
    sl.insert(5, "Second 5");
    sl.insert(1000, "Wicked big");
    sl.insert(500, "pretty big");
    sl.insert(30, "Medium");
    System.out.println(sl);

    System.out.println("Sl1.get: " + sl.get(30));

    SkipListSeqC<Integer, String> sl2 = new SkipListSeqC<>();

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

    System.out.println("\n\n---- About to Delete Key = 400 ---\n");

    System.out.println(sl2.get(400));
    sl2.delete(400);
    System.out.println(sl2);
    System.out.println("sl2 size: " + sl2.size());
    for (int i = 0; i < keys.length; i++) {
      System.out.println(sl2.get(keys[i]));
    }
  }
}
