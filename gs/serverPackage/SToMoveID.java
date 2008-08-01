package net.sf.jmuserver.gs.serverPackage;

import java.io.IOException;

public class SToMoveID extends ServerBasePacket {

	private int _id;
	private int _x;
	private int _y;
	private byte _f;
	public SToMoveID(int id,int x,int y,byte f) {
	_id=id;
	_x=x;
	_y=y;
	_f=f;
	}

	@Override
	public byte[] getContent() throws IOException {
		mC1Header(0xd7,0x08);
                writeC(0x00);
		writeC(_id);
		writeC(_x);
		writeC(_y);
		writeC(_f*0x10);
		writeC(0);
		
		return _bao.toByteArray();
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "cIDMOve";
	}

	@Override
	public boolean testMe() {
		// TODO Auto-generated method stub
		return false;
	}

}
