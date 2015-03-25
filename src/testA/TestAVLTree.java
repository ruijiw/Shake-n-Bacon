package testA;
import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;
import org.junit.Before;

import phaseA.AVLTree;
import providedCode.Comparator;
import providedCode.DataCount;
import providedCode.SimpleIterator;

/**
 *  @author Mengyuan Hunang, Ruijia Wang
 * CSE 332 
 * PROJECT 2 PHASE A
 */

public class TestAVLTree {
	private static final int TIMEOUT = 2000; // 2000ms = 2sec
	private AVLTree<String> dc;
	
	/** Creates AVLTree before each test cases **/
	@Before
	public void Setup() {
		dc = new AVLTree<String>(new Comparator<String>() {
			public int compare(String e1, String e2) { return e1.compareTo(e2); }
		});
	}

	/** Test Size ===========================================================**/

	@Test(timeout = TIMEOUT)
	public void test_size_empty(){
		assertEquals("Tree should have size 0 when constructed", 0, dc.getSize());
	}
	
	@Test(timeout = TIMEOUT) 
	public void test_size_after_adding_single_num(){
		addAndTestSize("Tree should have size 1 after adding single 5", new int[]{5}, 1);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_size_after_adding_many_same_num(){
		addAndTestSize("Tree should have size 1 after adding multiple 5", new int[]{5,5,5}, 1);
	}

	@Test(timeout = TIMEOUT)
	public void test_size_after_adding_unique_nums(){
		int[] testArray = {0,1,2,3,4};
		addAndTestSize("Added " + Arrays.toString(testArray), testArray, 5);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_size_after_adding_duplicate_nums(){
		int[] testArray = {0,0,1,1,2,2,3,3,4,4};
		addAndTestSize("Added " + Arrays.toString(testArray), testArray, 5);
	}
	
	
	
	/** Test height =======================================================**/
	
	@Test(timeout = TIMEOUT)
	public void test_height_null(){
		assertEquals("Tree should have height -1 when constructed", -1, dc.getHeight());
	}
	
	@Test(timeout = TIMEOUT)
	public void test_height_after_adding_many_same_num(){
		int[] testArray = {9, 9, 9, 9, 9, 9, 9};
		addAndTestHeight("Added " + Arrays.toString(testArray), testArray, 0);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_height_after_right_right_rotate(){
		int[] testArray = {0, 5, -5, 5, -3, 5, -4};
		addAndTestHeight("Added " + Arrays.toString(testArray), testArray, 2);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_height_after_left_left_rotate(){
		int[] testArray = {0, 5, -1, 5, -3, 5, -4};
		addAndTestHeight("Added " + Arrays.toString(testArray), testArray, 2);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_height_after_right_left_rotate(){
		int[] testArray = {0, 5, -1, 5, 6, 5, 7};
		addAndTestHeight("Added " + Arrays.toString(testArray), testArray, 2);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_height_after_left_right_rotate(){
		int[] testArray = {0, 5, -1, 5, 1, 5, 2};
		addAndTestHeight("Added " + Arrays.toString(testArray), testArray, 2);
	}
	
	/** Test getCount =======================================================**/
	
	@Test(timeout = TIMEOUT)
	public void test_get_count_after_left_right_same_num(){
		int key = 9;
		int[] testArray = {9, 9, 9, 9, 9, 9, 9};
		addAndGetCount("Added " + Arrays.toString(testArray) + ", key=" + key, testArray, key, 7);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_get_count_after_adding_many_diff_nums(){
		int key = 5;
		int[] testArray = {0, 5, -1, 5, 1, 5, 2};
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

	    int[] startArray = {7, 5, 5, 6, 6, 1, 9, 4, 8, 6};

	    // Expected array has no duplicates and is sorted
	    for (int i = 0; i < startArray.length; i++) { 
		dc.incCount("" + startArray[i]); 
	    }

	    String[] expected = { "1", "4", "5", "6", "7", "8", "9" };

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
	
	/** Test Balance =======================================================**/
	@Test(timeout = TIMEOUT)
	public void test_balance_null(){
		assertTrue("Tree should balance when constructed", dc.checkBalance());
	}
	
	@Test(timeout = TIMEOUT)
	public void test_balance_after_adding_many_same_num(){
		int[] testArray = {9, 9, 9, 9, 9, 9, 9};
		assertTrue("Added " + Arrays.toString(testArray), dc.checkBalance());
	}
	
	@Test(timeout = TIMEOUT)
	public void test_balance_after_right_right_rotate(){
		int[] testArray = {0, 5, -5, 5, -3, 5, -4};
		assertTrue("Added " + Arrays.toString(testArray), dc.checkBalance());
	}
	
	@Test(timeout = TIMEOUT)
	public void test_balance_after_left_left_rotate(){
		int[] testArray = {0, 5, -1, 5, -3, 5, -4};
		assertTrue("Added " + Arrays.toString(testArray), dc.checkBalance());
	}
	
	@Test(timeout = TIMEOUT)
	public void test_balance_after_right_left_rotate(){
		int[] testArray = {0, 5, -1, 5, 6, 5, 7};
		assertTrue("Added " + Arrays.toString(testArray), dc.checkBalance());
	}
	
	@Test(timeout = TIMEOUT)
	public void test_balance_after_left_right_rotate(){
		int[] testArray = {0, 5, -1, 5, 1, 5, 2};
		assertTrue("Added " + Arrays.toString(testArray), dc.checkBalance());
	}
	
	
	/** Private methods =====================================================**/

	private void addAndTestSize(String message, int[] input, int unique) {
		for (int num : input) { 
		    dc.incCount("" + num); 
		}
		assertEquals(message, unique, dc.getSize());
	}
	
	private void addAndGetCount(String message, int[] input, int key, int expected) {
		for (int num : input) { 
		    dc.incCount("" + num); 
		}
		assertEquals(message, expected, dc.getCount("" + key));
	}
	
	private void addAndTestHeight(String message, int[] input, int expected) {
		for (int num : input) { 
		    dc.incCount("" + num); 
		}
		assertEquals(message, expected, dc.getHeight()); 
	}

}
