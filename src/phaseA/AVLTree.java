/**
 *  @author Mengyuan Hunang, Ruijia Wang
 * CSE 332 
 * PROJECT 2 PHASE A
 * 
 * AVLTree extends the class BinarySearchTree using a avl tree.
 * The constructor takes a Comparator<? super E> "function object" so
 * that items of type E can be compared.
 * Each tree node associates a count with an E.
 */

package phaseA;
import providedCode.*;


public class AVLTree<E> extends BinarySearchTree<E> {
    
	/**
     * Create an empty avl tree.
     */
	public AVLTree(Comparator<? super E> c) {
		super(c);
	}
	
	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	public void incCount(E data) {
		overallRoot = insert(data, (AVLNode)overallRoot);
	}
	
	/**
	 * Internal method to insert into a subtree.
	 * @param data the item to insert.
	 * @param node the node that roots the subtree.
	 * @return the new root of the subtree.
	 */
	@SuppressWarnings("unchecked")
	private AVLNode insert(E data, AVLNode node) {
        if (node == null) {
            return new AVLNode(data);
        }       
        // compare the new data with the data at the current node
        int cmp = comparator.compare(data, node.data);
        if(cmp == 0) {            // a. Current node is a match
        	node.count++;
        }else if(cmp < 0) {       // b. Data goes left of current node
            node.left = insert(data, (AVLNode)node.left);
        }else{                    // c. Data goes right of current node
        	node.right = insert(data, (AVLNode)node.right);
        }
        return balance(node);
    }
	
	// Assume node is either balanced or within one of being balanced
	@SuppressWarnings("unchecked")
	private AVLNode balance(AVLNode node){
		if (node == null) {
			return node;
		}
		if (height((AVLNode)node.left) - height((AVLNode)node.right) > 1) {
			if(height((AVLNode)node.left.left) >= height((AVLNode)node.left.right)) {
				node = rotateWithLeft(node);
			} else {
				node = doubleRotateWithLeft(node);
			}			
		} else if (height((AVLNode)node.right) - height((AVLNode)node.left) > 1){
			if(height((AVLNode)node.right.right) >= height((AVLNode)node.right.left)) {
				node = rotateWithRight(node);
			} else {
				node = doubleRotateWithRight(node);
			}
		}
		node.height = Math.max(height((AVLNode)node.left), height((AVLNode)node.right)) + 1;
		return node;
	}
	
	// Junit test method for internal correctness
	@SuppressWarnings("unchecked")
	public int getHeight() {
    	return height((AVLNode)overallRoot);
	}
	
	// Junit test method for internal correctness
	@SuppressWarnings("unchecked")
	public boolean checkBalance() {
		return helper((AVLNode)overallRoot);
	}
	
	// a helper method for checkBalance method
	@SuppressWarnings("unchecked")
	private boolean helper(AVLNode node) {
		if (node == null) {
			return true;
		} else if ((height((AVLNode)node.left) - height((AVLNode)node.right)) > 1 
				|| (height((AVLNode)node.left) - height((AVLNode)node.right)) < -1) {
			return false;
		} else {
			return helper((AVLNode)node.left) && helper((AVLNode)node.right);
		}
	}
	
	/**
	 * Return the height of node t, or -1, if null.  
	 */
	private int height(AVLNode node) {
		 return node == null ? -1 : node.height;
	}
	
	/**
	 * Rotate avl tree node with left child
	 * For avl tree, this is a single rotation for case 1
	 * Update the height and return the new root
	 */
	@SuppressWarnings("unchecked")
	private AVLNode rotateWithLeft(AVLNode node) {
		AVLNode temp = (AVLNode) node.left;
		node.left = temp.right;
		temp.right = node;
		node.height = 1 + Math.max(height((AVLNode)node.left), height((AVLNode)node.right));
		temp.height = 1 + Math.max(height((AVLNode)temp.left), node.height);
		return temp;
	}
		
	/**
	 * Rotate avl tree node with right child
	 * For avl tree, this is a single rotation for case 4
	 * Update the height and return the new root
	 */
	@SuppressWarnings("unchecked")
	private AVLNode rotateWithRight(AVLNode node) {
		AVLNode temp = (AVLNode) node.right;
		node.right = temp.left;
		temp.left = node;
		node.height = 1 + Math.max(height((AVLNode)node.right), height((AVLNode)node.left));
		temp.height = 1 + Math.max(height((AVLNode)temp.right), node.height);
		return temp;
	}
		
	/**
	* Given an AVLNode current, performs a case 2 double rotation.
	* Update the height and return the new root
	*/
	@SuppressWarnings("unchecked")
	private AVLNode doubleRotateWithLeft(AVLNode node){
		node.left = rotateWithRight((AVLNode) node.left);
		return rotateWithLeft(node);
	}
		
	/**
	 * Given an AVLNode current, performs a case 3 double rotation.
	 * Update the height and return the new root
	 */
	@SuppressWarnings("unchecked")
	private AVLNode doubleRotateWithRight(AVLNode node){
		node.right = rotateWithLeft((AVLNode) node.right);
		return rotateWithRight((AVLNode) node);
	}
	
	/** Private Class ==================================================**/

    /**
     * Inner class to represent a node in the tree. Each node includes
     * a data of type E and an integer count. The class is protected
     * so that subclasses of AVLTree can access it.
     */
    protected class AVLNode extends BSTNode{
    	public int height;
        /**
         * Create a new data node and increment the enclosing tree's size.
         * @param data data element to be stored at this node.
         * @param height store the height of this node.
         */
        public AVLNode(E d) {
        	super(d);
        	height = 0;
        }
    }
}
