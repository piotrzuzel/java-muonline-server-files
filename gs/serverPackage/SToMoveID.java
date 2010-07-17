package net.sf.jmuserver.gs.serverPackage;

import java.io.IOException;

public class SToMoveID extends ServerBasePacket {

	private int _id;
	private int _x;
	private int _y;
	private byte _direction;
        private byte _murderStatus;

	public SToMoveID(int id,int x,int y,byte direction) {
            _id=id;
            _x=x;
            _y=y;
            _direction=direction;
	}

	@Override
	public byte[] getContent() throws IOException {
		mC1Header(0xd7,0x08);
                writeC(_id >> 8);
		writeC(_id & 0x00FF);
		writeC(_x);
		writeC(_y);
		writeC(_direction << 4); //this is a flag, acount only for direction atm
		writeC(0x00);
		
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
