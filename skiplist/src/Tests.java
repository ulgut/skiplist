import java.util.NoSuchElementException;

public class Tests {

	private static class Log {
		public void print(String test, long time, String units) {
			System.out.println(String.format("%s: %d%s", test, time, units));// millis
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

		startTime = System.currentTimeMillis();
		SkipListSeqC<Integer, String> seqSl = new SkipListSeqC<>(keys, vals);
		endTime = System.currentTimeMillis();
		log.print("Sequential Initialization", (endTime - startTime), "ms");

		startTime = System.currentTimeMillis();
		SkipListLinkedC<Integer, String> linkedSl = new SkipListLinkedC<>(keys, vals);
		endTime = System.currentTimeMillis();
		log.print("Linked Initialization", (endTime - startTime), "ms");

		startTime = System.nanoTime();
		String val1 = seqSl.get(keys[n % 2]);
		endTime = System.nanoTime();
		log.print("Sequential GET", (endTime - startTime), "ns");


		startTime = System.currentTimeMillis();
		String val2 = linkedSl.get(keys[n % 2]);
		endTime = System.currentTimeMillis();
		log.print("Linked GET", (endTime - startTime), "ns");

		assert val1.equals(val2);


		startTime = System.nanoTime();
		seqSl.delete(keys[n % 2]);
		endTime = System.nanoTime();
		log.print("Sequential DELETE", (endTime - startTime), "ns");


		startTime = System.currentTimeMillis();
		linkedSl.delete(keys[n % 2]);
		endTime = System.currentTimeMillis();
		log.print("Linked DELETE", (endTime - startTime), "ns");


		//Throws error on get deleted element.
//		try {
//			seqSl.get(keys[n % 2]);
//			System.out.println("failed.");
//		} catch (NoSuchElementException e) {
//			System.out.println("passed.");
//		}


		try {
			linkedSl.get(keys[n % 2]);
			System.out.println("failed.");
		} catch (NoSuchElementException e) {
			System.out.println("passed.");
		}


		startTime = System.nanoTime();
		for (int i = 0; i < keys.length; i++) {
			if (i == n % 2) {
//				try {
//					seqSl.get(keys[n % 2]);
//					System.out.println("failed.");
//				} catch (NoSuchElementException e) {
//					continue;
//				}
				continue;
			}
			seqSl.delete(keys[i]);
		}
		endTime = System.nanoTime();
		log.print("Sequential DELETE ALL", (endTime - startTime), "ns");


		startTime = System.nanoTime();
		for (int i = 0; i < keys.length; i++) {
			if (i == n % 2) {
				try {
					linkedSl.get(keys[n % 2]);
					System.out.println("failed.");
				} catch (NoSuchElementException e) {
					continue;
				}
			}
			linkedSl.delete(keys[i]);
		}
		endTime = System.nanoTime();
		log.print("Linked DELETE ALL", (endTime - startTime), "ns");

		assert (seqSl.size() == linkedSl.size() && linkedSl.size() == 0);

	}


	public static void main(String[] args) {
		test(10); //10 elements
		test(100); //100 elements
		test(1000); //100 elements
		test(10000); //10k elements
	}
}
