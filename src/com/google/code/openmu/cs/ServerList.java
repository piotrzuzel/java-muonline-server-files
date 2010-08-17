package com.google.code.openmu.cs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import com.google.code.openmu.gs.GameServerConfig;
import com.google.code.openmu.natty.CS.data.ServerEntry;

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
