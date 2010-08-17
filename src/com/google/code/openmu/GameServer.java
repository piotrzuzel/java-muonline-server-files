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

import com.google.code.openmu.gs.ClientThread;
import com.google.code.openmu.gs.CommandHandler;
import com.google.code.openmu.gs.GameServerConfig;
import com.google.code.openmu.gs.muObjects.MuWorld;
import com.google.code.openmu.logger.MuLoggerMennager;


public class GameServer extends Thread {
	// Socket listener

	private ServerSocket _serverSocket;
	// Ip adresses
	private String _ip;
	// Port listener
	public static int _port;
	// GS Global Logger
	static Logger GSLogger = Logger.getLogger("GameServer");

	public static void main(String[] args) throws Exception {
		MuLoggerMennager.getInstance();
		GSLogger.info("WorkingDir: " + System.getProperty("user.dir"));
		final GameServer server = new GameServer();

		GSLogger.info("GameServer Listening on port " + _port);
		server.start();// runing GS
	}

	@Override
	public void run() {
		GSLogger.info("Init Regions:...");
		MuWorld.getInstance().initWorld();
		GSLogger.info("Init Gates.....");
		MuWorld.getInstance().InitGates();
		CommandHandler.getInstancec();
		while (true) {
			try {
				final Socket connection = _serverSocket.accept();
				new ClientThread(connection);
			} catch (final IOException e) {
				// not a real problem
			}
		}
	}

	public long getUsedMemoryMB() {
		return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime()
				.freeMemory()) / 1024 / 1024;
	}

	public GameServer() throws Exception {
		super("AppMain");
		GameServerConfig.getInstance();

		_ip = GameServerConfig.gs.getProperty("gs.ip");
		_port = Integer.parseInt((GameServerConfig.gs.getProperty("gs.port")));

		System.out.println("used mem:" + getUsedMemoryMB() + "MB");

		final String hostname = "*";

		// String port = "port";
		// _port = 55901;

		if (!"*".equals(hostname)) {
			final InetAddress adr = InetAddress.getByName(hostname);
			_ip = adr.getHostAddress();
			_serverSocket = new ServerSocket(_port, 50, adr);
			// application.AddLog("GameServer listening on IP:" + _ip + " Port "
			// + _port);
		} else {
			_serverSocket = new ServerSocket(_port);
			// application.AddLog("GameServer listening on all available IPs on Port "
			// + _port);
		}

		// new File("data/clans").mkdirs();
		// new File("data/crests").mkdirs();

		// keep the references of Singletons to prevent garbage collection
		// Runtime.getRuntime().addShutdownHook(Shutdown.getInstance());
	}
}
