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
package com.google.code.openmu.gs.clientPackage;

import com.google.code.openmu.gs.ClientThread;

/**
 * 
 * @author Miki
 */
public class CItemDropFromInwentoryRequest extends ClientBasePacket {

	private int _xPos = 0;
	private int _yPos = 0;
	private int _slotFrom = 0;

	public CItemDropFromInwentoryRequest(byte[] decrypt, ClientThread _client) {
		super(decrypt);
		// readC();
		_xPos = decrypt[1] & 0xff;
		_yPos = decrypt[2] & 0xff;
		_slotFrom = decrypt[3] & 0xff;
		// 23 ad 7f 0c
		System.out.println("Drop Request from slot[" + _slotFrom + "] to wsp ["
				+ _xPos + "," + _yPos + "]");
	}// 26 0c 00

	@Override
	public String getType() {
		return "inwentory roop item request";
	}
	// 23 ad 7f 0c
}
