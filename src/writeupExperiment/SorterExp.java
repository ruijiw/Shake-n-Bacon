/**
 *  @author Mengyuan Hunang, Ruijia Wang
 * CSE 332 
 * PROJECT 2 PHASE B
 * 
 * This is the sorter experiment to compare different sorting algorithm.
 */
package writeupExperiment;

import java.io.IOException;

import main.Sorter;
import phaseA.*;
import phaseB.compareTopK;
import providedCode.*;


public class SorterExp {

	public static final int NUM_TEST = 100;
	public static final int NUM_WARMUP = 50;
	
	/**
	 * choose different sorting algorithm to compare.
	 * @param args
	 */
	public static void main(String[] args) {
		String file = "hamlet.txt"; //file to sort.
		//we use BST for sorting. It can be changed to other data structure.
		DataCounter<String> counter = new BinarySearchTree<String>(new StringComparator()); 
		countWords(file, counter);
		DataCount<String>[] counts = getCountsArray(counter);
		double average = getAverageRuntime(counts); // get the average runtime
		System.out.println(average);
	}
	
	/**
	 * Choose what sort you want to test runtime of and returns runtime for given sort.
	 * @param counts, array of counts of different words.
	 * @return the average runtime.
	 */
	private static double getAverageRuntime(DataCount<String>[] counts) {
		DataCount<String>[] temp = (DataCount<String>[]) new DataCount[counts.length];
		double totalTime = 0;
	    for(int i = 0; i < NUM_TEST; i++) {
	    	for(int j = 0; j < temp.length; j++) {
				temp[j] = counts[j];
			}
	    	long startTime = System.currentTimeMillis();
	    	//it can be changed to insertionSort, otherSort or heapSort.
        	Sorter.otherSort(counts, new DataCountStringComparator());
	    	
	    	long endTime = System.currentTimeMillis();
	    	if(NUM_WARMUP <= i) {                    // Throw away first NUM_WARMUP runs to encounter JVM warmup
	        totalTime += (endTime - startTime);
	    	}
	    }
	    return totalTime / (NUM_TEST-NUM_WARMUP);  // Return average runtime.
	}
	
	/**
	 * Takes a text file and computes the frequency of all unique words in the file.
	 * @param file, the file a client wants to get word count totals.
	 * @param counter data structure to hold words and associated values.
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
}