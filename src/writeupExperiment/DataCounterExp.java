/**
 *  @author Mengyuan Hunang, Ruijia Wang
 * CSE 332 
 * PROJECT 2 PHASE B
 * 
 * this is the DataCounter experiment to test different dataCounter 
 * implementation and calculate average runtime of each implementation.
 * the argument is in the form of:
 * 	[-b, -m, -a, -h, -h1] [file]
 */
package writeupExperiment;

import java.io.IOException;

import phaseA.*;
import phaseB.HashTable;
import phaseB.StringHasher;
import providedCode.*;


public class DataCounterExp {
	  public static  final int NUM_TESTS = 100;
	  public static final int NUM_WARMUP = 50;
	
	/**
	 * choose the right implementation from argument.
	 * @param args
	 */
	  public static void main(String[] args){
		  System.out.println( getAverageRuntime(args));
	  }
	  

	  private static double getAverageRuntime(String[] args) {
		    double totalTime = 0.0;
		    for (int i=0; i<NUM_TESTS; i++) {
		      long startTime = System.currentTimeMillis();
		      WordCount.main(args);
		      long endTime = System.currentTimeMillis();
		      if (NUM_WARMUP <= i) {                    // Throw away first NUM_WARMUP runs to exclude JVM warmup
		        totalTime += (endTime - startTime);
		      }
		    }
		    return totalTime / (NUM_TESTS - NUM_WARMUP);  // Return average runtime.
		  }
}