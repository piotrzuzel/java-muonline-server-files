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
import com.google.code.openmu.gs.muObjects.MuWorld;

/**
 * 
 * @author Miki i Linka
 */
public class CItemPickUpRequest extends ClientBasePacket {
	int id; // id wich item to get

	public CItemPickUpRequest(byte[] decrypt, ClientThread _client) {
		super(decrypt);
		// decrypt[1]=0x00 :// to fix with |0x80
		id = decrypt[2];
		// MuObject[] obj =
		// _client.getActiveChar().getCurrentWorldRegion().getVisibleObjects();
		// for (int i=0; i<obj.length; i++)
		// if (((MuObject)obj[i]).getObjectId() == id) {
		// ((MuObject)obj[i]).getCurrentWorldRegion();
		// MuWorld.getInstance().removeObject(obj[i]);
		// break;
		// }
		MuWorld.getInstance().removeObject(id);
		System.out.println("Request to pickup item id:" + id);
	}

	@Override
	public String getType() {
		return "pck up item";
	}
	// 0x22 opcode

}
