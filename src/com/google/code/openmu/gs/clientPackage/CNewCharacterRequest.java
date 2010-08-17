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

import java.io.IOException;

import com.google.code.openmu.gs.ClientThread;
import com.google.code.openmu.gs.muObjects.MuCharacterBase;
import com.google.code.openmu.gs.muObjects.MuCharacterWear;
import com.google.code.openmu.gs.serverPackets.SNewCharacterAnsfer;


public class CNewCharacterRequest extends ClientBasePacket {

	private String _name;
	private int _class;

	public CNewCharacterRequest(byte[] decrypt, ClientThread _client)
			throws IOException, Throwable {
		super(decrypt);
		_name = readS(2, 10);
		_name = _name.trim();
		_class = decrypt[12];
		_class = _class * 2;
		System.out.println(decrypt.length);
		System.out.println("Create Character '" + _name
				+ "' Requested class = " + _class);

		final int position = _client.getChList().getFirstFreeSlot();

		final MuCharacterBase newCB = new MuCharacterBase(_name, 1, _class,
				position, new MuCharacterWear());

		final boolean success = _client.storeNewChar(_client.getUser().getId(),
				_name, _class);
		if (success) {
			_client.getChList().addNew(newCB);
		}
		_client.getConnection().sendPacket(
				new SNewCharacterAnsfer(newCB, success, position));
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

}
