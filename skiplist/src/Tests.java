import java.util.HashMap;

public class Tests {

	private static class Log {
		public void print(String test, long time, String units) {
			System.out.println(String.format("%s: %d%s", test, time, units));// millis
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

		for (int i = 0; i < n; i++) {
			int key = (int) (1000000 * Math.random());
			keys[i] = key;
			vals[i] = "TEST#" + key;
		}
		long startTime;
		long endTime;


		// Initialization
		startTime = System.nanoTime();
		SkipListSeqC<Integer, String> seqSl = new SkipListSeqC<>(keys, vals);
		endTime = System.nanoTime();
		log.print("Sequential Initialization", (endTime - startTime), "ns");

		startTime = System.nanoTime();
		SkipListLinkedC<Integer, String> linkedSl = new SkipListLinkedC<>(keys, vals);
		endTime = System.nanoTime();
		log.print("Linked Initialization", (endTime - startTime), "ns");
		// Initialization End

		// Get
		Record seqGetRecord = new Record();
		for (int i = 0; i < keys.length; i++) {
			startTime = System.nanoTime();
			String val = seqSl.get(keys[i]);
			endTime = System.nanoTime();
			seqGetRecord.add("Get Test:" + i, endTime - startTime);
			assert (val.equals(vals[i]));

		}
		log.print("Sequential GET", seqGetRecord.avg(), "ns");


		Record linkedGetRecord = new Record();
		for (int i = 0; i < keys.length; i++) {
			startTime = System.nanoTime();
			String val = linkedSl.get(keys[i]);
			endTime = System.nanoTime();
			linkedGetRecord.add("Get Test:" + i, endTime - startTime);
			assert (val.equals(vals[i]));

		}
		log.print("Linked GET", linkedGetRecord.avg(), "ns");
		// Get End

		//Delete
		Record seqDeleteRecord = new Record();
		for (int i = 0; i < keys.length; i++) {
			startTime = System.nanoTime();
			seqSl.delete(keys[i]);
			endTime = System.nanoTime();
			seqDeleteRecord.add("Get Test:" + i, endTime - startTime);

		}
		log.print("Sequential DELETE", seqDeleteRecord.avg(), "ns");


		Record linkedDeleteRecord = new Record();
		for (int i = 0; i < keys.length; i++) {
			startTime = System.nanoTime();
			linkedSl.delete(keys[i]);
			endTime = System.nanoTime();
			linkedDeleteRecord.add("Get Test:" + i, endTime - startTime);

		}
		log.print("Linked DELETE", linkedDeleteRecord.avg(), "ns");

		assert (seqSl.size() == linkedSl.size() && linkedSl.size() == 0);
		//Delete End

	}


	public static void main(String[] args) {
		test(10); //10 elements
		test(100); //100 elements
		test(1000); //100 elements
		test(10000); //10k elements
	}
}
