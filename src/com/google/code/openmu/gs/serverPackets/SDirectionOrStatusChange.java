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

import com.google.code.openmu.gs.muObjects.MuPcInstance;

public class SDirectionOrStatusChange extends ServerBasePacket {
	private final MuPcInstance player;
	private final short status;

	public SDirectionOrStatusChange(MuPcInstance player, short status) {
		super();
		this.player = player;
		this.status = status;
	}

	@Override
	public byte[] getContent() throws IOException {
		mC1Header(0x18, 0x07);
		writeC(player.getObjectId() >> 8);
		writeC(player.getObjectId() & 0x00FF);
		writeC(player.getDirection());
		writeC(status);
		_bao.write(0x00);
		return _bao.toByteArray();
	}

	@Override
	public String getType() {

		return "Action result";
	}

	@Override
	public boolean testMe() {
		// TODO Auto-generated method stub
		return false;
	}
}