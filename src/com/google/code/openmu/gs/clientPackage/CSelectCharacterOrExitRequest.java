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

public class CSelectCharacterOrExitRequest extends ClientBasePacket {
	public CSelectCharacterOrExitRequest(byte[] decrypt, ClientThread _client) {
		super(decrypt);
		switch (decrypt[2]) {
		case 0x00:
			System.out.println("exit game request");
			break;
		case 0x01:
			System.out.println("Change server request");
			break;
		case 0x02:
			System.out.println("Change character request");
			break;
		}

	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

}
