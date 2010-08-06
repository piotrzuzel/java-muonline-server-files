/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.jmuserver.gs.muObjects;

/**
 * 
 * @author Miki i Linka
 */
public class MuGate {
	public static final int FlagNoGate = 0x00; // to telepowrt without enter to
												// gate
	public static final int FlagGat1 = 0x01; // Go to
	public static final int FlagGat2 = 0x02;// Go Back
	public static final int FlagGat3 = 0x03;// ??

	private int _gateNb;
	private int _flag;
	private int _Map;
	private int _x1;
	private int _x2;
	private int _y1;
	private int _y2;
	private int _toGateNb;
	private int _HeadDir;
	private int _lvlRequared;

	public MuGate(int _gateNb, int _flag, int _Map, int _x1, int _y1, int _x2,
			int _y2, int _toGate, int _HeadDir, int _lvlRequared) {
		this._gateNb = _gateNb;
		this._flag = _flag;
		this._Map = _Map;
		this._x1 = _x1;
		this._x2 = _x2;
		this._y1 = _y1;
		this._y2 = _y2;
		this._toGateNb = _toGate;
		this._HeadDir = _HeadDir;
		this._lvlRequared = _lvlRequared;
	}

	public int getHeadDir() {
		return _HeadDir;
	}

	public void setHeadDir(int _HeadDir) {
		this._HeadDir = _HeadDir;
	}

	public int getFlag() {
		return _flag;
	}

	public void setFlag(int _flag) {
		this._flag = _flag;
	}

	public int getMap() {
		return _Map;
	}

	public void setFromMap(int _Map) {
		this._Map = _Map;
	}

	public int getGateNb() {
		return _gateNb;
	}

	public void setGateNb(int _gateNb) {
		this._gateNb = _gateNb;
	}

	public int getLvlRequared() {
		return _lvlRequared;
	}

	public void setLvlRequared(int _lvlRequared) {
		this._lvlRequared = _lvlRequared;
	}

	public int getToGate() {
		return _toGateNb;
	}

	public void setTogate(int _ToGateNb) {
		this._toGateNb = _ToGateNb;
	}

	public int getX1() {
		return _x1;
	}

	public void setX1(int _x1) {
		this._x1 = _x1;
	}

	public int getX2() {
		return _x2;
	}

	public void setX2(int _x2) {
		this._x2 = _x2;
	}

	public int getY1() {
		return _y1;
	}

	public void setY1(int _y1) {
		this._y1 = _y1;
	}

	public int getY2() {
		return _y2;
	}

	public void setY2(int _y2) {
		this._y2 = _y2;
	}

	/**
	 * 
	 * @return the object of map where going gate
	 */
	public MuMap getDestMap() {
		return MuWorld.getInstance().getMap(
				MuWorld.getInstance().getGate(_toGateNb)._Map);
	}

	/**
	 * get object map from going map
	 * 
	 * @return
	 */
	public MuMap FromMap() {
		return MuWorld.getInstance().getMap(_Map);
	}

}
