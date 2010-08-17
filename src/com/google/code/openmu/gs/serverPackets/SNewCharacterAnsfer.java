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

import com.google.code.openmu.gs.muObjects.MuCharacterBase;

public class SNewCharacterAnsfer extends ServerBasePacket {

	private final MuCharacterBase _b;
	private final boolean _success;
	private final int _position;

	public SNewCharacterAnsfer(MuCharacterBase muCharacterBase,
			boolean success, int position) {
		_b = muCharacterBase;
		_success = success;
		_position = position;
	}

	@Override
	public byte[] getContent() throws IOException {
		mC1Header(0xf3, 0x01, 0x2a); // C1 2A F3 01
		if (_success) {
			writeC(0x01);
		} else {
			writeC(0x00);
		}
		writeNick(_b.getName()); // writes nick on 10 bytes
		writeC(_position); // position in charlist
		writeC(0x01); // level 1 [2 bytes]
		writeC(0x00); // ctlcode
		writeC(_b.getClas()); // class
		final byte[] fill = { (byte) 0x00, (byte) 0xFF, (byte) 0xFF,
				(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
				(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
				(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
				(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
				(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF };

		writeB(fill);
		return getBytes();
	}

	@Override
	public String getType() {

		return null;
	}

	@Override
	public boolean testMe() {

		return false;
	}

}
