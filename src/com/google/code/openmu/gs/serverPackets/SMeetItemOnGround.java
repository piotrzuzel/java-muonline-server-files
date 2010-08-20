/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.openmu.gs.serverPackets;

import java.io.IOException;
import java.util.ArrayList;

import com.google.code.openmu.gs.muObjects.MuItemOnGround;
import com.google.code.openmu.gs.muObjects.MuObject;

/**
 * 
 * @author Miki i Linka
 */
public class SMeetItemOnGround extends ServerBasePacket {

	ArrayList<MuObject> _item;

	public SMeetItemOnGround(ArrayList<MuObject> _items) {
		_item = _items;
	}

	@Override
	public byte[] getContent() throws IOException, Throwable {
		final int size = (9 * _item.size()) + 5; // (size of block items+
													// id+wsp)* itemscout +
													// itemcontbit + head
		mC2Header(0x20, size);
		writeC(_item.size()); // count of items
		for (int i = 0; i < _item.size(); i++) {
			System.out.println("buduje sub item");
			if (_item.get(i) instanceof MuItemOnGround) {
				makeSub((MuItemOnGround) _item.get(i));
			}
		}
		return getBytes();
	}

	private void makeSub(MuItemOnGround i) {
		writeC(0x00);
		writeC(i.getObjectId());
		writeC(i.getX()); // write x pos 1
		writeC(i.getY()); // write y pos 1
		writeB(i.getItemHex().toByteArray());// 5 bytes of item

	}

	@Override
	public String getType() {
		return "meet itemson ground";
	}

	@Override
	public boolean testMe() {
		return true;
	}
}
