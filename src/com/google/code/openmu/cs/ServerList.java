package com.google.code.openmu.cs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import com.google.code.openmu.cs.serverPackets.ServerEntry;
import com.google.code.openmu.gs.GameServerConfig;

public class ServerList {
	private static ServerList instance = null;
	ArrayList<ServerEntry> gsArray= new ArrayList<ServerEntry>();
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

	ServerEntry[][] servers = null;

	private ServerList() throws IOException {
		GameServerConfig.getInstance();
		// settings groups and list of server
		int groupnb;
		try {
			groupnb = Integer.parseInt(GameServerConfig.cs
					.getProperty("cs.Groups")) ;
		} catch (NumberFormatException e) {
			groupnb = 1;

		}
		servers = new ServerEntry[groupnb][5];
	}

	public ServerEntry get(int pos, int group) {
		try {
			System.out.println(group);
			System.out.println(pos);
		return servers[group][pos];
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
		
	}

	public void addServer(ServerEntry serv) {
		try {
			System.out.println("Add:"+serv);
			gsArray.add(serv);
			servers[serv.grup][serv.pos] = serv;
		} catch (IndexOutOfBoundsException e) {

		}
	}
	
	public void load()
	{
		Properties cs = GameServerConfig.cs;
		HashSet<String>  names= new HashSet<String>();
		for (Entry en: cs.entrySet())
		{
			//System.out.println(en);
		String key = (String)en.getKey();
		String val = (String)en.getValue();
		if(key.startsWith("gs.")){
			String name = key.substring(3, key.indexOf(".", 4));
			names.add(name);
		}
		}
		
		for (String s: names)
		{
			String propertyPref = "gs."+s;
			ServerEntry t = new ServerEntry(s,cs.getProperty(propertyPref+".Host"),
				Integer.parseInt(cs.getProperty(propertyPref+".Port")),
				(byte)(Integer.parseInt(cs.getProperty(propertyPref+".Nb"))),
				(byte)(Integer.parseInt(cs.getProperty(propertyPref+".Group"))-1),
				(byte)0);
			addServer(t);
		}
				
	}
	public ArrayList<ServerEntry> asArrayList()
	{
		return gsArray;
	}
}
