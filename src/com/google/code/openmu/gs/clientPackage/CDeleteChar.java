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
import com.google.code.openmu.gs.database.MuCharacterListDB;
import com.google.code.openmu.gs.serverPackets.SDeleteChar;


/**
 * 
 * @author Marcel , Mikione
 * 
 */
public class CDeleteChar extends ClientBasePacket {
	private final String _personalcode;
	private final String _name;

	public CDeleteChar(byte[] decrypt, ClientThread _client)
			throws IOException, Throwable {
		super(decrypt);
		String p_code = _client.getUser().getChCode();
		// TODO sometimes if its nathing set i DB there is null so w relace it
		// as ""
		if (p_code == null) {
			p_code = "";
		}
		int result = 0x02;
		_name = readS(2, 10);
		_personalcode = readS(12, 7);
		if (_personalcode.compareTo(p_code) == 0) {
			result = 0x01;
		}
		// if (_personalcode.length() != 7)
		// result = 0x02;
		if (_client.getChList().getChar(_name).isInGuild()) {
			result = 0x00;
		}
		if (result == 0x01) {
			_client.getChList().removeChar(_name);
			final MuCharacterListDB cdb = new MuCharacterListDB(_client
					.getUser().getId());
			cdb.removeCharacterFromDB(_name);
		}
		_client.getConnection().sendPacket(new SDeleteChar(_name, result));
	}

	@Override
	public String getType() {
		return null;
	}
}
