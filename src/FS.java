import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

import net.sf.jmuserver.fs.FriendTheard;

public class FS extends Thread {

	private ServerSocket _serverSocket;
	public static Logger FSLogger = Logger.getLogger("FriendServer");
	private String _ip = "0";
	public static int _port = 55980;

	public static void main(String[] args) throws Exception {
		final FS server = new FS();
		FSLogger.info("Friend Server listen on port " + _port);
		server.start();
	}

	/**
     *
     */
	@Override
	public void run() {

		FSLogger.info("Friend Server listen on port " + _port);
		while (true) {
			try {

				FSLogger.info("used mem:" + getUsedMemoryMB() + "MB");
				final Socket connection = _serverSocket.accept();
				new FriendTheard(connection);

			} catch (final IOException e) {
				// not a real problem
			}
		}
	}

	public long getUsedMemoryMB() {
		return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime()
				.freeMemory()) / 1024 / 1024;
	}

	public FS() throws Exception {
		super("FS");

		FSLogger.info("used mem:" + getUsedMemoryMB() + "MB");

		final String hostname = "*";

		if (!"*".equals(hostname)) {
			final InetAddress adr = InetAddress.getByName(hostname);
			_ip = adr.getHostAddress();
			_serverSocket = new ServerSocket(_port, 50, adr);

		} else {
			_serverSocket = new ServerSocket(_port);
		}

	}
}
