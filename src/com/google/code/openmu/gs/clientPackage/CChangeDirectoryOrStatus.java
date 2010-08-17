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
import com.google.code.openmu.gs.muObjects.MuPcInstance;
import com.google.code.openmu.gs.serverPackets.SDirectionOrStatusChange;

public class CChangeDirectoryOrStatus extends ClientBasePacket {
	private final byte _direction;
	private final byte _status;

	public CChangeDirectoryOrStatus(byte[] data, ClientThread _client) {
		super(data);
		_direction = data[1];
		_status = data[2];

		final MuPcInstance pc = _client.getActiveChar();
		pc.setDirection(_direction);
		pc.setStatus(_status);
		if (pc.getCurrentWorldRegion() != null) {
			pc.getCurrentWorldRegion().broadcastPacketWideArea(pc,
					pc.getCurrentMuMapPointX(), pc.getCurrentMuMapPointY(),
					new SDirectionOrStatusChange(pc, _status));
		}

		System.out.println("Object new direction to: " + _direction
				+ " and status: " + _status);
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

}
