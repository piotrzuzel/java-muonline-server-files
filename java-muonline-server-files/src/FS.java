
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.logging.*;
import net.sf.jmuserver.fs.FriendTheard;

public class FS extends Thread {

    private ServerSocket _serverSocket;
    public static Logger FSLogger = Logger.getLogger("FriendServer");
    private String _ip = "0";
    public static int _port = 55980;

    public static void main(String[] args) throws Exception {
        FS server = new FS();
        FSLogger.info("Friend Server listen on port " + _port);
        server.start();
    }

    /**
     *
     */
    public void run() {

        FSLogger.info("Friend Server listen on port " + _port);
        while (true) {
            try {

                FSLogger.info("used mem:" + getUsedMemoryMB() + "MB");
                Socket connection = _serverSocket.accept();
                FriendTheard c = new FriendTheard(connection);


            } catch (IOException e) {
                // not a real problem
            }
        }
    }

    public long getUsedMemoryMB() {
        return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 / 1024;
    }

    public FS() throws Exception {
        super("FS");

        FSLogger.info("used mem:" + getUsedMemoryMB() + "MB");

        String hostname = "*";

        if (!"*".equals(hostname)) {
            InetAddress adr = InetAddress.getByName(hostname);
            _ip = adr.getHostAddress();
            _serverSocket = new ServerSocket(_port, 50, adr);

        } else {
            _serverSocket = new ServerSocket(_port);
        }

        int maxPlayers = 10;

    }
}
