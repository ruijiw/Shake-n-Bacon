/**
 *  @author Mengyuan Hunang, Ruijia Wang
 * CSE 332 
 * PROJECT 2 PHASE A
 * 
 * MoveToFrontList implements the DataCounter interface using a list.
 * The constructor takes a Comparator<? super E> "function object" so
 * that items of type E can be compared.
 * Each tree node associates a count with an E.
 */
package phaseA;
import providedCode.*;
import providedCode.GArrayStack;


public class MoveToFrontList<E> extends DataCounter<E> {
	private Node root;
	private int size;
	private Comparator<? super E> comparator;
	
	/**
     * Create an empty MoveToFrontList.
     */
	public MoveToFrontList(Comparator<? super E> c) {
		root = null;
		comparator = c;
		size = 0;
	}
	
	/** {@inheritDoc} */
	public void incCount(E data) {
		if(root == null){
			root = new Node(data);
			size++;
			return;
		}
		Node currentNode = root;
		int cmp = comparator.compare(data, currentNode.data);
		if(cmp == 0){
			currentNode.count++;
			return;
		} else {
			// traverse the entire list to see if there is the same data.
			// increase the data count and move it to the front.
			// otherwise keep searching to the end.
			while(currentNode.next != null){
				currentNode = currentNode.next;
				cmp = comparator.compare(data, currentNode.data);
				if(cmp == 0){
					currentNode.count++;
					currentNode.front.next = currentNode.next;
					if(currentNode.next != null){
						currentNode.next.front = currentNode.front;
					}
					currentNode.front = null;
					currentNode.next = root;
					root.front = currentNode;
					root = currentNode;
					return;
				} 
			}
			// if the data doesn't exist yet, store it to the front.
			// and increase the linked list size.
			Node newNode = new Node(data);
			newNode.next = root;
			root.front = newNode;
			root = newNode;
			size++;
		}
	}

	/** {@inheritDoc} */
	public int getSize() {
		return size;
	}

	/** {@inheritDoc} */
	public int getCount(E data) {
		if (root != null) {
			int comp = comparator.compare(data,root.data);
			if (comp == 0) {
				return root.count;
			} else {
				Node currentNode = root.next;
				while(currentNode!= null){
					int cmp = comparator.compare(data, currentNode.data);
					if(cmp == 0){
						currentNode.front.next = currentNode.next;
						if(currentNode.next != null){
							currentNode.next.front = currentNode.front;
						}
						currentNode.next = root;
						root = currentNode;
						root.next.front = root;
						root.front = null;
						return currentNode.count;
					} else {
						currentNode = currentNode.next;
					}
				}
			}
		}
		return 0;
	}

	/** {@inheritDoc} */
    public SimpleIterator<DataCount<E>> getIterator() {
    	// Anonymous inner class that keeps a stack of yet-to-be-processed nodes
    	return new SimpleIterator<DataCount<E>>() {  
    		GStack<Node> stack = new GArrayStack<Node>(); 
    		{
    			if(root != null) { stack.push(root); }
    		}
    		public boolean hasNext() {
        		return !stack.isEmpty();
        	}
        	public DataCount<E> next() {
        		if(!hasNext()) {
        			throw new java.util.NoSuchElementException();
        		}
        		Node nextNode = stack.pop();
        		if(nextNode.next != null) {
        			stack.push(nextNode.next);
        		}
        		return new DataCount<E>(nextNode.data, nextNode.count);
        	}
    	};
    }
	
    // Junit test method for internal correctness
	public int getIndex(E data) {
		int index = 0;
		Node temp = root;
		while (temp != null) {
			if (temp.data == data) {
				return index;
			} 
			temp = root.next;
			index++;
		}
		return -1;
	}
    
	/** Private Class ==================================================**/

    /**
     * Inner class to represent a node in the list. Each node includes
     * a data of type E and an integer count. The node is double linked.
     * Each node has a front node and a next node.
     */
	private class Node{
		public E data; // data in the node.
		public Node next; //next node
		public int count;
		public Node front;

		// construct a node with given data and 
		// given next node.
		public Node(E data) {
		    this.data = data;
		    this.next = null;
		    this.count = 1;
		    this.front = null;
		}
	}

}