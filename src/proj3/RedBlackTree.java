package proj3;


/**
 * @author rachaelbirky
 * @version 04.04.14
 * @section 01
 * 
 * <p> Description:  This represents a self-balancing tree using
 * 	red black tree implementation, where each node is an instance
 * 	of RedBlackNode<AnyType> and contains a red or black flag.
 *
 * @param <AnyType> this class is generic
 */
public class RedBlackTree<AnyType extends Comparable<? super AnyType>> {

	boolean debug = false;
	RedBlackNode<AnyType> root;

	/**
	 *<p> Method:  RedBlackTree
	 *<p> Description:  constructor, creates a new RedBlackTree with a null root 
	 */
	public RedBlackTree(){
		root = null;
	}

	/**
	 * <p> Method:  getRoot
	 * @return the first element (root) of the current tree
	 */
	public RedBlackNode<AnyType> getRoot(){
		return root;
	}

	
	/**
	 * <p> Method:  insert
	 * <p> Description:  bootstrap method to insert a new node into the tree
	 * 	with the given item. no duplicates allowed.
	 * If the tree is empty, the item is added as the root.  If not,
	 * 	the recursive insert function is called.
	 * Lastly, the new node is passed to the first insertion method
	 *  for keeping the tree balanced
	 * @param item: the item to store in the new node
	 */
	public void insert(AnyType item){
		//make new node so it can be passed to insertion cases
		RedBlackNode<AnyType> newNode = new RedBlackNode<AnyType>(item);
		if (root == null){
			root = newNode;
		}
		else
			insert(newNode, root);
		
		if (debug) System.out.println(newNode.parent);
		//fix everything by passing to first insertion case
		newNode.makeRed();
		insert0(newNode);
	}
	
	/**
	 * <p> Method:  insert
	 * <p> Description:  recursive method to add a new node into the RedBlackTree.
	 * 	Uses the standard BST algorithm to find the correct insertion location.
	 * 	Then sets the new node's parent and returns.
	 * @param newNode: node being inserted
	 * @param aRoot: the current root as the tree is traversed
	 * @return the new node
	 */
	private RedBlackNode<AnyType> insert(RedBlackNode<AnyType> newNode, RedBlackNode<AnyType> aRoot){
		
		int compare = newNode.getElement().compareTo(aRoot.getElement());
		
		//go to left
		if (compare<0){
			if(aRoot.left == null){
				aRoot.left = newNode;
				newNode.parent = aRoot;
				return newNode;	//can insert at this location
			}
			else
				return insert(newNode, aRoot.left);
		}
		//go to right
		else{
			if (aRoot.right == null){
				aRoot.right = newNode;
				newNode.parent = aRoot;
				return newNode;	//can insert here
			}
			else
				return insert(newNode, aRoot.right);
		}
	}


	/**
	 * <p> Method:  insert0
	 * <p> Description:  Handles the first (zeroth) case of inserting a node into
	 * 	a red black tree: if the node is the root, color it black and return.
	 * 	If it is not the root, check for the next case
	 * @param x: the RedBlackNode to be verified
	 */
	private void insert0(RedBlackNode<AnyType> x){
		if (x==root){
			if (debug) System.out.println(x==root);
			x.makeBlack();
		}
		else
			insert1(x);
	}
	
	/**
	 * <p> Method:  insert1
	 * <p> Description:  Handles the second case of inserting a node into
	 * 	a red black tree: if the node's parent is black, nothing needs
	 * 	to be done, and the node can be added and colored red
	 * @param x: the RedBlackNode to be verified
	 */
	private void insert1(RedBlackNode<AnyType> x){
		if (x.parent.isBlack()){
			return;
		}
		else
			insert2(x);
	}
	
	/**
	 * <p> Method:  insert2
	 * <p> Description:  Handles the third case of inserting a node into
	 * 	a red black tree: if the node's parent is red (as per insert1)
	 * 	and the node's uncle is also red
	 * 	1. color parent black
	 *  2. color the uncle black
	 *  3. color the grandparent red
	 *  4. verify that the newly red gandparent node doesn't violate
	 *  	any red black tree rules by recursing to first insertion case
	 *  5. check if the pattern is zig zag or zig zig and call the correct
	 *  	insertion case method
	 * @param x: the RedBlackNode to be verified
	 */
	private void insert2(RedBlackNode<AnyType> x){
		//parent has already been proven red, check uncle
		if (x.getUncle()!=null && x.getUncle().isRed()){
			//recolor
			x.parent.makeBlack();
			x.getUncle().makeBlack();
			x.getGrandparent().makeRed();
			//grandparent might violate double red
			insert0(x.getGrandparent());
		}
		//check zig zag
		else if ((x == x.parent.right && x.parent == x.getGrandparent().left) || (x == x.parent.left && x.parent == x.getGrandparent().right))
			insert3(x);
		//is zig zig
		else
			insert4(x);
	}
	
