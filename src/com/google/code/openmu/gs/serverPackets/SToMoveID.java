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

public class SToMoveID extends ServerBasePacket {

	private final int _id;
	private final int _x;
	private final int _y;
	private final byte _direction;

	public SToMoveID(int id, int x, int y, byte direction) {
		_id = id;
		_x = x;
		_y = y;
		_direction = direction;
	}

	@Override
	public byte[] getContent() throws IOException {
		mC1Header(0xd7, 0x08);
		writeC(_id >> 8);
		writeC(_id & 0x00FF);
		writeC(_x);
		writeC(_y);
		writeC(_direction << 4); // this is a flag, acount only for direction
									// atm
		writeC(0x00);

		return _bao.toByteArray();
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "cIDMOve";
	}

	@Override
	public boolean testMe() {
		// TODO Auto-generated method stub
		return false;
	}

}
