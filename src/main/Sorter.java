/**
 *  @author Mengyuan Hunang, Ruijia Wang
 * CSE 332 
 * PROJECT 2 PHASE B
 */

package main;
import providedCode.Comparator;
import phaseA.FourHeap;
import providedCode.Heap;


/**
 * the Sorter class implements 4 different sorting method to 
 * be used by WordCount to sort a given array of data.
 */
public class Sorter {

	
	/**
     * Sort the count array in descending order of count. If two elements have
     * the same count, they should be ordered according to the comparator.
     * This code uses insertion sort. The code is generic, but in this project
     * we use it with DataCount<String> and DataCountStringComparator.
     * @param counts array to be sorted.
     * @param comparator for comparing elements.
     */
    public static <E> void insertionSort(E[] array, Comparator<E> comparator) {
        for (int i = 1; i < array.length; i++) {
            E x = array[i];
            int j;
            for (j=i-1; j>=0; j--) {
                if (comparator.compare(x,array[j]) >= 0) { break; }
                array[j + 1] = array[j];
            }
            array[j + 1] = x;
        }
    }
    
    /**
     * pre: the array shouldn't be empty. (throw exception otherwise)
     * post: This method will sort the given array with the comparator.
     * First insert each element from the given array to be sorted
     * into the fourHeap. Then remove each element from the heap, storing 
     * them in min-heap order and put them back to the array.
     * @param array array that need to be sorted
     * @param comparator for comparing elements
     */
    public static <E> void heapSort(E[] array, Comparator<E> comparator) {
    	if(array == null){
    		throw new NullPointerException();
    	}
    	Heap<E> heap = new FourHeap<E>(comparator);
    	for(int i = 0; i < array.length; i++){
    		heap.insert(array[i]);
    	}
    	for(int i = 0; i < array.length; i++){
    		array[i] = heap.deleteMin();
    	}
    }
    
    /**
     * pre: the array should not be empty
     * (throw exception otherwise)
     * post: Sort the top k most frequent element in ascending order. 
     * If two elements have the same count, they should be ordered according
     * to the comparator. This code uses heap sort. The code is generic.
     * We use it with DataCount<String> and DataCountStringComparator.
     * If k is greater than the array length, sort the entire array.
     * @param array the given array for sort
     * @param comparator for comparing different elements.
     * @param k number of most frequent elements we should sort.
     */
    public static <E> void topKSort(E[] array, Comparator<E> comparator, int k) {
    	if(array == null || k <= 0){
    		throw new NullPointerException();
    	}

    	// using a heap to sort the element
    	Heap<E> heap = new FourHeap<E>(comparator);
    	if(k > array.length){
    		k = array.length;
    	}
    	for(int i = 0; i < array.length; i++){
    		if(i >= k){
    			if(comparator.compare(array[i], heap.findMin()) > 0){
    				heap.deleteMin();
    				heap.insert(array[i]);
    			}
    		} else {
    			heap.insert(array[i]);
    		}
    	}
    	
    	// restore the array from the heap
    	for(int i = k - 1; i >= 0; i--){
    		array[i] = heap.deleteMin();
    	}
    }
 
    /**
     * pre: the array should not be empty. (throw exception otherwise) 
	 * Sort the count array in descending order of count. If two elements have
     * the same count, they should be ordered according to the comparator.
     * This code uses  merge sort. The code is generic, but in this project
     * we use it with DataCount<String> and DataCountStringComparator.
    * @param array to be sorted
    * @param comparator for comparing elements.
    */
	@SuppressWarnings("unchecked")
	public static <E> void otherSort(E[] array, Comparator<E> comparator) { 	
    	if(array == null) {
    		throw new NullPointerException();
    	}
    	E[] temp = (E[]) new Object[array.length];
    	mergeSort(array, temp, 0, array.length - 1, comparator);
    }
    
	/**
	 * internal merge sort method that takes recursive calls.
	 * @param array the given array to be sorted
	 * @param temp to store data temporarily
	 * @param left the left most index in subarray
	 * @param right the right most index in subarray
	 * @param comparator for comparing elements
	 */
    private static <E> void mergeSort(E[] array, E[] temp, 
    									int left, int right, 
    									Comparator<E> comparator) {
    	if(left < right) {
    		int center = (left + right) / 2;
    		mergeSort(array, temp, left, center, comparator); //sort the left part
    		mergeSort(array, temp, center + 1, right, comparator); // sort the right part
       		merge(array, temp, left, center, right, comparator); // combine two subarray	
    	}
    }
    
    /**
     * implement merge sort to merge two subarray.
     * @param array to be sorted
     * @param temp 
     * @param left the left-most index of the left subarry
     * @param mid	for the left-most index of the right subarray
     * @param high the right-most index of the right subarray
     * @param comparator for comparing elements.
     */
    private static <E> void merge(E[] array, E[] temp, int left, int mid, int high, Comparator<E> comparator) {
    	int leftEnd = mid;
    	int size = high - left + 1;
    	int pos = left;
    	int right = mid + 1;
    	
    	while(left <= leftEnd && right <= high) { //find the min in the left and right subarray
    		if(comparator.compare(array[left], array[right]) <= 0) {
    			temp[pos++] = array[left++];
    		} else {
    			temp[pos++] = array[right++];
    		}
    	} 
    	
    	while(left <= leftEnd) { // put the rest left subarray to the temp
    		temp[pos++] = array[left++];
    	}
    	
    	while(right <= high) { // put the rest right subarray to the temp
    		temp[pos++]  = array[right++];
    	}
    	
    	for(int i = 0; i < size; i++, high--) { // copy elements back to array
    		array[high] = temp[high];
    	}
    }
 }
    

    
    
    
    
    
    
    

