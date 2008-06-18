package net.sf.jmuserver.gs.serverPackage;

import java.io.IOException;

import net.sf.jmuserver.gs.muObjects.MuCharacterBase;

public class SNewCharacterAnsfer extends ServerBasePacket{

	private MuCharacterBase _b;
	private short _flag;
	public SNewCharacterAnsfer(MuCharacterBase muCharacterBase,short flag) {
		_b=muCharacterBase;
		_flag=flag;
	}

	public byte[] getContent() throws IOException {
		mC1Header(0xf3,0x01,0x2a); // C1 2A F3 01
		writeC(_flag);             // 01 = succes, 00 = charname exists
                writeNick(_b.getName());   // writes nick on 10 bytes
                writeC(0x00); writeC(0x01);// level 1 [2 bytes]
                writeC(0x00);              // ctlcode
                writeC(_b.getClas());      // class
                //byte[] fill = {(byte)0x00,(byte)0xFF,(byte)0xFF,(byte)0xFF,
                //    (byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,
                //    (byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,
                //    (byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,
                //    (byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF}; 
                //  Get new character "look" bytes
                writeB(_b.getWear().getBytes());
                //writeB(fill);
		return getBytes();
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
