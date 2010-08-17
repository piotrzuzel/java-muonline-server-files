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

import com.google.code.openmu.gs.muObjects.MuViewPoint.State;

/**
 * 
 * @author mikiones Te class to represent view point of MuObjecton map. the
 *         class hold up to 75 meeting
 */
public class MuViewPointSet {
	/**
	 * Array of 75 see object not necessarily actual on view range
	 */
	private MuViewPoint _vPort[] = new MuViewPoint[75];
	/**
	 * count of used cells in array up to 75
	 */
	private int _vSize;
	/**
	 * Radius of see Area of object
	 */
	private final short _vRadius;
	/**
	 * Index of Game Object
	 */
	private final short _vIndex;

	public MuViewPointSet(short Index, short Rad) {
		_vSize = 0;
		_vRadius = Rad;
		_vIndex = Index;
	}

	/**
	 * 
	 * @return radius value when MuObject owner see
	 */
	public short getRadius() {
		return _vRadius;
	}

	/**
	 * 
	 * @return id of MuObject
	 */
	public short getIndex() {
		return _vIndex;
	};

	/**
	 * @return Actual max index used in _vPort
	 */
	public int getSize() {
		return _vSize;
	}

	/**
	 * Search ViewPortSet for specified Object Index and return the position in
	 * _vPort
	 * 
	 * @param Index
	 *            GameObject look {@linkplain MuObject}._ObiectId
	 * 
	 * @return position in _vPort if Index is not in _vPort the n return -1
	 */
	protected int searchIndex(short Index) {
		for (int i = 0; i < _vIndex; i++) {
			if (_vPort[i].o_Index == Index) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Add or update entry in ViewpOintSet
	 * 
	 * @param point
	 *            to update/add
	 */
	public void addViewPort(MuViewPoint point) {
		{
			final int pos = searchIndex(point.o_Index);
			if (pos == -1) // we do not known it yet
			{
				if (point.c_State == State.S_New) {
					_vSize++;
					_vPort[_vSize] = point;

				}
			} else if ((point.c_State == State.S_New)
					&& (_vPort[pos].c_State == State.S_Known)) {
				_vPort[pos].o_Dist = point.o_Dist;
			} else if ((point.c_State == State.S_ToForget)
					&& (_vPort[pos].c_State == State.S_Empty)) {
				_vPort[pos].o_Dist = point.o_Dist;
			} else {
				_vPort[pos] = point;
			}
		}
	}

	/**
	 * optymalise list to avoid to much free spaces
	 */
	public void Optymalise() {
		// skip if the list is not full up to 60 space;
		if (_vSize < 60) {
			MuViewPoint[] point = new MuViewPoint[75];
			int pos = 0;
			for (MuViewPoint t : _vPort) {
				if (t.c_State != State.S_Empty) {
					point[pos] = t;
					pos++;
				}
			}
			_vPort = point;
			_vSize = pos;
		}
	}
}
