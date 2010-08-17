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
