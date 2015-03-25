/**
 *  @author Mengyuan Hunang, Ruijia Wang
 * CSE 332 
 * PROJECT 2 PHASE A
 */
package testA;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

import phaseA.FourHeap;
import phaseA.StringComparator;

public class TestFourHeap {
	private static final int TIMEOUT = 2000;
	private FourHeap<String> test;
	
	
	@Before
	public void setUp() {
		test = new FourHeap<String>(new StringComparator());
	}
	
	/** Test isEmpty ===========================================================**/
	@Test(timeout = TIMEOUT)
	public void test_size_empty(){
		assertTrue("heap should be empty when constructed", test.isEmpty());
	}
	
	
	/** Test insertion =========================================================**/
	@Test(timeout = TIMEOUT)
	public void test_insert_in_order(){
		String[] expectedArray = {"a", "b", "c","d", "e", "f"};
		for(int i = 0; i < expectedArray.length; i++){
			test.insert(expectedArray[i]);
		}
		String result = test.getArray();
		String expected = Arrays.toString(expectedArray);
		assertEquals("expected array and result array should be equals", expected, result);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_insert_without_order(){
		String[] insert = {"a", "f", "e", "b", "c", "d"};
		for(int i = 0; i < insert.length; i++){
			test.insert(insert[i]);
		}
		String result = test.getArray();
		String[] expectedArray = {"a", "d", "e", "b", "c", "f"};
		String expected = Arrays.toString(expectedArray);
		assertEquals("expected array and result array should be equals", expected, result);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_insert_size_change_in_order(){
		test.insert("a");
		test.insert("b");
		test.insert("c");
		test.insert("d");
		String result = test.getArray();
		String[] expectedArray = {"a", "b", "c", "d", null, null};
		String expected = Arrays.toString(expectedArray);
		assertEquals("expected array and result array should be equals", expected, result);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_null_insert(){
		test.insert(null);
		String result = test.getArray();
		String[] expectedArray = {null, null};
		String expected = Arrays.toString(expectedArray);
		assertEquals("expected array and result array should be equals", expected, result);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_insert_duplicate(){
		test.insert("a");
		test.insert("a");
		test.insert("b");
		test.insert("c");
		test.insert("d");
		test.insert("f");
		String result = test.getArray();
		String[] expectedArray = {"a", "a", "b", "c", "d", "f"};
		String expected = Arrays.toString(expectedArray);
		assertEquals("expected array and result array should be equals", expected, result);
	}
	
	
	/** Test deleteMine =========================================================**/
	@Test(expected = NoSuchElementException.class)
	public void test_exception(){
		test.deleteMin();
	}
	
	@Test(timeout = TIMEOUT)
	public void test_returnMin(){
		String[] insert = {"a", "f", "e", "b", "c", null};
		for(int i = 0; i < insert.length; i++){
			test.insert(insert[i]);
		}
		String[] expectedArray = {"a", "b", "c", "e", "f", null};
		String[] resultArray = new String[6];
		for(int i = 0; i < resultArray.length; i++){
			resultArray[i] = test.deleteMin();
		}
		String expected = Arrays.toString(expectedArray);
		String result = Arrays.toString(resultArray);
		assertEquals("result should be equal to expected output after insertion and deletion", expected, result);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_percolateDown(){
		String[] insert = {"f", "e", "b", "c", "a", null};
		for(int i = 0; i < insert.length; i++){
			test.insert(insert[i]);
		}
		String result = test.deleteMin();
		String expected = "a";
		assertEquals("result should be equal to expected output after single deletion", expected, result);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_findMin(){
		String[] insert = {"f", "e", "b", "c", "a"};
		for(int i = 0; i < insert.length; i++){
			test.insert(insert[i]);
		}
		String result = test.findMin();
		assertEquals("result should be equal to expected output after single deletion", "a", result);
	}
	
	/** Test isEmpty =========================================================**/
	@Test(timeout = TIMEOUT)
	public void test_empty(){
		assertTrue("Fourheap should be empty when constructed", test.isEmpty());
	}
	
	public void test_empty_after_deletion(){
		test.insert("a");
		test.insert("b");
		test.deleteMin();
		test.deleteMin();
		assertTrue("Fourheap should be empty after delet all elements", test.isEmpty());
	}
	
}
