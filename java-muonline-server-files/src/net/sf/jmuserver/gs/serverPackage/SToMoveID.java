package net.sf.jmuserver.gs.serverPackage;

import java.io.IOException;

public class SToMoveID extends ServerBasePacket {

	private short _id;
	private short _x;
	private short _y;
	private short _f;
	public SToMoveID(short id,short x,short y,short f) {
	_id=id;
	_x=x;
	_y=y;
	_f=f;
	}

	@Override
	public byte[] getContent() throws IOException {
		mC1Header(0xd7,0x08);
		writeI(_id);
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
