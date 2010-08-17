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
import java.util.ArrayList;

import com.google.code.openmu.gs.muObjects.MuObject;

/**
 * @author Miki
 * @opis paczka z lista id'ow do zapomnienia
 * @example c1 16 14 01 00 01 00 <br>
 *          xx xx xx x1 x2 x2 x3 <br>
 *          x1 - ilosc idow do zaponienia <br>
 *          x2-id do zapomnienia <br>
 *          x3 --bit 00 stopu
 */
public class SForgetId extends ServerBasePacket {

	private final ArrayList<MuObject> _ids = new ArrayList<MuObject>();

	/**
	 * single id constructor
	 * 
	 * @param i
	 */
	public SForgetId(MuObject i) {
		_ids.add(i);
	}

	/**
	 * constructor with pos ids to forget
	 * 
	 * @param ids
	 */
	public SForgetId(ArrayList<MuObject> ids) {
		_ids.addAll(ids);
	}

	@Override
	public byte[] getContent() throws IOException {
		System.out.println("ToForgetId Run");

		mC1Header(0x14, 0x04 + (_ids.size() * 2));
		writeC(_ids.size()); // Count of ids to forget
		for (final MuObject integer : _ids) {
			writeC(integer.getObjectId() >> 8);
			writeC(integer.getObjectId() & 0x00FF);
		}
		return _bao.toByteArray();
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean testMe() {
		// TODO Auto-generated method stub
		return false;
	}
}
