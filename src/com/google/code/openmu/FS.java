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

package com.google.code.openmu;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

import com.google.code.openmu.fs.FriendTheard;


public class FS extends Thread {

	private ServerSocket _serverSocket;
	public static Logger FSLogger = Logger.getLogger("FriendServer");
	private String _ip = "0";
	public static int _port = 55980;

	public static void main(String[] args) throws Exception {
		final FS server = new FS();
		FSLogger.info("Friend Server listen on port " + _port);
		server.start();
	}

	/**
     *
     */
	@Override
	public void run() {

		FSLogger.info("Friend Server listen on port " + _port);
		while (true) {
			try {

				FSLogger.info("used mem:" + getUsedMemoryMB() + "MB");
				final Socket connection = _serverSocket.accept();
				new FriendTheard(connection);

			} catch (final IOException e) {
				// not a real problem
			}
		}
	}

	public long getUsedMemoryMB() {
		return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime()
				.freeMemory()) / 1024 / 1024;
	}

	public FS() throws Exception {
		super("FS");

		FSLogger.info("used mem:" + getUsedMemoryMB() + "MB");

		final String hostname = "*";

		if (!"*".equals(hostname)) {
			final InetAddress adr = InetAddress.getByName(hostname);
			_ip = adr.getHostAddress();
			_serverSocket = new ServerSocket(_port, 50, adr);

		} else {
			_serverSocket = new ServerSocket(_port);
		}

	}
}
