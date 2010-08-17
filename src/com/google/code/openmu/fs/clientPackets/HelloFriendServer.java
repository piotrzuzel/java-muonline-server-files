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

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.code.openmu.gs.clientPackage.ClientBasePacket;
import com.google.code.openmu.fs.FriendTheard;

import com.google.code.openmu.fs.serverPackets.FriendHelloAnsfer;

/**
 * 
 * @author Miki i Linka
 */
public class HelloFriendServer extends ClientBasePacket {

	public HelloFriendServer(byte[] decrypt, FriendTheard _fs) {
		super(decrypt);
		int _IdRoom = decrypt[2] & 0xff;
		_IdRoom |= _decrypt[3] << 8 & 0xff00;
		Dec3bit(4, 10);
		final String Nick = readS(4, 10);
		System.out.println("Request to connect to Chat Room Id:" + _IdRoom
				+ " From Nick" + Nick);
		try {

			// _fs.getConnection().sendPacket(new byte[]{(byte) 0xc2, (byte)
			// 0x00, (byte) 0x1e, (byte) 0x02, (byte) 0xcc, (byte) 0xcc, (byte)
			// 0x02, (byte) 0xcc, (byte) 0x00, (byte) 0x41, (byte) 0x6D, (byte)
			// 0x65, (byte) 0x6C, (byte) 0x69, (byte) 0x73, (byte) 0x73, (byte)
			// 0x61, (byte) 0x6E, (byte) 0x61, (byte) 0x01, (byte) 0x41, (byte)
			// 0x6D, (byte) 0x65, (byte) 0x6C, (byte) 0x65, (byte) 0x6B, (byte)
			// 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00});
			_fs.getConnection().sendPacket(new FriendHelloAnsfer("System"));
		} catch (final IOException ex) {
			Logger.getLogger(HelloFriendServer.class.getName()).log(
					Level.SEVERE, null, ex);
		} catch (final Throwable ex) {
			Logger.getLogger(HelloFriendServer.class.getName()).log(
					Level.SEVERE, null, ex);
		}

	}

	@Override
	public String getType() {
		return " Hello to FS";
	}

}