	/**
	 * <p> Method:  insert3
	 * <p> Description:  Handles the fourth case of inserting a node into
	 * 	a red black tree: if the node's parent is red (per insert1) and the
	 * 	node's uncle is black (per insert2) and the pattern is zig zag
	 *  1. color the grandparent red
	 *  2. color the new node black
	 *  3. rotate left or right accordingly, on the parent, then the grandparent
	 * @param x: the RedBlackNode to be verified
	 */
	private void insert3(RedBlackNode<AnyType> x){
		//parent proven red, uncle proven black
		//recolor
		x.getGrandparent().makeRed();
		x.makeBlack();
		
		//x is right child of left child, so rotate left
		//rotate left on parent
		if (x == x.parent.right && x.parent == x.getGrandparent().left){
			rotateLeft(x.parent);
			rotateRight(x.parent);
		}
		//x is left child of right child
		if (x == x.parent.left && x.parent == x.getGrandparent().right){
			rotateRight(x.parent);
			rotateLeft(x.parent);
		}
	}
	
	/**
	 * <p> Method:  insert4
	 * <p> Description:  Handles the fifth case of inserting a node into
	 * 	a red black tree: if the node's parent is red (per insert1) and the
	 * 	node's uncle is black (per insert2) and the pattern is zig zig
	 *  1. color the parent black
	 *  2. color the grandparent red
	 *  3. rotate left or right accordingly, on the grandparent
	 * @param x: the RedBlackNode to be verified
	 */
	private void insert4(RedBlackNode<AnyType> x){
		x.parent.makeBlack();
		x.getGrandparent().makeRed();
		
		//both left children, rotate right
		if (x == x.parent.left && x.parent == x.getGrandparent().left)
			//rotate right (parent on grandparent, pass parent)
			rotateRight(x.getGrandparent());
		
		//both right children, rotate left
		else if (x == x.parent.right && x.parent == x.getGrandparent().right)
			rotateLeft(x.getGrandparent());
	}
	

	
	/**
	 * <p> Method:  rotateRight
	 * <p> Description:  given a parent node, this function rotates it with the child to
	 * 	its left. the child becomes the parent, and the parent becomes the child's
	 * 	new right child. the parent's left child become's the child's original right.
	 * 	resets the relationships as well. 
	 * @param parent: the parent node, when tree is rotated right, this node is 
	 * 	being rotated with its left child
	 */
	private void rotateRight(RedBlackNode<AnyType> parent){ //pass parent/grandparent node!
		//q.left = p
		//p.right = original q.left
		RedBlackNode<AnyType> child = parent.left;

		//replacing root
		if (parent.parent == null)
			root = child;
		else {
			if (parent == parent.parent.left)
				//parent is grandparent's left child
				parent.parent.left = child;
			else
				//parent is grandparent's right child
				parent.parent.right = child;}
		
		//*reset the parent* to the grandparent
		if (child!=null)
			child.parent = parent.parent;
		
		//*replace parent's left* with child's right
		parent.left = child.right;
		
		//*reset the parent* of the child's right child
		if(child.right!=null)
			child.right.parent = parent;
		
		//parent becomes child's right 
		child.right = parent;
	}

	/**
	 * <p> Method:  rotateLeft
	 * <p> Description:  given a parent node, this function rotates it with the child to
	 * 	its right. the child becomes the parent, and the parent becomes the child's
	 * 	new left child. the parent's right child become's the child's original left.
	 * 	resets the relationships as well. 
	 * @param parent: the parent node, when tree is rotated left, this node is 
	 * 	being rotated with its right child
	 */
	private void rotateLeft(RedBlackNode<AnyType> parent){ //same as left, but opposite direction
		//q.left = p
		//p.right = original q.left
		RedBlackNode<AnyType> child = parent.right;

		if (parent.parent == null)
			root = child;
		else {
			if (parent == parent.parent.left)
				//parent is grandparent's left child
				parent.parent.left = child;
			else
				//parent if grandparent's right child
				parent.parent.right = child;}
		
		//*reset the parent* to the grandparent
		if (child!=null)
			child.parent = parent.parent;
		
		//replace parent's left with child's right
		parent.right = child.left;
		
		//*reset the parent* of the child's left child
		if(child.left!=null)
			child.left.parent = parent;
		
		//the parent becomes the left chld of the child
		child.left = parent;
	}

