/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tools;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author Miki
 */
public class HexLineTest {

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		try {
			final HexLinePackage t = new HexLinePackage("dhc1 c2 11 c4 c5");
			final byte[] f = t.getContent();
			System.out.println(t.printData(f, f.length, ""));
		} catch (final IOException ex) {
			Logger.getLogger(HexLineTest.class.getName()).log(Level.SEVERE,
					null, ex);
		} catch (final WrongCharacter ex) {
			Logger.getLogger(HexLineTest.class.getName()).log(Level.SEVERE,
					null, ex);

		}
	}

}
