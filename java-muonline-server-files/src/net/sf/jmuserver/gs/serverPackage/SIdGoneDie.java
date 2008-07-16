package net.sf.jmuserver.gs.serverPackage;

import java.io.IOException;

public class SIdGoneDie extends ServerBasePacket {

	private int _id;

	public SIdGoneDie(int i) {
		_id=i;
	}

	@Override
	public byte[] getContent() throws IOException {
		mC1Header(0x17, 0x05);	//c1 05 17
                writeC(0x00);
		writeC(_id);
		return _bao.toByteArray();
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "IdGomeDie";
	}

	@Override
	public boolean testMe() {
		// TODO Auto-generated method stub
		return false;
	}

}
