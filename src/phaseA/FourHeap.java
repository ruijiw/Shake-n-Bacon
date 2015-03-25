/**
 *  @author Mengyuan Hunang, Ruijia Wang
 * CSE 332 
 * PROJECT 2 PHASE A
 * This class extends Heap class. It is a four-children min-heap.
 */

package phaseA;
import providedCode.*;

import java.util.Arrays;
import java.util.NoSuchElementException;


public class FourHeap<E> extends Heap<E> {
	private Comparator<? super E> comparator;
	
	/**
	 * This method will construct a FourHeap.
	 * Using an array-based implementation, beginning
	 * at index(size) 0.
	 * @param c: this is the comparator.
	 */
	public FourHeap(Comparator<? super E> c) {
		super(0);
		comparator = c;
		size = -1;
	}


	/**
	 * @param item: item that will be inserted into the heap
	 * This method will insert item into heap. It will first 
	 * resize the heap array if the array is almost full. 
	 * Then it will use percolate up method to adjust the heap
	 * array to a min-heap.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void insert(E item) {
		if(size == heapArray.length - 1){
			E[] newArray = (E[]) new Object[(heapArray.length + 1) * 2];
			for(int i = 0; i < heapArray.length; i++){
				newArray[i] = heapArray[i];
			}
			heapArray = newArray;
		} 
		size++;
		int hole = size;
		while(hole > 0 && comparator.compare(item, heapArray[(hole - 1) / 4]) < 0){
			heapArray[hole] = heapArray[(hole - 1) / 4];
			hole = (hole - 1) / 4;
		}
		heapArray[hole] = item;
	}
	

	/**
	 * pre: the heap array shouldn't be empty. (throw exception otherwise).
	 * post: @return return the minimum element in the heap array.
	 */
	@Override
	public E findMin() {
		if(isEmpty()){
			throw new NoSuchElementException(); // ?????which exception???
		} 
		return heapArray[0];
	}

	/**
	 * @return return the minimum element in the array.
	 * pre: the heap array shouldn't be empty (throw exception otherwise)
	 * post: this method will return and delete the minimum element in
	 * the heap array. It will also adjust the heap array to a min-heap.
	 */
	@Override
	public E deleteMin() {
		if(isEmpty()){
			throw new NoSuchElementException();
		}
		E min = heapArray[0];
		int hole = percolateDown(0, heapArray[size]);
		heapArray[hole] = heapArray[size];
		size--;
		return min;
	}
	
	/**
	 * @param hole the index of min-element
	 * @param val the last element in the array
	 * @return return the index that the last element should be inserted
	 * in the array.
	 * This is the private method to help find the index where the 
	 * last element should be placed after deleting the min-element.
	 */
	private int percolateDown(int hole, E val){
		while(hole * 4 + 1 <= size){
			int target = hole * 4 + 1;
			int second = target + 1;
			int third = target + 2;
			int fourth = target + 3;
			
			if(second <= size && comparator.compare(heapArray[target], heapArray[second]) > 0){
				target = second;
			} 
			if(third <= size && comparator.compare(heapArray[target], heapArray[third]) > 0){
				target = third;
			} 
			if(fourth <= size && comparator.compare(heapArray[target], heapArray[fourth]) > 0){
				target = fourth;
			} 
			if(comparator.compare(heapArray[target], val) < 0){
				heapArray[hole] = heapArray[target];
				hole = target;
			} else {
				break;
			}
		}
		return hole;
	}

	/**
	 * @return return true if the heap array is empty 
	 * and false otherwise.
	 */
	@Override
	public boolean isEmpty() {
		return size == -1;
	}
	
	/**
	 * @return string of the array for test purpose
	 * for Junit test.
	 */
	public String getArray(){
		return Arrays.toString(heapArray);
	}
}
