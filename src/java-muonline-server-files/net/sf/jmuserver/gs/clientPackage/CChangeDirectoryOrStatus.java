package net.sf.jmuserver.gs.clientPackage;

import net.sf.jmuserver.gs.ClientThread;


public class CChangeDirectoryOrStatus extends ClientBasePacket {
	private byte _directory;
	private byte _status;
	public CChangeDirectoryOrStatus(byte[] data, ClientThread _client) {
		super(data);
		_directory=data[1];
		_status=data[2];
		
		System.out.println("zmiana kierunku na : "+ _directory + " i statusu na "+_status);
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

}
