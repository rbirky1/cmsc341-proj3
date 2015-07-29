package proj3;

/**
 * @author rachaelbirky
 * @version 04.04.14
 * @section 01
 * 
 *<p> Description:  This class represents a binary node with extra variables
 *	to represent a color of black or red, so it is usable in a red black tree.
 *	It contains references to its left and right children, as well as its parent.
 *	The item it contains is stored in "element"
 *
 * @param <AnyType> this class is generic
 */
public class RedBlackNode<AnyType> {

	private AnyType element;
	
	private boolean RED = true;
	private boolean BLACK = false;
	
	RedBlackNode<AnyType> left;
	RedBlackNode<AnyType> right;
	RedBlackNode<AnyType> parent;
	
	/**
	 * <p> Method:  RedBlackNode
	 * <p> Description:  constructor; creates new RedBlackNode with the given attributes
	 * @param aElement: the item to store
	 * @param aLeft: the left child
	 * @param aRight: the right child
	 * @param aParent: the parent of the new node
	 */
	RedBlackNode(AnyType aElement, RedBlackNode<AnyType> aLeft, RedBlackNode<AnyType> aRight, RedBlackNode<AnyType> aParent){
		this.element = aElement;
		this.left = aLeft;
		this.right = aRight;
		this.parent = aParent;
	}

	
	/**
	 * <p> Method:  RedBlackNode
	 * <p> Description:  alternate/default constructor that creates a new node with the given element
	 * 	and null children and parent pointers 
	 * @param aElement: the item to store
	 */
	RedBlackNode(AnyType aElement){
		this(aElement, null, null, null);
	}
	

	/**
	 * <p> Method:  getElement
	 * @return returns the item stored in this node
	 */
	public AnyType getElement(){
		return this.element;
	}
	
	/**
	 * <p> Method:  getGrandparent
	 * @return calculates and returns the node's parent's parent (grandparent) if it exists.
	 */
	public RedBlackNode<AnyType> getGrandparent(){
		if (this.parent!=null)
			{if(this.parent.parent!=null)
				return this.parent.parent;
			else return null;}
		else return null;
	}
	
	/**
	 * <p> Method:  getUncle
	 * @return calculates and returns the node's parent's sibling (uncle) if it exists.
	 */
	public RedBlackNode<AnyType> getUncle(){
		if (this.parent!=null)
			{if (this.getGrandparent()!=null)
				if (this.parent == this.getGrandparent().right)
					return this.getGrandparent().left;
				else
					return this.getGrandparent().right;
			}
		return null;
	}
	
	/**
	 * <p> Method:  isRed
	 * @return true if the node is red, false if it is black
	 */
	public boolean isRed(){
		return RED;
	}
	
	/**
	 * <p> Method:  isBlack
	 * @return true if the node is black, false if it is red
	 */
	public boolean isBlack(){
		return BLACK;
	}
	
	/**
	 *<p> Method:  makeRed
	 *<p> Description:  changes the node's color to red and verifies it is not tagged as black 
	 */
	public void makeRed(){
		RED = true;
		BLACK = false;
	}
	
	/**
	 *<p> Method:  makeBlack
	 *<p> Description:  changes the node's color to black and verifies it is not tagged as red
	 */
	public void makeBlack(){
		RED = false;
		BLACK = true;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		return element.toString();
	}
}