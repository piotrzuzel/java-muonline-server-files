

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.logging.*;
import net.sf.jmuserver.gs.ClientThread;
import net.sf.jmuserver.gs.CommandHandler;
import net.sf.jmuserver.gs.GameServerConfig;
import net.sf.jmuserver.gs.muObjects.MuWorld;



public class ApppMain extends Thread {
	private ServerSocket _serverSocket;
	//final static AppFrame application = new AppFrame();
	private String _ip;
     
	public static int _port;

  
        
	//static Logger _log = Logger.getLogger(AppMain.class.getName());

	// private static FileHandler fh = new FileHandler("mylog.txt");

	// private Shutdown _shutdownHandler;

	public static void main(String[] args) throws Exception {
		
	
	
		//_log.addHandler(new FileHandler("mylog.txt"));
		ApppMain server = new ApppMain();
                FS FServer=new FS();
                
		System.out.println("GameServer Listening on port "+_port);
		// server.testPlayback();
		server.start();// runing GS
                FServer.start();//runing FS
		// System.out.println(_log);
	}

	/**
	 * 
	 */
	public void run() {
      
        MuWorld.getInstance().initRegions();
        CommandHandler.getInstancec();
		while (true) {
			try {
                        
				//System.out.println("used mem:" + getUsedMemoryMB() + "MB");
				//System.out.println("waiting for client connection");
				Socket connection = _serverSocket.accept();
				ClientThread c= new ClientThread(connection);
                          
                                
			} catch (IOException e) {
				// not a real problem
			}
		}
	}

	public long getUsedMemoryMB() {
		return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime()
				.freeMemory()) / 1024 / 1024;
	}

	public ApppMain() throws Exception {
		super("AppMain");
		
		//SwingUtilities.invokeLater(new Runnable() {
		//	public void run() {
		//		
		//		application.setVisible(true);
		//	}
		//});
		
		// LogManager.getLogManager().addLogger(_log);
		// LogOutputStream sl=new OutputStream(new FileOutputStream("log.txt"));
                
                // load config (1 time ever)
                GameServerConfig.getInstance().loadConfig();
                
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
