/**
 *  @author Mengyuan Hunang, Ruijia Wang
 * CSE 332 
 * PROJECT 2 PHASE B
 * 
 * this is for test hastable implementation.
 */
package writeupExperiment;

import providedCode.Comparator;
import providedCode.DataCount;
import providedCode.DataCounter;
import providedCode.GArrayStack;
import providedCode.GStack;
import providedCode.Hasher;
import providedCode.SimpleIterator;

public class HashTable1<E> extends DataCounter<E> {
	// the number of the node in the hash table
    private int size; 
    // the array of buckets
    private HashNode[] array;
    // Function object to compare elements of type E, passed in constructor.
	private Comparator<? super E> comparator;
	// Function object to hash elements of type E, passed in constructor.
	private Hasher<E> hasher;
	// make the table size about as large as the number of elements expected
	public static final int LOAD_FACTOR = 1;
	// keep track the size of the hash table
	private int primeNum;
	// the hash table rehash multiple by 2
	public static final int[] PRIMES = {11, 23, 47, 97, 197, 397, 809, 1619, 3251, 6521, 13043, 
		26099, 52201, 104417, 208891};
	
    /**
     * Create an empty hash table.
     */
	public HashTable1(Comparator<? super E> c, Hasher<E> h) {
		primeNum = 0;
		array = (HashNode[]) new HashTable1.HashNode[PRIMES[primeNum]];
		comparator = c;
		hasher = h;
		size = 0;
	}
	
	/** {@inheritDoc} */
	public void incCount(E data) {
		int key = hasher.hash(data) % array.length;
		HashNode current = array[key];
		// if the data is in the chain
		while(current != null) {
			if(comparator.compare(current.data, data) == 0) {
				current.count++;
				return;
			}
			current = current.next;
		}
		// if the data is not in the chain
		if(current == null) {
			// check whether it need to rehash
			if (size + 1 > LOAD_FACTOR * array.length && primeNum < PRIMES.length - 1) {
				primeNum++;
				HashNode[] temp = (HashNode[]) new HashTable1.HashNode[PRIMES[primeNum]];
				for(int i = 0; i < array.length; i++) {
					HashNode current1 = array[i];
					while(current1 != null) {
						int key1 = hasher.hash(current1.data) % temp.length;
						HashNode next = current1.next;
						current1.next = temp[key1];
						temp[key1] = current1;
						current1 = next;
					}
				}
				array = temp;
				key = hasher.hash(data) % array.length;
			}
			// add a new node to the chain
			array[key] = new HashNode(data, array[key]);
			size++;
		}
	}

	/** {@inheritDoc} */
	public int getSize() {
		return size;
	}

	/** {@inheritDoc} */
	public int getCount(E data) {
		int key = hasher.hash(data) % array.length;
		HashNode current = array[key];
		while(current != null) {
			if(comparator.compare(current.data, data) == 0) {
				return current.count;
			}
		}
		return 0;
	}

	/** {@inheritDoc} */
	public SimpleIterator<DataCount<E>> getIterator() {
		// Anonymous inner class that keeps a stack of yet-to-be-processed nodes
		return new SimpleIterator<DataCount<E>>() {  
			GStack<HashNode> stack = new GArrayStack<HashNode>(); 
    		{
    			for (int i = 0; i < array.length; i++) {
    				if (array[i] != null) {
    					stack.push(array[i]);
    				}
    			}
    		}
    		public boolean hasNext() {
        		return !stack.isEmpty();
        	}
        	public DataCount<E> next() {
        		if(!hasNext()) {
        			throw new java.util.NoSuchElementException();
        		}
        		HashNode nextNode = stack.pop();
        		if(nextNode.next != null) {
        			stack.push(nextNode.next);
        		}
        		return new DataCount<E>(nextNode.data, nextNode.count);
        	}
    	};
	}
	
	// Junit test method for internal correctness
	public int getLength() {
		return array.length;
	}
	
	/** Private Class ==================================================**/
	
	/**
	 * Inner class to represent a customized linked list node, HashNode.
	 * Each node type E and an integer count. 
	 */
	private class HashNode {
		public HashNode next;
		public E data;
		public int count;
		
		/**
		 * @param next, the next HashNode in the list the client specifies
		 * @param data, data element to be stored at this node.
		 * @effects creates a new HashNode with the data and next specified by
		 * client. Initializes the count field to 1.
		 */
		public HashNode(E data, HashNode next) {
			this.next = next;
			this.data = data;
			count = 1;
		}
	}
}

