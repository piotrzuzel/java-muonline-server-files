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
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.code.openmu.gs.ClientThread;
import com.google.code.openmu.gs.muObjects.MuGate;
import com.google.code.openmu.gs.muObjects.MuWorld;
import com.google.code.openmu.gs.serverPackets.SGateEnterAnsfer;


/**
 * 
 * @author Miki i Linka, MarcelGh
 */
public class CEnterInGateRequest extends ClientBasePacket {

	private final int GateNb;// number of gate in gate.bmp

	public CEnterInGateRequest(byte[] decrypt, ClientThread _client) {
		super(decrypt);
		GateNb = decrypt[1] & 0xff;
		// System.out.println("Request to enter in gate id:" + GateNb);
		// fMap = decrypt[2] & 0xff;
		// x = decrypt[3] & 0xff;
		// y = decrypt[4] & 0xff;
		// direction = decrypt[5] & 0xff;
		// TODO: must test packet mapid and coordinates to match current chars
		// mapid and coords
		// and if those coords are in range defined by the gate and only then
		// perform teleport
		// WARNING: sometimes received coordinates are 0x00 and direction is not
		// valid
		final MuGate gate = MuWorld.getInstance().getGate(GateNb);
		final MuGate gateTo = MuWorld.getInstance().getGate(gate.getToGate());
		try {
			_client.getConnection().sendPacket(
					new SGateEnterAnsfer(gateTo, _client.getActiveChar()
							.getDirection()));
		} catch (final IOException ex) {
			Logger.getLogger(CEnterInGateRequest.class.getName()).log(
					Level.SEVERE, null, ex);
		} catch (final Throwable ex) {
			Logger.getLogger(CEnterInGateRequest.class.getName()).log(
					Level.SEVERE, null, ex);
		}

	}

	@Override
	public String getType() {
		return null;
	}
}