	/**
	 *<p> Method:  printRoot
	 *<p> Description:  prints the first item in the current tree 
	 */
	public void printRoot() {
		if (root!=null)
			System.out.print("This tree starts with " + root.getElement());
		else
			System.out.println("This tree has no nodes");
	}


	/**
	 * <p> Method:  printTree
	 * <p> Description:  bootstrap method that calls the recursive print tree function
	 * 	and gives it the first node (root) of this tree
	 */
	public void printTree(){
		if (root!=null)
			printTree(root);
		else
			System.out.println("This tree is empty\n");
	}

	/**
	 * <p> Method:  printTree
	 * <p> Description:  recursive function that starts at the given root
	 * 	and prints the tree in infix order
	 * @param t: the root at which to start the printing
	 */
	private void printTree(RedBlackNode<AnyType> t){
		if (t.left!=null) printTree(t.left);
		if (t!=null) System.out.print(t.getElement() + "\n");
		if (t.right!=null) printTree(t.right);
	}

	
	/**
	 * <p> Method:  contains
	 * <p> Description:  bootstrap function that checks that the tree's
	 * 	root and the item given are not null, then calls the recursive contains
	 * 	function. returns true if the given item is in the tree, false otherwise
	 * @param item: the item for which to search
	 * @return item in tree ? true : false
	 */
	public boolean contains(AnyType item){
		if (root == null) return false;
		
		if (item == null) return false;
		
		else return contains(item, root);

	}
	
	/**
	 * <p> Method:  contains
	 * <p> Description:  recursive function that traverses the tee,
	 * 	comparing each node to the given object.
	 * 	returns true if the given item is in the tree, false otherwise
	 * @param item: the item for which to search
	 * @param aRoot: the root at which to start the search
	 * @return item in tree ? true : false
	 */
	private boolean contains(AnyType item, RedBlackNode<AnyType> aRoot){
		if (aRoot == null)
			return false;

		int compareTo = item.compareTo(aRoot.getElement()); // use custom compare function

		if (compareTo < 0)
			return contains(item, aRoot.left);
		else if (compareTo > 0)
			return contains(item, aRoot.right);
		//Match found
		else
			return true;
	}

	/**
	 * <p> Method:  contains
	 * <p> Description:  bootstrap function that checks that the tree's
	 * 	root and the item given are not null, then calls the recursive retreiveIfItContains
	 * 	function. returns the item if it is in the tree, null otherwise
	 * @param item: the item for which to search
	 * @return item or null
	 */
	public AnyType retreiveIfItContains(AnyType item) {
		if (item == null)
			return null;
		else if (this.root == null)
			return null;
		//Ok to call recursive function
		else
			return retrieveIfItContains(root, item);
	}

	/**
	 * <p> Method:  contains
	 * <p> Description:  recursive function that traverses the tree,
	 * 	comparing each node to the given item.
	 * 	returns the item if it is in the tree, null otherwise
	 * @param item: the item for which to search
	 * @param aRoot the root at which to begin the search
	 * @return item or null
	 */
	private AnyType retrieveIfItContains(RedBlackNode<AnyType> aRoot, AnyType item){
		//Reached a leaf, no match found
		if (aRoot == null)
			return null;
		
		int compareTo = item.compareTo(aRoot.getElement());

		if (compareTo < 0)
			return retrieveIfItContains(aRoot.left, item);
		else if (compareTo > 0)
			return retrieveIfItContains(aRoot.right, item);
		//Match found
		else
			return aRoot.getElement();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		if (root!=null)
			return "This tree starts with " + root.getElement();
		else
			return "This tree has no nodes";
	}
	
	
//	public static void main(String[] args){
//		RedBlackTree<Integer> intTree = new RedBlackTree<Integer>();
//		intTree.insert(new Integer(5));
//		intTree.insert(new Integer(3));
//		intTree.insert(new Integer(7));
//		intTree.insert(new Integer(4));
//		intTree.insert(new Integer(2));
//		intTree.insert(new Integer(1));
//		intTree.insert(new Integer(0));
//		
//		intTree.printTree();
//		
//		System.out.println(intTree.getRoot().isBlack());
//	}

}