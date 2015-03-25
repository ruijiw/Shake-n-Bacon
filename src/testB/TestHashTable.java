package testB;
import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import phaseB.HashTable;
import phaseB.StringHasher;
import providedCode.Comparator;
import providedCode.DataCount;
import providedCode.SimpleIterator;

/**
 *  @author Mengyuan Hunang, Ruijia Wang
 * CSE 332 
 * PROJECT 2 PHASE B
 */

public class TestHashTable {
	private static final int TIMEOUT = 2000; // 2000ms = 2sec
	private HashTable<String> dc;
	
	/** Creates AVLTree before each test cases **/
	@Before
	public void Setup() {
		dc = new HashTable<String>(new Comparator<String>() {
			public int compare(String e1, String e2) { return e1.compareTo(e2); }
		}, new StringHasher());
	}

	/** Test Size ===========================================================**/

	@Test(timeout = TIMEOUT)
	public void test_size_empty(){
		assertEquals("Tree should have size 0 when constructed", 0, dc.getSize());
	}
	
	@Test(timeout = TIMEOUT) 
	public void test_size_after_adding_single_string(){
		addAndTestSize("Tree should have size 1 after adding single 5", new String[]{"five"}, 1);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_size_after_adding_many_same_string(){
		addAndTestSize("Tree should have size 1 after adding multiple 5", 
				new String[]{"five", "five", "five"}, 1);
	}

	@Test(timeout = TIMEOUT)
	public void test_size_after_adding_unique_string(){
		String[] testArray = {"zero", "one", "two", "three", "four"};
		addAndTestSize("Added " + Arrays.toString(testArray), testArray, 5);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_size_after_adding_duplicate_string(){
		String[] testArray = {"zero", "zero", "one", "one", "two", "two", "three", "three", "four", 
				"four"};
		addAndTestSize("Added " + Arrays.toString(testArray), testArray, 5);
	}
	
	/** Test getCount =======================================================**/
	
	@Test(timeout = TIMEOUT)
	public void test_get_count_after_adding_many_same_string(){
		String key = "nine";
		String[] testArray = {"nine", "nine", "nine", "nine", "nine", "nine", "nine"};
		addAndGetCount("Added " + Arrays.toString(testArray) + ", key=" + key, testArray, key, 7);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_get_count_after_adding_many_diff_string(){
		String key = "five";
		String[] testArray = {"zero", "five", "minusone", "five", "one", "five", "two"};
		addAndGetCount("Added " + Arrays.toString(testArray) + ", key=" + key, testArray, key, 3);
	}
	
	/** Test Iterator =======================================================**/

	@Test(timeout = TIMEOUT, expected = java.util.NoSuchElementException.class)
	public void test_iterator_empty() {
		SimpleIterator<DataCount<String>> iter = dc.getIterator();
		iter.next(); 
	}
	
	@Test(timeout = TIMEOUT)
	public void test_iterator_get_all_data() {
	    String[] startArray = {"seven", "five", "five", "six", "six", "ten", "nine", "four", 
	    		"eight", "six"};

	    // Expected array has no duplicates and is sorted
	    for (int i = 0; i < startArray.length; i++) { 
		dc.incCount(startArray[i]); 
	    }

	    String[] expected = {"eight", "five", "four", "nine", "seven", "six", "ten"};
	    
	    // Actual array returned by the iterator
	    int i = 0;
	    SimpleIterator<DataCount<String>> iter = dc.getIterator();
	    String[] actual = new String[expected.length];
	    while (iter.hasNext()) { 
		actual[i++] = iter.next().data; 
	    }
	    
	    
	    // Sort and test
	    Arrays.sort(actual);
	    assertArrayEquals("Added " + Arrays.toString(startArray) + 
			      "Got " + Arrays.toString(actual), expected, actual);

	}	
	
	/** Test rehash =======================================================**/
	
	@Test(timeout = TIMEOUT)
	public void test_length_before_over_load_factor(){
		String[] testArray = {"zero", "one", "two", "three", "four", "five", "six", "seven", 
				"eight", "nine", "ten"};
		addAndGetLength("Added " + Arrays.toString(testArray), testArray, 11);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_length_after_over_load_factor(){
		String[] testArray = {"zero", "one", "two", "three", "four", "five", "six", "seven", 
				"eight", "nine", "ten", "eleven"};
		addAndGetLength("Added " + Arrays.toString(testArray), testArray, 23);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_size_after_over_load_factor(){
		String[] testArray = {"zero", "one", "two", "three", "four", "five", "six", "seven", 
				"eight", "nine", "ten", "eleven", "zero", "one"};
		addAndTestSize("Added " + Arrays.toString(testArray), testArray, 12);
	}
	
	/** Test StringHasher =======================================================**/
	@Test(timeout = TIMEOUT) 
	public void test_equality_among_strings(){
		StringHasher hasher = new StringHasher();
		assertEquals("single char case", hasher.hash("one"), hasher.hash("one"));
		assertFalse("single char case", hasher.hash("one") == hasher.hash("two"));
	}
	
	/** Private methods =====================================================**/

	private void addAndTestSize(String message, String[] input, int unique) {
		for (String s : input) { 
		    dc.incCount(s); 
		}
		assertEquals(message, unique, dc.getSize());
	}
	
	private void addAndGetCount(String message, String[] input, String key, int expected) {
		for (String s : input) { 
		    dc.incCount(s); 
		}
		assertEquals(message, expected, dc.getCount(key));
	}
	
	private void addAndGetLength(String message, String[] input, int length) {
		for (String s : input) { 
		    dc.incCount(s); 
		}
		assertEquals(message, length, dc.getLength());
	}
}
