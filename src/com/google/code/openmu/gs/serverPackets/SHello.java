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

/**
 * @author Miki
 * 
 */
public class SHello extends ServerBasePacket {
	private final int _id;
	private final String _version;

	public SHello(int id, String version) {
		_id = id;
		_version = version;
	}

	@Override
	public byte[] getContent() throws IOException {

		mC1Header(0xf1, 0x00, 0x0c);
		writeC(0x01);
		writeI(_id);
		writeS(_version);
		_bao.write(0x00);
		return _bao.toByteArray();

	}

	@Override
	public String getType() {

		return "SF101 witaj klijencie";
	}

	@Override
	public boolean testMe() {
		// TODO Auto-generated method stub
		return false;
	}

}
