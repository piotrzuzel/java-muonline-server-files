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
