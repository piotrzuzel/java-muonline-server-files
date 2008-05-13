package net.sf.jmuserver.gs.serverPackage;

import java.io.IOException;

import net.sf.jmuserver.gs.muObjects.MuCharacterBase;

public class SNewCharacterAnsfer extends ServerBasePacket{

	private MuCharacterBase _b;
	private int _gdzie;
	private short _flag;
	public SNewCharacterAnsfer(MuCharacterBase muCharacterBase,int gdzie,short flag) {
		_b=muCharacterBase;
		_gdzie=gdzie;
		_flag=flag;
	}

	public byte[] getContent() throws IOException {
		mC1Header(0xf3,0x01 ,0xa2);
		writeC(_flag);
		writeS(_b.getName());
		return null;
	}

	@Override
	public String getType() {

		return null;
	}

	@Override
	public boolean testMe() {

		return false;
	}

}
