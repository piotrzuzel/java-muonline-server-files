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

package com.google.code.openmu.fs;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.code.openmu.gs.MuConnection;


/**
 * 
 * @author Miki i Linka
 */
public class FriendTheard extends Thread {
	private final byte[] _cryptkey = { (byte) 0x94, (byte) 0x35, (byte) 0x00,
			(byte) 0x00, (byte) 0xa1, (byte) 0x6c, (byte) 0x54, (byte) 0x87 };
	private final MuConnection _connection;
	private final com.google.code.openmu.fs.PacketHandler _handler;

	public FriendTheard(Socket _con) throws IOException {
		_connection = new MuConnection(_con, _cryptkey);
		_handler = new com.google.code.openmu.fs.PacketHandler(this);
		start();
	}

	public MuConnection getConnection() {
		return _connection;

	}

	@Override
	public void run() {
		boolean _while = true;
		while (_while) {
			try {

				// System.out.println("czekam na odpowiedz");
				final byte[] decrypt = _connection.getPacket();
				if (decrypt != null) {
					_handler.handlePacket(decrypt);
				} else {
					_while = false;
				}
				// System.out.println("odebralem");
			} catch (final IOException ex) {
				Logger.getLogger(FriendTheard.class.getName()).log(
						Level.SEVERE, null, ex);
			} catch (final Throwable ex) {
				Logger.getLogger(FriendTheard.class.getName()).log(
						Level.SEVERE, null, ex);

			}
			// System.out.println("odebralem");
		}

	}
}
