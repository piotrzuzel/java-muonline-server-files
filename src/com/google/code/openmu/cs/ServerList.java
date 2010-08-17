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
package com.google.code.openmu.cs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Properties;

import com.google.code.openmu.cs.codec.data.ServerEntry;
import com.google.code.openmu.gs.GameServerConfig;

public class ServerList {
	private static ServerList instance = null;
	ArrayList<ServerEntry> gsArray = new ArrayList<ServerEntry>();

	public static ServerList getInstance() {
		if (instance == null)
			try {
				instance = new ServerList();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return instance;
	}

	private ServerList() throws IOException {
		GameServerConfig.getInstance();
	}

	/**
	 * 
	 * @param pos
	 * @param flag
	 * @return {@link ServerEntry} gs allocated with speciefed pos & flag
	 * @throws ServerEntryNotFound
	 *             if requared gs not registred
	 */
	public ServerEntry get(int pos, int flag) throws ServerEntryNotFound {

		for (ServerEntry e : gsArray) {
			if (e.pos == pos || e.flag == flag)
				return e;
		}

		throw new ServerEntryNotFound(pos, flag);

	}

	/**
	 * registre GS with CS
	 * @param serv
	 */
	public void addServer(ServerEntry serv) {
			System.out.println("Add:" + serv);
			gsArray.add(serv);
	}

	public void load() {
		Properties cs = GameServerConfig.cs;
		HashSet<String> names = new HashSet<String>();
		for (Entry en : cs.entrySet()) {
			// System.out.println(en);
			String key = (String) en.getKey();
			String val = (String) en.getValue();
			if (key.startsWith("gs.")) {
				String name = key.substring(3, key.indexOf(".", 4));
				names.add(name);
			}
		}

		for (String s : names) {
			String propertyPref = "gs." + s;
			ServerEntry t = new ServerEntry(s, cs.getProperty(propertyPref
					+ ".Host"), Integer.parseInt(cs.getProperty(propertyPref
					+ ".Port")), (byte) (Integer.parseInt(cs
					.getProperty(propertyPref + ".Nb"))),
					(byte) (Integer.parseInt(cs.getProperty(propertyPref
							+ ".Flag"))), (byte) 0);
			addServer(t);
		}

	}

	public ArrayList<ServerEntry> asArrayList() {
		return gsArray;
	}
}
