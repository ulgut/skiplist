import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentSkipListMap;

public class Tests {

	private static class Log {
		public void print(String test, long time, String units) {
			System.out.println(String.format("%s: %d%s", test, time, units));// nano seconds
		}
	}

	private static class Record {
		HashMap<String, Long> hm;

		public Record() {
			this.hm = new HashMap<String, Long>();
		}

		public int size() {
			return hm.size();
		}

		public long avg() {
			long sum = hm.values().stream().mapToLong(val -> val).sum();
			return sum / this.size();
		}

		public void add(String test, long result) {
			hm.put(test, result);
		}
	}

	public static void test(int n) {
		System.out.println("\n%%%%%%%%%%%%Test with " + n + " elements%%%%%%%%%%%%");
		Log log = new Log();
		Integer[] keys = new Integer[n];
		String[] vals = new String[n];
		ConcurrentSkipListMap<Integer, String> slt = new ConcurrentSkipListMap<Integer, String>(); // testing class


		for (int i = 0; i < n; i++) {
			int key = (int) (Math.random() * 10000 * Math.random());
			keys[i] = key;
			vals[i] = "TEST#" + key;
		}

		for (int j = 0; j < n; j++) {
			slt.put(keys[j], "TEST#" + keys[j]);
		}
		long startTime;
		long endTime;
		long beforeMem;
		long afterMem;

		// Initialization
		Runtime.getRuntime().gc();
		beforeMem = Runtime.getRuntime().freeMemory();
		startTime = System.nanoTime();
		SkipListSeqC<Integer, String> seqSl = new SkipListSeqC<>(keys, vals);
		endTime = System.nanoTime();
		afterMem = Runtime.getRuntime().freeMemory();
		long seqSlInsertionTime = (endTime - startTime);
		long seqMem = afterMem - beforeMem;
		log.print("Sequential Initialization", (endTime - startTime), "ns");

		System.out.println(seqSl);

		Runtime.getRuntime().gc();
		beforeMem = Runtime.getRuntime().freeMemory();
		startTime = System.nanoTime();
		SkipListLinkedC<Integer, String> linkedSl = new SkipListLinkedC<>(keys, vals);
		endTime = System.nanoTime();
		afterMem = Runtime.getRuntime().freeMemory();
		long linkedSlInsertionTime = (endTime - startTime);
		long linkedMem = afterMem - beforeMem;
		log.print("Linked Initialization", (endTime - startTime), "ns");
		System.out.println(linkedSl);
		// Initialization End

		// Get
		Record seqGetRecord = new Record();
		for (int i = 0; i < seqSl.size(); i++) {
			startTime = System.nanoTime();
			String val = seqSl.get(keys[i]);
			endTime = System.nanoTime();
			seqGetRecord.add("Get Test:" + i, endTime - startTime);
			assert (val.equals(vals[i]));

		}

		Record linkedGetRecord = new Record();
		for (int i = 0; i < linkedSl.size(); i++) {
			startTime = System.nanoTime();
			String val = linkedSl.get(keys[i]);
			endTime = System.nanoTime();
			linkedGetRecord.add("Get Test:" + i, endTime - startTime);
			assert (val.equals(vals[i]));

		}

		Record javaTGetRecord = new Record();
		for (int i = 0; i < linkedSl.size(); i++) {
			startTime = System.nanoTime();
			String val = slt.get(keys[i]);
			endTime = System.nanoTime();
			javaTGetRecord.add("Get Test:" + i, endTime - startTime);
			assert (val.equals(vals[i]));
		}
		// Get End

		//Delete
		Record seqDeleteRecord = new Record();
		for (int i = 0; i < seqSl.size(); i++) {
			startTime = System.nanoTime();
			try {
				seqSl.delete(keys[i]);
			} catch (NoSuchElementException e) {
			}
			endTime = System.nanoTime();
			seqDeleteRecord.add("Get Test:" + i, endTime - startTime);
		}


		Record linkedDeleteRecord = new Record();
		for (int i = 0; i < linkedSl.size(); i++) {
			startTime = System.nanoTime();
			try {
				linkedSl.delete(keys[i]);
			} catch (NoSuchElementException e) {
			}
			endTime = System.nanoTime();
			linkedDeleteRecord.add("Get Test:" + i, endTime - startTime);

		}

		Record javaTDeleteRecord = new Record();
		for (int i = 0; i < linkedSl.size(); i++) {
			startTime = System.nanoTime();
			try {
				slt.remove(keys[i]);
			} catch (NoSuchElementException e) {
			}
			endTime = System.nanoTime();
			javaTDeleteRecord.add("Get Test:" + i, endTime - startTime);

		}

		assert (seqSl.size() == linkedSl.size() && linkedSl.size() == 0);
		//Delete End


		// Edge Case Section
		try {
			seqSl.get((int) (Math.random() * 1000));
		} catch (NoSuchElementException e) {
			System.out.println("passed.");
		}
		try {
			seqSl.delete((int) (Math.random() * 1000));
		} catch (NoSuchElementException e) {
			System.out.println("passed.");
		}
		try {
			linkedSl.get((int) (Math.random() * 1000));
		} catch (NoSuchElementException e) {
			System.out.println("passed.");
		}
		try {
			linkedSl.delete((int) (Math.random() * 1000));
		} catch (NoSuchElementException e) {
			System.out.println("passed.");
		}
		// Edge Case Section End


		System.out.println("RESULTS: ");
		System.out.println("INSERTION: ");
		if (seqSlInsertionTime > linkedSlInsertionTime)
			System.out.println("LINKED WAS FASTER BY: " + (seqSlInsertionTime - linkedSlInsertionTime) + "ns");
		else
			System.out.println("SEQ WAS FASTER BY: " + (linkedSlInsertionTime - seqSlInsertionTime) + "ns");
		System.out.println("INSERTION MEMORY: ");
		if (seqMem > linkedMem)
			System.out.println("LINKED WAS BETTER BY: " + (seqMem - linkedMem) + "bytes");
		else
			System.out.println("SEQ WAS BETTER BY: " + (linkedMem - seqMem) + "bytes");
		System.out.println("GET: ");
		log.print("Linked GET: ", linkedGetRecord.avg(), "ns");
		log.print("Seq GET: ", seqGetRecord.avg(), "ns");
		log.print("Java GET: ", javaTGetRecord.avg(), "ns");
		System.out.println("DELETE: ");
		log.print("Linked DELETE: ", linkedDeleteRecord.avg(), "ns");
		log.print("Seq DELETE: ", seqDeleteRecord.avg(), "ns");
		log.print("Java DELETE: ", javaTDeleteRecord.avg(), "ns");
	}

	public static void main(String[] args) {
		test(1000); //1k elements
		test(10000); //10k elements
		test(100000); //100k elements

//		SkipListLinkedC<Integer, String> t1 = new SkipListLinkedC<Integer, String>();
//		for (int i = 0; i < 1000000; i++) {
//			int key = (int) (Math.random() * 100);
//			String val = "TEST #" + i;
//			System.out.println("INSERTING KEY: " + key + " WITH VALUE: " + val);
//			t1.insert(key, val);
//		}
//		System.out.println(t1);
	}
}
