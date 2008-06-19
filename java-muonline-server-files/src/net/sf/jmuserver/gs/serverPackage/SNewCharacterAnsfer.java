package net.sf.jmuserver.gs.serverPackage;

import java.io.IOException;

import net.sf.jmuserver.gs.muObjects.MuCharacterBase;

public class SNewCharacterAnsfer extends ServerBasePacket{

	private MuCharacterBase _b;
	private boolean _success;
        private int _position;
	public SNewCharacterAnsfer(MuCharacterBase muCharacterBase,
                boolean success, int position) {
		_b = muCharacterBase;
		_success = success;
                _position = position;
	}

	public byte[] getContent() throws IOException {
		mC1Header(0xf3,0x01,0x2a); // C1 2A F3 01
                if (_success)              // 01 = succes, 00 = charname exists
                    writeC(0x01);
                else
                    writeC(0x00);
                writeNick(_b.getName());   // writes nick on 10 bytes
                writeC(_position);         // position in charlist 
                writeC(0x01);              // level 1 [2 bytes]
                writeC(0x00);              // ctlcode
                writeC(_b.getClas());      // class
                byte[] fill = {(byte)0x00,(byte)0xFF,(byte)0xFF,(byte)0xFF,
                    (byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,
                    (byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,
                    (byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,
                    (byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF}; 
                
                writeB(fill);
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
