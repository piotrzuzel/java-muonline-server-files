/*
 * Copyright [mikiones] [Michal Kinasiewicz]
 * 			 [marcel]   [Marcel Gheorghita] 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.code.openmu.gs.muObjects.Position;

import com.google.code.openmu.gs.muObjects.MuMap;

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
