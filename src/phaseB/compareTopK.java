/**
 *  @author Mengyuan Hunang, Ruijia Wang
 * CSE 332 
 * PROJECT 2 PHASE A
 * 
 * a new comparator for DataCounter<String>that sorts two
 * data-counts in the order we need for topKSort: 
 * It will consider the element with lowest count to be 
 * the smallest. Ties are resolved in alphabetical order.
 */
package phaseB;
import phaseA.*;
import providedCode.Comparator;
import providedCode.DataCount;

public class compareTopK implements Comparator<DataCount<String>>{

	StringComparator compareTie = new StringComparator();
	public int compare(DataCount<String> s1, DataCount<String> s2){
		if(s1.count != s2.count){
			return s1.count - s2.count;
		}
		return compareTie.compare(s2.data, s1.data);
	}
}
