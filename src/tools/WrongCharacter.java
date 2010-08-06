/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tools;

/**
 * 
 * @author Miki
 */
public class WrongCharacter extends Throwable {

	char t;

	WrongCharacter(char c) {
		super();
		t = c;
	}

	public char getCharacter() {
		return t;
	}
}
