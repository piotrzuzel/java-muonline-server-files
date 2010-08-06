/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tools;

/**
 * 
 * @author Miki i Linka
 */
public abstract class IConsoleObiect {
	private String _Name;
	private String _Desc;
	protected int _types;

	protected IConsoleObiect() {
	};

	public IConsoleObiect(String _name, String _desc) {
		_Name = _name;
		_Desc = _desc;
	};

	public String getDesc() {
		return _Desc;
	}

	public String getName() {
		return _Name;
	}

	public abstract void PrintInfo();

	public abstract void update(String param);

}
