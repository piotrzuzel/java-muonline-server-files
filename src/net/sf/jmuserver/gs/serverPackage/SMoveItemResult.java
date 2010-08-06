/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.jmuserver.gs.serverPackage;

/**
 * 
 * @author Marcel
 */
public class SMoveItemResult extends ServerBasePacket {

	private final byte[] _item;
	private final int _pos;
	private final int _wid;

	public SMoveItemResult(int Window, int Position, byte[] ItemHex) {
		_item = ItemHex;
		_pos = Position;
		_wid = Window;
	}

	@Override
	public byte[] getContent() {
		mC3Header(0x24, 0x0A);
		writeC(_wid);
		writeC(_pos);
		writeB(_item);
		return getBytes();
	}

	@Override
	public String getType() {
		return "Move item from one inventory to another.";
	}

	@Override
	public boolean testMe() {
		return true;
	}

}
