/**
 *  @author Mengyuan Hunang, Ruijia Wang
 * CSE 332 
 * PROJECT 2 PHASE B
 * 
 * StringHasher1 implements the Hasher interface converting a string to a integer.
 * Different integers represent different strings.
 * Same integers represent same strings.
 */

package writeupExperiment;
import providedCode.Hasher;

public class StringHasher1 implements Hasher<String> {
	
	@Override
	public int hash(String s) {
		int value = 0;
		for (int i = 0; i < s.length(); i++) {
		  value += s.charAt(i) * 127 * (s.length() - i);
		}
		return Math.abs(value);
	}
}
