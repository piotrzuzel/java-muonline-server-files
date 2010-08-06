/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.jmuserver.gs.serverPackage;


/**
 * 
 * @author Marcel
 */
public class SPutItemInInventory extends ServerBasePacket {

	private final byte[] _itemHex;
	private final int _pos;

	public SPutItemInInventory(byte[] Item, byte Position) {
		_itemHex = Item;
		_pos = Position;
	}

	@Override
	public byte[] getContent() {
		mC3Header(0x22, 0x09);
		writeC(_pos);
		writeB(_itemHex);
		return getBytes();
	}

	@Override
	public String getType() {
		return "Request to create item in character inventory.";
	}

	@Override
	public boolean testMe() {
		return true;
	}

}
