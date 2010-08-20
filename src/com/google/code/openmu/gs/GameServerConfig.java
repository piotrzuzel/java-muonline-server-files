package com.google.code.openmu.gs;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.mchange.v2.c3p0.impl.NewPooledConnection;

/**
 * 
 * @author Marcel
 */
public class GameServerConfig {
	static private GameServerConfig _instance;
	static public Properties global = new Properties();
	static public Properties databse = new Properties();
	static public Properties gs = new Properties();
	static public Properties logs = new Properties();
	static public Properties cs = new Properties();
	

	public static GameServerConfig getInstance() throws IOException {
		if (_instance == null) {
			_instance = new GameServerConfig();
		}
		return _instance;
	}

	private GameServerConfig() throws IOException {
		//global.put("global.home", System.getProperty("user.home")+"/.jmuserv/");
		global.put("global.home", System.getProperty("user.dir"));
		global.put("global.itemFile", global.getProperty("global.home")+"/data/item.txt");
		global.put("global.mapsDir", global.getProperty("global.home")+"/data/maps/");				
		loadConfig();
	}

	private void loadConfig() throws IOException {
		databse.load(new FileInputStream(global.getProperty("global.home")+"/conf/database.ini"));
		gs.load(new FileInputStream(global.getProperty("global.home")+"/conf/gameserver.ini"));
		logs.load(new FileInputStream(global.getProperty("global.home")+"/conf/mulog.ini"));
		cs.load(new FileInputStream(global.getProperty("global.home")+"/conf/connectserver.ini"));
	}

	public void findDataDirectory() {
		final String home = System.getProperty("user.dir");
		System.out.println(home);
	}

}
