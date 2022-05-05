import java.util.Scanner;

public class Driver {


	public static boolean isPrime(int n) {
		if(n <= 1) return false;
		if(n <= 3) return true;

		//check to see if number is div by two or three
		if(n % 2 == 0 || n % 3 == 0)
			return false;

		for(int i = 5; i*i <= n; i += 6) {
			if(n % i == 0 || n % (i + 2) == 0) return false;
		}

		return true;
	}

	public static int nextPrime(int n) {
		int p = n;
		while(true) {
			p++;

			if(isPrime(p)) {
				return p;
			}

		}
	}

	public static void main(String[] args) {
		McHashTable.Probe lin = McHashTable.Probe.LINEAR;
		McHashTable.Probe quad = McHashTable.Probe.QUADRATIC;

		int elements = 45403;
		int buckets = 0;
		//percentage of extra buckets to add in order to prevent excessive coll.
		double tablePadding = 30;

		Scanner kb = new Scanner(System.in);
		String[] words = new String[elements];

		for(int i = 0; i < elements; i++) {
			words[i] = kb.nextLine().trim();
		}
		kb.close();


		System.out.println("LINEAR PROBING:");
		for(int load = 50; load <= 100; load += 5) {
			buckets = (int) ( (elements / (load/100.0)) );
			buckets += buckets * (tablePadding / 100.0);
			buckets = nextPrime(buckets);


			McHashTable m = new McHashTable(buckets, lin);

			for(String s : words) {
				m.insert(s);
			}

			System.out.print(load);
			System.out.print(" ");
			System.out.print(m.getInsertions());
			System.out.print(" ");
			System.out.print(m.getCollisions());
			System.out.println();

		}
		System.out.println();
		System.out.println();

		System.out.println("QUADRATIC PROBING:");
		for(int load = 50; load <= 100; load += 5) {
			buckets = (int) ( (elements / (load/100.0)) );
			buckets += buckets * (tablePadding / 100.0);
			buckets = nextPrime(buckets);


			McHashTable d = new McHashTable(buckets, quad);

			for(String s : words) {
				d.insert(s);
			}

			System.out.print(load);
			System.out.print(" ");
			System.out.print(d.getInsertions());
			System.out.print(" ");
			System.out.print(d.getCollisions());
			System.out.println();

		}


	}
}
