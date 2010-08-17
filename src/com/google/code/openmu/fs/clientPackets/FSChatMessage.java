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

package com.google.code.openmu.fs.clientPackets;

import com.google.code.openmu.gs.clientPackage.ClientBasePacket;
import com.google.code.openmu.fs.FriendTheard;


/**
 * 
 * @author mikiones
 */
public class FSChatMessage extends ClientBasePacket {

	byte size;
	String message;

	public FSChatMessage(byte[] decrypt, FriendTheard _fs) {
		super(decrypt);
		size = decrypt[02];
		System.out.println("FS:> Size of package:" + decrypt.length + "]");
		Dec3bit(03, size);
		message = readS(03, size);
		System.out.println("FS:> Message[" + message + "] Size : ["
				+ Integer.toHexString(size) + "]");
	}

	@Override
	public String getType() {
		return "Chat request 0x04 00";
	}

}
