/**
 *  @author Mengyuan Hunang, Ruijia Wang
 * CSE 332 
 * PROJECT 2 PHASE B
 * 
 * An executable that counts the words in a files and prints out the counts in
 * descending order.
 */
package main;
import java.io.IOException;

import phaseA.*;
import providedCode.*;
import phaseB.*;

public class WordCount {
	
	/**
	 * Takes a text file and computes the frequency of all unique words in the file.
	 * @param file file for count
	 * @param counter a data structure to hold words and its associated value.
	 */
    private static void countWords(String file, DataCounter<String> counter) {
        try {
            FileWordReader reader = new FileWordReader(file);
            String word = reader.nextWord();
            while (word != null) {
                counter.incCount(word);
                word = reader.nextWord();
            }
        }catch (IOException e) {
            System.err.println("Error processing " + file + " " + e);
            System.exit(1);
        }
    }
    
    /**
     * Returns an array that hold the number of occurrences of a particular word at each index
     * @param counter that contain words and its associated values.
     * @return an array of dataCount objects that containing each unique word.
     */
	@SuppressWarnings("unchecked")
 	private static <E> DataCount<E>[] getCountsArray(DataCounter<E> counter) {
 		DataCount<E>[] array = (DataCount<E>[]) new  DataCount[counter.getSize()];
		SimpleIterator<DataCount<E>> itr = counter.getIterator();
		int i = 0;
		while (itr.hasNext()) {
			array[i] = itr.next();
			i++;
		}
		return array;
 	}
    
 	
    // IMPORTANT: Used for grading. Do not modify the printing *format*!
    // Otherwise you may modify this method (its signature, or internals) 
    // if you want.
    private static void printDataCount(DataCount<String>[] counts) {
    	for (DataCount<String> c : counts) {
            System.out.println(c.count + "\t" + c.data);
        }
    }
    
    /**
     * print the first k elements of the given array.
     * If the array length is less than k, print the entire array.
     * @param counts array to be printed
     * @param k the first k elements.
     */
    private static void printDataCountTopK(DataCount<String>[] counts, int k){
    	for(int i = 0; i < k; i++){
    		System.out.println(counts[i].count + "\t" + counts[i].data);
    	}
    }
   
    /**
     * print out error message and exit if the argument is not in proper form.
     */
    private static void errorMessage(){
        System.err.println("incorrect number of arguments.");
        System.err.println("Usage: ");
        System.err.println
        ("java WordCount [ -b | -a | -m | -h ] [ -is | -hs | -os | -k <number>] <filename>");
        System.exit(1);
    }
    
    /**
     * Read the arguments and uses a specified data structure and a specified sorting method
     * to print out all the unique words in a specified text file and their frequency in that file.
     */
    public static void main(String[] args) {
        // pre: the argument should in the form of :
    	// "java WordCount [ -b | -a | -m | -h ] [ -is | -hs | -os | -k <number>] <filename>"
    	// Otherwise print error message and exit.
    	if (args.length != 3 && args.length != 4) {
            errorMessage();
        }

        DataCounter<String> counter = null;
        
        /*
         * use different DataCounter implementation according to
    	 * the argument.
         */
        if (args[0].compareTo("-b") == 0) {
        	counter = new BinarySearchTree<String>(new StringComparator());
        } else if (args[0].compareTo("-a") == 0) {
        	counter = new AVLTree<String>(new StringComparator());
        } else if(args[0].compareTo("-m") == 0) {
        	counter = new MoveToFrontList<String>(new StringComparator());
        }else if(args[0].compareTo("-h") == 0){
        	counter = new HashTable<String>(new StringComparator(), new StringHasher());
        } else{
        	errorMessage();
        }
        
        
        /*
         * Use different sorting routing according to the argument.
         * Print out the result.
         */
        if(args[1].compareTo("-k") == 0){
        	countWords(args[3], counter);
            DataCount<String>[] counts = getCountsArray(counter);
        	int k = Integer.parseInt(args[2]);
	        Sorter.topKSort(counts, new compareTopK(), k);
	        printDataCountTopK(counts, k);
        } else {
        	countWords(args[2], counter); 
            DataCount<String>[] counts = getCountsArray(counter);
	        if (args[1].compareTo("-is") == 0) {
	        	Sorter.insertionSort(counts, new DataCountStringComparator());
	        } else if (args[1].compareTo("-hs") == 0){
	        	Sorter.heapSort(counts, new DataCountStringComparator());
	        } else if(args[1].compareTo("-os") == 0){
	        	Sorter.otherSort(counts, new DataCountStringComparator());
	        } else {
	        	errorMessage();
	        }
	       	printDataCount(counts);
        }
    }
}

