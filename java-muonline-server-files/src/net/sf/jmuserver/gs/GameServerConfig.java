package net.sf.jmuserver.gs;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Marcel
 */
public class GameServerConfig {
    static private GameServerConfig _instance;
    
    static public String DB_USER;
    static public String DB_PASS;
    static public String GS_IP;
    static public int GS_PORT;
    static public int PLAYER_VISIBILITY;
    
    public static GameServerConfig getInstance() throws IOException {
        if (_instance == null)
            _instance = new GameServerConfig();
        return _instance;                
    }
        
    public void loadConfig() throws IOException {
        Properties conf = new Properties();
        FileInputStream fis = new FileInputStream("GameServer.conf");
        conf.load(fis);
        fis.close();
        String aux = new String();        
        DB_USER = conf.getProperty("DB_USER", "postgres");
        DB_PASS = conf.getProperty("DB_PASS", "");
        GS_IP = conf.getProperty("GS_IP", "0");      
        GS_PORT = Integer.parseInt(conf.getProperty("GS_PORT", "55901"));
        PLAYER_VISIBILITY = Integer.parseInt(
                conf.getProperty("PLAYER_VISIBILITY", "1"));
    }

}
