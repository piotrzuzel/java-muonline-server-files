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

package com.google.code.openmu.gs.muObjects;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import com.google.code.openmu.gs.IdFactory;


import javolution.util.FastMap;

/**
 * @author Miki, Marcel
 * Base class for every visible object in the MuWorld.
 */
public class MuObject {

	private MuMap _map;
	private short _objectId;
	protected short _objectType; // 00 player // 1 npc // 2 mob // 3 item // 4 ?
	protected short _coordX;// y
	protected short _coordY;// y
	private short _mapIndex;// map;
	private short _status; // status

	/**
	 * Constructor for the MuObject class.
	 */
	public MuObject() {
	}

	/**
	 * Constructor for the MuObject class.
	 * @param obiectId Unique identified for the object.
	 * @param coordX The x coordinate of the object's place on map.
	 * @param coordY The y coordinate of the object's place on map.
	 * @param mapIndex The index of the map in which the object is placed.
	 */
	public MuObject(short obiectId, short coordX, short coordY, short mapIndex) {
		super();
		this._objectId = obiectId;
		this._coordX = coordX;
		this._coordY = coordY;
		this._mapIndex = mapIndex;
	}

	/**
	 * @param s
	 *            change status on s
	 */
	public void changeStatus(int s) {
		_status = (short) s;
	}

	/**
	 * get actual map
	 * 
	 * @return return actual map
	 */
	public MuMap getCurrentWorldRegion() {
		return _map;
	}

	public int getCurrentMuMapPointX() {
		return _coordX / 3;
	}

	public int getCurrentMuMapPointY() {
		return _coordY / 3;
	}

	/**
	 * @return Bytecode of map
	 */
	public int getM() {
		return _mapIndex;
	}

	public short getMyType() {
		return _objectType;
	}

	/**
	 * @return Obiect Id
	 */
	public int getObjectId() {
		return _objectId;
	}

	/**
	 * @return Status of object like where look for character and mobs
	 */
	public short getStatus() {
		return _status;
	}

	/**
	 * @return wsp x
	 */
	public int getX() {
		return _coordX;
	}

	/**
	 * @return wsp y
	 */
	public int getY() {
		return _coordY;
	}

	/**
	 * 
	 * @return isvisibable on map ?
	 */
	public boolean isVisible() {
		return true;
	}

	/**
	 * method set actual region
	 * 
	 * @param Actual
	 *            regio to set
	 */
	public void setCurrentWorldRegion(MuMap region) {
		if (_map != null) {
			_map.removeObject(this);
		}
		_map = region;
		_map.addObject(this);
	};

	/**
	 * ustawia mape = m
	 * 
	 * @param m
	 */
	public void setM(byte m) {
		_mapIndex = m;
	}

	/**
	 * ustawia id obiektu na id
	 * 
	 * @param id
	 */
	public void setObiectId(short id) {
		_objectId = id;
	}

	/**
	 * method to rand new id
	 */
	public void RandObjectId() {
		_objectId = (short) IdFactory.getInstance().newId();
	}

	/**
	 * @param s
	 *            - status czyli gdzie obiekt patrzy itp
	 */
	public void setStatus(int s) {
		_status = (short) s;
	}

	/**
	 * ustawia x na _newx
	 * 
	 * @param _newx
	 */
	public void setX(int _newx) {
		_coordX = (short) _newx;
	}

	/**
	 * Set new Y pos
	 * 
	 * @param _newy
	 */
	public void setY(int _newy) {
		_coordY = (short) _newy;
	}

	/**
	 * update maps after it changes
	 */
	public void updateCurrentWorldRegion() {
		final MuMap newRegion = MuWorld.getInstance().getMap(getM());
		if (!newRegion.equals(_map)) {
			if (_map != null) {
				_map.removeObject(this);
			}
			newRegion.addObject(this);
			_map = newRegion;
		}
	}

	/**
	 * Fast set location inf on map
	 * 
	 * @param x
	 *            x wsp
	 * @param y
	 *            wsp
	 * @param f
	 *            where look
	 */
	public void SetPos(int x, int y, int f) {
		setX(x);
		setY(y);
		setStatus(f);
	}

	@Override
	public String toString() {
		return "[" + getM() + "][" + getObjectId() + "][" + getX() + ","
				+ getY() + "][" + getClass().getSimpleName() + "]";
	}
}
