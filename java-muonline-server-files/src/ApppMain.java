
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.logging.*;
import net.sf.jmuserver.gs.ClientThread;
import net.sf.jmuserver.gs.CommandHandler;
import net.sf.jmuserver.gs.GameServerConfig;
import net.sf.jmuserver.gs.muObjects.MuWorld;
import net.sf.jmuserver.logger.MuLoggerMennager;

public class ApppMain extends Thread {
    //Socket listener

    private ServerSocket _serverSocket;
    //Ip adresses
    private String _ip;
    //Port listener
    public static int _port;
    //GS Global Logger
    static Logger GSLogger = Logger.getLogger("GameServer");

    public static void main(String[] args) throws Exception {
        MuLoggerMennager.getInstance();
        GSLogger.info("WorkingDir: " + System.getProperty("user.dir"));
        ApppMain server = new ApppMain();
        FS FServer = new FS();
        GSLogger.info("GameServer Listening on port " + _port);
        server.start();// runing GS
        FServer.start();//runing FS
    }

    public void run() {
        GSLogger.info("Init Regions:...");
        MuWorld.getInstance().initWorld();
        GSLogger.info("Init Gates.....");
        MuWorld.getInstance().InitGates();
        CommandHandler.getInstancec();
        while (true) {
            try {
                Socket connection = _serverSocket.accept();
                ClientThread c = new ClientThread(connection);
            } catch (IOException e) {
                // not a real problem
            }
        }
    }

    public long getUsedMemoryMB() {
        return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 / 1024;
    }

    public ApppMain() throws Exception {
        super("AppMain");
        GameServerConfig.getInstance();

        _ip = GameServerConfig.GS_IP;
        _port = GameServerConfig.GS_PORT;

        System.out.println("used mem:" + getUsedMemoryMB() + "MB");

        String hostname = "*";

        //String port = "port";
        //_port = 55901;

        if (!"*".equals(hostname)) {
            InetAddress adr = InetAddress.getByName(hostname);
            _ip = adr.getHostAddress();
            _serverSocket = new ServerSocket(_port, 50, adr);
            //		application.AddLog("GameServer listening on IP:" + _ip + " Port "
            //				+ _port);
        } else {
            _serverSocket = new ServerSocket(_port);
            //	application.AddLog("GameServer listening on all available IPs on Port "
            //					+ _port);
        }

        int maxPlayers = 10;
        //	application.AddLog("Maximum Numbers of Connected Players: "
        //		+ maxPlayers);

        //new File("data/clans").mkdirs();
        //new File("data/crests").mkdirs();

        // keep the references of Singletons to prevent garbage collection
        //Runtime.getRuntime().addShutdownHook(Shutdown.getInstance());
    }
}
