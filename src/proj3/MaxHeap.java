package proj3;

/**
 * @author rachaelbirky
 * @version 04.04.14
 * @section 01
 * 
 *<p> Description:  This class represents a binary heap, using array implementation,
 *	where the maximum value of the heap is at the root. Access is constant time.
 *
 * @param <AnyType> this class is generic
 */
public class MaxHeap<AnyType extends Comparable<? super AnyType>> {

	private AnyType[] heap;
	private int currentSize;
	private final int INITIAL_CAPACITY = 10;


	/**
	 * <p> Method:  MaxHeap()
	 * <p> Description:  creates a new Maximum Heap with an initial capacity of 10
	 * 	and zero items (size of zero)
	 */
	public MaxHeap(){
		currentSize = 0;
		heap = (AnyType[]) new Comparable[INITIAL_CAPACITY];
	}
	
	/**
	 * <p> Method:  insert
	 * <p> Description:  adds a new item to the end of the heap, then
	 * 	iteratively compares the value to its parents and swaps if necessary
	 * @param item: the new item added 
	 */
	public void insert(AnyType item) {
		//Reserve 0th index for sentinel value 
		if (currentSize == heap.length - 1)
		{growHeap(heap.length*2+1);}

		//start comparing at end of heap
		int tempIndex = ++currentSize;

		//Compare to Parent, at i/2; stop when tempIndex is at the "root"
		for(; tempIndex>1 && item.compareTo(heap[tempIndex/2]) > 0; tempIndex /= 2){
			//swap if less than parent
			heap[tempIndex] = heap[tempIndex/2];
		}

		//When place is found, insert item
		heap[tempIndex] = item;
	}

	
	/**
	 * <p> Method:  growHeap
	 * <p> Description:  increases the capacity of the heap to accomodate new items
	 * @param x: the amount by which to grow the heap
	 */
	private void growHeap(int x){
		AnyType [] oldHeap = heap;
		heap = (AnyType []) new Comparable[x];
		System.arraycopy(oldHeap, 1, heap, 1, currentSize);
	}

	/**
	 * <p> Method:  isEmpty
	 * <p> Description:  determines whether the heap is empty
	 * @return true if the current size is zero, false otherwise
	 */
	public boolean isEmpty(){
		return this.currentSize == 0;
	}

	/**
	 *<p> Method:  printImmediateOptions
	 *<p> Description:  prints the first three elements in the heap 
	 */
	public void printImmediateOptions(){
		System.out.println(heap[1] 
				+"\n"+ heap[2] 
						+"\n"+ heap[3]
								+"\n");
	}

	/**
	 *<p> Method:  printHeapRoot
	 *<p> Description:  prints the first item in the heap 
	 */
	public void printHeapRoot(){
		if (isEmpty())
			System.out.println("This heap is empty!\n");
		else
			System.out.println(heap[1]);
	}

	/**
	 *<p> Method:  printHeap
	 *<p> Description:  prints the items of the heap in infix order 
	 */
	public void printHeap(){
		System.out.println(this.toString());
	}

	/**
	 * <p> Method:  findMax()
	 * @decription returns the max item in the heap
	 * @return heap[1] the item with the greatest value
	 */
	public AnyType findMax(){
		return heap[1];
	}
	
	/**
	 * <p> Method:  makeEmpty
	 * <p> Description:  takes the current heap, and sets all objects to null
	 */
	public void makeEmpty(){
		while (currentSize>0)
			heap[currentSize--] = null;
	}
			
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		if (isEmpty())
			return "This heap is empty!\n";
		String returnString="";
		for (int i=1; i<=currentSize; i++)
			returnString+= ("["+i+"] " + heap[i]+"\n");
		return " --> The heap contains:\n" + returnString;
	}
	
	
	/**
	 * <p> Method:  MaxHeap
	 * <p> Description:  constructor for creating heap from array of items
	 * @param things: array of items to insert into heap
	 */
	public MaxHeap(AnyType[] things){
		currentSize = things.length;
		heap = (AnyType[]) new Comparable[things.length +1];
		for (int i=0; i<heap.length; i++)
			heap[i+1] = things[i];
		buildHeap();
	}
	
	/**
	 * <p> Method:  buildHeap
	 * <p> Description:  restructures the heap to maintain heap order
	 */
	private void buildHeap(){
		for(int i=currentSize/2; i > 0; i-- )
            shift(i);
	}
	
	/**
	 * <p> Method:  shift
	 * <p> Description:  given a starting index, re-arranges the items so the max is at the root
	 * @param tempIndex: the starting index for bubbling max item to root
	 */
	private void shift(int tempIndex){
		int hole;
		AnyType tempItem = heap[tempIndex];
		
		for (hole=tempIndex; hole>1 && (heap[tempIndex].compareTo(heap[hole/2])>0); hole/=2){
			heap[hole] = heap[hole/2];
		}
		heap[hole] = tempItem;
	}
}