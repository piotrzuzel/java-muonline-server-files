package com.google.code.openmu.gs.muObjects;

/**
 * 
 * @author kinasiewicz10168342 MuViewPoint The data structure to hold distance
 *         between two objects, State of view and type
 */
public class MuViewPoint {
	/**
	 * 
	 * @author mikiones State of ViewPoint
	 */
	public enum State {
		S_Empty, S_ToForget, S_New, S_Known;
	}

	/**
	 * Sate of view point
	 */
	public State c_State;
	// TODO for what the hell is it ?
	public short c_Number;
	/**
	 * MuObject type
	 */
	public short o_Type;
	/**
	 * MuObject ID
	 */
	public short o_Index;
	/**
	 * Distance betwen owner of ViewPOintSet to Object from ViewPoint
	 */
	public double o_Dist;

	public MuViewPoint() {
		c_State = State.S_Empty;
		c_Number = 0;
		o_Index = 0;
		o_Type = 0;
		o_Dist = 0;
	}

	@Override
	/**
	 *  for printf
	 */
	public String toString() {
		String t = String.format(
				"Obj[Index:%d,Type%d] in distance [%f],State:[", o_Index,
				o_Type, o_Dist);
		t += c_State + "]";
		return t;
	}

}
