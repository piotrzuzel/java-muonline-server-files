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

public class SDMgOnScreen extends ServerBasePacket {
	private final int _dmg;
	private final int _id;
	private final byte _f;
	public static final byte _DMG_MISS = 0x00;
	public static final byte _DMG_NORM = 0x02;
	public static final byte _DMG_REFL = 0x00;
	public static final byte _DMG_EXC = 0x00;
	public static final byte _DMB_CRI = 0x01;

	public SDMgOnScreen(int id, int dmg, byte fdmg) {
		_dmg = dmg;
		_id = id;
		_f = fdmg;
	}

	@Override
	public byte[] getContent() throws IOException {
		// c1 08 d6 74 00 96 00 00
		// c1 08 d6 00 f0 00 96 00 00
		// c1 08 d6 00 a0 00 96 00
		// c1 08 d7 14 41 8c 56 70
		// c1 08 d9 03 94 00 00 00
		// 0xc1,0x08,0xd6,0xff,0xff,0xff,0xff,0xff
		// d7 poruszanie
		// d6 ?
		// d5 ?
		// d1 ?
		// d2 ?

		mC1Header(0xd9, 0x08);
		writeC(0x00);
		writeC(_id);
		writeIDiverted(_dmg); // diverted Int byte order
		writeC(_f);
		// writeC(0);
		return _bao.toByteArray();

	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean testMe() {

		return false;
	}

}
