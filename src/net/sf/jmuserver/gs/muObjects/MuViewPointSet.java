package net.sf.jmuserver.gs.muObjects;

import net.sf.jmuserver.gs.muObjects.MuViewPoint.State;

/**
 * 
 * @author mikiones Te class to represent view point of MuObjecton map. the
 *         class hold up to 75 meeting
 */
public class MuViewPointSet {
	/**
	 * Array of 75 see object not necessarily actual on view range
	 */
	private final MuViewPoint _vPort[] = new MuViewPoint[75];
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

}
