/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tools;

/**
 * 
 * @author Miki i Linka
 */
public class MySingleton {
	protected MySingleton() {
	}

	private static MySingleton _instane = null;

	public MySingleton getInstance() {
		if (_instane == null) {
			_instane = new MySingleton();
		}
		return _instane;
	}

}
