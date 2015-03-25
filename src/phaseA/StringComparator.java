/**
 * @author Mengyuan Huang, Ruijia Wang
 * CSE 332 
 * PROJECT 2 PHASE A
 * This string comparator is used by the provided
 * code for both dataCounters and sorting. It will
 * compare two strings by their ASCII code.
 */

package phaseA;
import providedCode.*;



public class StringComparator implements Comparator<String>{
	
	/**
	 * @return return positive number if the first string is 
	 * considered larger than the second string and vice versa.
	 * If two Strings have same characters and order at first, 
	 * we consider the longer string larger. If a string is empty,
	 * we consider it the largest.
	 */
	@Override
	public int compare(String s1, String s2) {
		if(s1 == null && s2 == null) {
			return 0;
		} else if(s1 == null && s2 != null){
			return 1;
		} else if(s1 != null && s2 == null){
			return -1;
		}
		for(int i = 0; i < Math.min(s1.length(), s2.length()); i++){
			if(s1.charAt(i) != s2.charAt(i)){
				return s1.charAt(i) - s2.charAt(i);
			}
		}
		return s1.length() - s2.length();
	}
}

