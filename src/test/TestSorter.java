/**
 *  @author Mengyuan Hunang, Ruijia Wang
 * CSE 332 
 * PROJECT 2 PHASE A
 */
package test;
import static org.junit.Assert.*;
import main.Sorter;

import org.junit.Test;

import phaseA.StringComparator;
import phaseB.compareTopK;
import providedCode.DataCount;


public class TestSorter {
	private static final int TIMEOUT = 4000;
	
	/**Test heapSort ===========================================**/
	@Test(timeout = TIMEOUT)
	public void test_heap_sort() {
		String[] insert = {"a", "c", "b", "d", "e", null};
		Sorter.heapSort(insert, new StringComparator());
		String[] expected = {"a", "b", "c", "d", "e", null};
		assertArrayEquals("expected array should equal to insert array", insert, expected);
	}
	
	@Test(expected = NullPointerException.class)
	public void test_exception(){
		Sorter.heapSort(null, new StringComparator());
	}
	
	/**Test otherSort ===========================================**/
	@Test(timeout = TIMEOUT)
	public void test_otherSort(){
		String[] insert = {"b", "c", "a", "d", "e", "g"};
		Sorter.otherSort(insert, new StringComparator());
		String[] expected = {"a", "b", "c", "d", "e", "g"};
		assertArrayEquals("expected array should equal to insert array", insert, expected);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_otherSort_duplicate(){
		String[] insert = {"c", "a", "a", "b"};
		Sorter.otherSort(insert, new StringComparator());
		String[] expected = {"a", "a", "b", "c"};
		assertArrayEquals("insert and expected array should be same", insert, expected);
	}
	
	@Test(expected = NullPointerException.class)
	public void test_array_null(){
		Sorter.otherSort(null, new StringComparator());
	}
	
	@Test(timeout = TIMEOUT)
	public void test_sorted_otherSort(){
		String[] insert = {"a", "b", "c", "d"};
		Sorter.otherSort(insert, new StringComparator());
		String[] expected = {"a", "b", "c", "d"};
		assertArrayEquals("insert and expected array should be same", insert, expected);
	}
	
	/**Test topKSort ===========================================**/
	@SuppressWarnings("unchecked")
	@Test(timeout = TIMEOUT)
	public void test_topK_k_is_less_than_length(){
		DataCount<String> s1 = new DataCount<String>("a", 3);
		DataCount<String> s2 = new DataCount<String>("b", 7);
		DataCount<String> s3 = new DataCount<String>("c", 5);
		DataCount<String> s4 = new DataCount<String>("d", 10);
		DataCount<String> s5 = new DataCount<String>("e", 1);
		DataCount<String>[] insert = (DataCount<String>[]) new DataCount[5];
		insert[0] = s1;
		insert[1] = s2;
		insert[2] = s3;
		insert[3] = s4;
		insert[4] = s5;
		
		Sorter.topKSort(insert, new compareTopK(), 3);
		
		DataCount<String>[] expected = (DataCount<String>[]) new DataCount[3];
		expected[0] = s4;
		expected[1] = s2;
		expected[2] = s3;
		
		assertElementEquals(insert, expected);
	}
	
	@Test(timeout = TIMEOUT)
	@SuppressWarnings("unchecked")
	public void test_topK_k_equals_to_length(){
		DataCount<String> s1 = new DataCount<String>("dog", 3);
		DataCount<String> s2 = new DataCount<String>("cat", 7);
		DataCount<String> s3 = new DataCount<String>("pig", 5);
		DataCount<String> s4 = new DataCount<String>("tiger", 10);
		DataCount<String> s5 = new DataCount<String>("lion", 1);
		DataCount<String>[] insert = (DataCount<String>[]) new DataCount[5];
		insert[0] = s1;
		insert[1] = s2;
		insert[2] = s3;
		insert[3] = s4;
		insert[4] = s5;
		
		Sorter.topKSort(insert, new compareTopK(), 5);
		DataCount<String>[] expected = (DataCount<String>[]) new DataCount[5];
		expected[0] = s4;
		expected[1] = s2;
		expected[2] = s3;
		expected[3] = s1;
		expected[4] = s5;
		
		assertElementEquals(insert, expected);
	}
	
	@Test(timeout = TIMEOUT)
	@SuppressWarnings("unchecked")
	public void test_topK_same_frequency(){
		DataCount<String> s1 = new DataCount<String>("dog", 3);
		DataCount<String> s2 = new DataCount<String>("cat", 3);
		DataCount<String> s3 = new DataCount<String>("pig", 3);
		DataCount<String> s4 = new DataCount<String>("tiger", 10);
		DataCount<String> s5 = new DataCount<String>("lion", 1);
		DataCount<String>[] insert = (DataCount<String>[]) new DataCount[5];
		insert[0] = s1;
		insert[1] = s2;
		insert[2] = s3;
		insert[3] = s4;
		insert[4] = s5;
		
		Sorter.topKSort(insert, new compareTopK(), 5);
		DataCount<String>[] expected = (DataCount<String>[]) new DataCount[5];
		expected[0] = s4;
		expected[1] = s2;
		expected[2] = s1;
		expected[3] = s3;
		expected[4] = s5;
		 
		assertElementEquals(insert, expected);
	}
	
	@Test(expected = NullPointerException.class)
	public void test_topK_array_null(){
		Sorter.topKSort(null, new compareTopK(), 0);
	}
	
	private void assertElementEquals(DataCount<String>[] insert, DataCount<String>[] expected){
		for(int i = 0; i < expected.length; i++){
			assertEquals(expected[i], insert[i]);
		}
	}
	

	
}
