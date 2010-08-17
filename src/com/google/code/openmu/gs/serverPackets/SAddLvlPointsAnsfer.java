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

public class SAddLvlPointsAnsfer extends ServerBasePacket {

	public SAddLvlPointsAnsfer(byte co, boolean czy, int newMana, int newStamina) {
		_co = co;
		_czy = czy;
		_newMana = newMana;
		_newStamina = newStamina;
	}

	private final byte _co;
	private final boolean _czy;
	private final int _newStamina;
	private final int _newMana;

	@Override
	public byte[] getContent() throws IOException {

		// unsigned char t[]={0xC1,0x0A,0xF3,0x06,
		// 0x13,0x00,0x44,0x00,0x18,0x00,0x00,0x00};
		// unsigned char b=0x00;
		// b=_st;
		// unsigned char f;
		// if (_f)f=0x10;
		// b=b|f;
		// (*h)[0].writeAC(t,0x0a+1);
		// (*h)[4].writeC(b); //co i czy sied udalo
		// (*h)[6].writeI(_manaU,false);
		// (*h)[8].writeI(_stamU,false);
		// return h;

		mC1Header(0xf3, 0x06, 0x0a);// c1 0a f3 06
		byte c = 0;
		if (_czy) {
			c = 0x10;
		}
		final byte b = (byte) (_co | c);
		writeC(b); // 00
		writeC(0); // 00
		writeI(_newMana); // ff ff
		writeI(_newStamina); // ff ff
		writeI(0x00); // 00 00

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
