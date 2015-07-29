package proj3;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author rachaelbirky
 * @version 04.04.14
 * 
 * <p>This class processes a text file with the format
 * 	Node [word="word", frequency=x] (where x is an integer) and hashes the results.
 * 	It contains a table of self-balancing (red black) binary trees. The trees contain
 * 	nodes with the first two letters of any word found. The object storing the first two
 * 	letters also contains a heap of the actual words that start with those letters,
 * sorted by frequency
 *
 * @param <AnyType> this class is generic
 */
public class HashedRBTs<AnyType> {

	boolean debug = false;
	Scanner infile;
	private ArrayList<RedBlackTree<Partial>> table;
	private int ASCII_A = 65;
	private int ASCII_Z = 90;
	private int CAPITAL_SHIFT = 65;
	private int LOWERCASE_SHIFT = 71;
	
	/**
	 * HashedRBT:
	 * constructor, creates a new instance of 
	 * 	this class with its own table of red black trees containing partials
	 * 	containing heaps
	 * @param size: the desired size of the hash table
	 */
	public HashedRBTs(int size){
		table = new ArrayList<RedBlackTree<Partial>>(size);
		
		for (int i=0; i<size; i++)
			table.add(new RedBlackTree<Partial>());
	}

	/**
	 * <p> Method:  fileReader
	 * <p> Description:  parses the given file to extract each word and frequency.
	 * 	converts the ASCII value of the first letter to the appropriate
	 * 	index of the hash table. accesses the tree at that index and checks
	 * 	to see if there is an existing partial into which the word node can be
	 * 	added. If one does not exist, it creates a new one with the word.
	 * @param filename: file to be parsed
	 */
	public void fileReader(String filename){
		//Get file
		try {
			infile = new Scanner(new FileReader(filename));
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
			e.printStackTrace();
			System.exit(0);
		}
		
		while(infile.hasNextLine()){
			String nextLine = infile.nextLine();
			if (!nextLine.equalsIgnoreCase("Empty tree")){
				
				String[] line = nextLine.replaceAll("Node \\[word", "").replaceAll(", frequency", "").replaceAll("\\]", "").split("=");
				
				String word = line[1];
				int frequency = Integer.parseInt(line[2]);
				Node newNode = new Node(word, frequency);

				int charIndex = (int) word.charAt(0);
				if (charIndex >= ASCII_A && charIndex <= ASCII_Z)
					charIndex-=CAPITAL_SHIFT;
				else
					charIndex-=LOWERCASE_SHIFT;

				//Get Tree
				RedBlackTree<Partial> current = table.get(charIndex);

				//make the partial for which to search
				Partial newPartial = new Partial(newNode);

				//check for partial in tree; comparison only worries about the first two letters contained
				boolean contains = current.contains(newPartial);
				
				//if exists, add to it
				if (contains){
					if (debug){
						System.out.println("Found existing Partial for \""+word+"\"");
					}
					Partial temp = current.retreiveIfItContains(newPartial);
					temp.insertNodeIntoHeap(newNode);
				}
				//if not, create new (this works fine)
				else{
					if (debug) System.out.println("Inserting new word node with \""+word+"\"");
					current.insert(newPartial);
				}
			}	
		}
	}

	/**
	 * <p> Method:  printHashCountResults
	 * <p> Description:  prints the root of each tree in the hash table
	 */
	public void printHashCountResults(){
		for (int i=0; i<table.size(); i++){
			table.get(i).printRoot();
		}
	}
	
	/**
	 * <p> Method:  retreiveHashedRBTat
	 * <p> Description:  when given an integer, this function returns the
	 * 	binary tree stored in that index of the hash table
	 * @param i: index of tree to be returned
	 * @return the tree at index i
	 */
	public RedBlackTree<Partial> retreiveHashedRBTat(int i) {
		return table.get(i);
	}
	
}