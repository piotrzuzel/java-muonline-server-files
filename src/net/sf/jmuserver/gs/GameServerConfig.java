package net.sf.jmuserver.gs;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * 
 * @author Marcel
 */
public class GameServerConfig {
	static public String Data_Dir;
	static private GameServerConfig _instance;

	static public String DB_USER;
	static public String DB_PASS;
	static public String GS_IP;
	static public int GS_PORT;
	static public int PLAYER_VISIBILITY;
	static public String ITEM_FILE;

	public static GameServerConfig getInstance() throws IOException {
		if (_instance == null) {
			_instance = new GameServerConfig();
		}
		return _instance;
	}

	private GameServerConfig() throws IOException {
		loadConfig();
	}

	private void loadConfig() throws IOException {
		final Properties conf = new Properties();
		final FileInputStream fis = new FileInputStream("GameServer.conf");
		conf.load(fis);
		fis.close();
		DB_USER = conf.getProperty("DB_USER", "postgres");
		DB_PASS = conf.getProperty("DB_PASS", "");
		GS_IP = conf.getProperty("GS_IP", "0");
		GS_PORT = Integer.parseInt(conf.getProperty("GS_PORT", "55901"));
		PLAYER_VISIBILITY = Integer.parseInt(conf.getProperty(
				"PLAYER_VISIBILITY", "1"));
		ITEM_FILE = conf.getProperty("ITEM_FILE", "item.txt");
	}

	public void findDataDirectory() {
		final String home = System.getProperty("user.home");
		System.out.println(home);
	}

}
