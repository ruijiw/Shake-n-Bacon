/**
 *  @author Mengyuan Hunang, Ruijia Wang
 * CSE 332 
 * PROJECT 2 PHASE B
 * 
 * StringHasher implements the Hasher interface converting a string to a integer.
 * Different integers represent different strings.
 * Same integers represent same strings.
 */

package phaseB;
import providedCode.Hasher;

public class StringHasher implements Hasher<String> {
	
	@Override
	public int hash(String s) {
		int value = 0;
		for (int i = 0; i < s.length(); i++) {
		  value = value * 31 + s.charAt(i);
		}
		return Math.abs(value);
	}
}
