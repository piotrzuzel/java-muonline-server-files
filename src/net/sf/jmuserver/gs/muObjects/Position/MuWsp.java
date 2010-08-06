/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.jmuserver.gs.muObjects.Position;

import net.sf.jmuserver.gs.muObjects.MuMap;

/**
 * 
 * @author Miki i Linka
 */
public class MuWsp {

	private MuMap _worldRegion; // the World region[map]
	private short _x; // X position on map
	private short _y; // y position map;
	private byte _headDiretion;// head direction

	MuWsp(int upx, int upy, byte direction) {
		_x = (short) upx;
		_y = (short) upy;
		_headDiretion = direction;
	}

	/**
	 * 
	 * @return the curent World Region
	 */
	public MuMap getCurrentWorldRegion() {
		return _worldRegion;
	}

	/**
	 * consstructor for muWsp
	 * 
	 * @param _x
	 *            the X position On Map;
	 * @param _y
	 *            the Y position On Map;
	 * @param _headDiretion
	 *            WHere looking ;
	 */
	public MuWsp(short _x, short _y, byte _headDiretion) {
		this._x = _x;
		this._y = _y;
		this._headDiretion = _headDiretion;
	}

	/**
	 * Set basicposition on map
	 * 
	 * @param x
	 * @param y
	 */
	public void setWsp(short x, short y) {
		_x = x;
		_y = y;
	}

	/**
	 * settings position on map and head pos
	 * 
	 * @param x
	 * @param y
	 * @param look
	 *            head position
	 */
	public void setWsp(short x, short y, byte look) {
		_x = x;
		_y = y;
		_headDiretion = look;
	}

	/**
	 * 
	 * @return the X pos on map
	 */
	public short getX() {
		return _x;
	}

	/**
	 * 
	 * @return Y pos on Map
	 */
	public short getY() {
		return _y;
	}

	/**
	 * The Head Posbits
	 * 
	 * @return
	 */
	public byte getHeadPos() {
		return _headDiretion;
	}

	@Override
	public String toString() {
		return super.toString();
	}

	public void add(MuWsp what) {
		_x += what.getX();
		_y += what.getY();
		_headDiretion = what.getHeadPos();
	}
}
