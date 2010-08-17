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

public class SManaStaminaStats extends ServerBasePacket {

	private int _maxMana;
	private int _maxStamina;
	private final byte _typ;
	final public static byte _UPDATE_MAX = 0;
	final public static byte _UPDATE_CUR = 1;

	public SManaStaminaStats(byte typ) {
		_typ = typ;
	}

	@Override
	public byte[] getContent() throws IOException {
		// C1 08 27 FF 00 42 00 13
		switch (_typ) {
		case _UPDATE_MAX:
			mC1Header(0x27, 0xfe, 0x8);
			break;
		case _UPDATE_CUR:
			mC1Header(0x27, 0xff, 0x8);
			break;
		}

		writeI(_maxMana);
		writeI(_maxStamina);
		return _bao.toByteArray();
	}

	@Override
	public String getType() {

		return null;
	}

	public void setMana(int maxMana) {
		_maxMana = maxMana;

	}

	public void setStamina(int maxStamina) {
		_maxStamina = maxStamina;

	}

	@Override
	public boolean testMe() {
		// TODO Auto-generated method stub
		return false;
	}

}
