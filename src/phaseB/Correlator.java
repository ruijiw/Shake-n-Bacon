/**
 *  @author Mengyuan Hunang, Ruijia Wang
 * CSE 332 
 * PROJECT 2 PHASE B
 * 
 * This is a simple approach to qualify the similarity between
 * two text files. The Correlator class will calculate the normalized 
 * frequency of each word in the file that is between 0.01% to 1%. 
 * Then it will square the difference between frequencies to compute 
 * the difference metric. Adding all the metric together to 
 * get the variance and print it out.
 */

package phaseB;

import java.io.IOException;

import phaseA.AVLTree;
import phaseA.MoveToFrontList;
import phaseA.StringComparator;
import providedCode.BinarySearchTree;
import providedCode.DataCount;
import providedCode.DataCounter;
import providedCode.FileWordReader;
import providedCode.SimpleIterator;


public class Correlator {
	/*
	 * normalized frequency should be between 0.01% and 1%
	 */
	public static final double MIN = 0.0001;
	public static final double MAX = 0.01;
	
	/*
	 * print out error message and exit. 
	 */
    private static void errorMessage(){
        System.err.println("incorrect number of arguments.");
        System.err.println("Usage: ");
        System.err.println
        ("jjava Correlator [ -b | -a | -m | -h ] <filename1> <filename2>");
        System.exit(1);
    }
	
    /**
     * output the variance that represent the correlation between
     * two files.
     * @param args the argument in the form of
     * 			"[ -b | -a | -m | -h ] <filename> <filename>"
     */
    public static void main(String[] args) {
    	/*
    	 * pre: the argument should have length of 3
    	 * (print out error message and exit otherwise)
    	 */
    	if(args.length != 3){
    		errorMessage();
    	}
    	
    	/*
    	 * set up two DataCounter for two files.
    	 */
    	DataCounter<String> counter1 = null;
    	DataCounter<String> counter2 = null;
    	
    	/*
    	 * use different DataCounter implementation according to
    	 * the argument.
    	 */
        if (args[0].compareTo("-b") == 0) {
        	counter1 = new BinarySearchTree<String>(new StringComparator());
        	counter2 = new BinarySearchTree<String>(new StringComparator());
        } else if (args[0].compareTo("-a") == 0) {
        	counter1 = new AVLTree<String>(new StringComparator());
        	counter2 = new AVLTree<String>(new StringComparator());
        } else if(args[0].compareTo("-m") == 0) {
        	counter1 = new MoveToFrontList<String>(new StringComparator());
        	counter2 = new MoveToFrontList<String>(new StringComparator());
        } else{
        	errorMessage();
        }
    	 
    	// count total words and the frequency of each word.
        int totalWords1 = countWords(args[1], counter1);
        int totalWords2 = countWords(args[2], counter2);
    	
        /*
         * iterate through the first file. If the word in the first file
         * is also in the second file, calculate the normalized frequency 
         * in each file and square the difference between two frequencies.  
         * Adding the metric up to get the variance. Output the variance at last.
         * Ignore words whose normalized frequency is higher than 1% or lower than 0.01%.
         */
    	SimpleIterator<DataCount<String>> itr = counter1.getIterator();
    	double variance = 0.0;  
    	while(itr.hasNext()){
    		String word = itr.next().data;
    		int count1 = counter1.getCount(word);
    		int count2 = counter2.getCount(word);
    		if(count1 > 0 && count2 > 0){
    			double freq1 = (double) count1 / totalWords1;
    			double freq2 = (double) count2 / totalWords2;
    			if(freq1 > MIN && freq1 < MAX && freq2 > MIN && freq2 < MAX){
    				variance += Math.pow((freq1 - freq2), 2);
    			}
    		}
    	}
    	
    	
    	System.out.println(variance);  
    }
    
    
    /**
     * Takes a text file and computes the frequency of all unique words in the file.
     * return the total words in the file.
     * @param file the given text file
     * @param counter DataCounter to count the data.
     * @return total words in the file.
     */
    private static int countWords(String file, DataCounter<String> counter) {
        int totalWords = 0;
    	try {
            FileWordReader reader = new FileWordReader(file);
            String word = reader.nextWord();
            while (word != null) {
                counter.incCount(word);
                totalWords++;
                word = reader.nextWord();
            }
        }catch (IOException e) {
            System.err.println("Error processing " + file + " " + e);
            System.exit(1);
        }
    	return totalWords;
    }
}
















