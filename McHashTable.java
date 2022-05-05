import java.sql.Array;
import java.util.NoSuchElementException;

public class McHashTable {
	int collisions = 0;
	int insertions = 0;
	int totalInsertions = 0;

	enum Probe
	{
		LINEAR,
		QUADRATIC
	}

	int buckets = 0;
	String[] a;
	Probe pMethod;

	McHashTable(int elements, Probe p) {
		buckets = elements;
		a = new String[buckets];
		pMethod = p;
	}

	int index(String s) {
		return (Math.abs(s.hashCode()) % (buckets + 1));
	}

	public void insert(String s) {
		int collCache = collisions;
		insertR(index(s), s);
		//this checks that there were no collisions for this insertion
		if(collCache == collisions) insertions++;
	}

	public void insertR(int i, String s) {

		//if there are no spots left then don't even try to insert
		//System.out.println(insertions+"/"+buckets+" - "+collisions+" " +
		//		"collisions");

		//check if out of bounds or if done
		if(i >= buckets-1) {
			insertR(0, s);
			return;
		}

		System.out.println(totalInsertions+"/"+buckets);
		if(totalInsertions >= buckets) {
			return;
		}

		if(a[i] == null) {
			a[i] = s;
			totalInsertions++;
		} else {
			int newI;

			//linear probing else we must be using quadratic probing
			if(pMethod.equals(Probe.LINEAR)) {
				newI = (i + 1);
				//spot not open, try again
				collisions++;
				//System.out.println("i: "+newI+"/"+(buckets-1));
				insertR(newI, s);
			} else {
				newI = ( (int) (Math.pow(i, 2)) );

				//spot not open, try again
				collisions++;
				insertR(newI, s);
			}
		}
	}

	private int findLin(String s) {
		int start = index(s);

		//probe until either we find the target or until the probe tests the
		// bucket right before where it started
		for(int probe = start; probe == (start-1); probe++) {
			System.out.println("probe is "+probe);
			if(a[probe].equals(s)) {
				return probe;
			}

			//if we reach the right boundary then set to -1 so loop sets to 0
			if(probe == (buckets-1) ) probe = -1;
		}

		return -1;
	}

	public String get(String s) throws NoSuchElementException {
		int i = findLin(s);
		if(i == -1) {
			throw new NoSuchElementException();
		} else {
			return a[i];
		}
	}

	private int size() {
		return buckets;
	}

	public int getCollisions() {
		return collisions;
	}

	public int getInsertions() {
		return insertions;
	}


}
