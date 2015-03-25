package providedCode;
/* Mengyuan Huang
 * CSE 332 AE
 * HW1 Phase A
 * implements Dstack interface using an array.
 */
import java.util.EmptyStackException;
public class GArrayStack<T> implements GStack<T>{
	public static final int INITIAL_SIZE = 10; // the initial size of the array
	private T[] array;
	private int lastIndex; //keep tracking the last index in the array
	
	//post: construct the initial array with the initial size.
	@SuppressWarnings("unchecked")
	public GArrayStack(){
		array = (T[])new Object[INITIAL_SIZE];
		lastIndex = -1;
	}
	
	// post: return true if the array is empty.
	// 		 false otherwise.
	public boolean isEmpty(){
		return lastIndex == -1;
	}
	
	
	// post: push the given value into the array.
	//		 when the array is full, resize the array
	//		 twice as large and copy over the elements
	// 		 in the smaller array to the larger one.
	@SuppressWarnings("unchecked")
	public void push(T d){
		if(lastIndex + 1 == array.length){
			int length = array.length;
			T[] newArray = (T[])new Object[length * 2];
			for(int i = 0; i < length; i++){
				newArray[i] = array[i];
			}
			array = newArray;
		}
		lastIndex++;
		array[lastIndex] = d;
	}
	
	// pre: the array should not be empty.
	// 		(throw EmptyStackException if not)
	// post: return the value of the last element
	//		 and remove the last element from the array.
	public T pop(){
		if(isEmpty()){
			throw new EmptyStackException();
		}
		T pop = array[lastIndex];
		lastIndex--;
		return pop;
	}
	
	// pre: the array should not be empty.
	// 		(throw EmptyStackException if not)
	// post: return the value of the last element 
	//		 in the array.
	public T peek(){
		if(isEmpty()){
			throw new EmptyStackException();
		}
		return array[lastIndex];
	}
	
}
