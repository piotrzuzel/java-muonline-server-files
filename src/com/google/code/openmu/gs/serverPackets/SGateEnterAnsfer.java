/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.google.code.openmu.gs.serverPackets;

import java.io.IOException;

import com.google.code.openmu.gs.muObjects.MuGate;

/**
 * 
 * @author Miki i Linka
 */
public class SGateEnterAnsfer extends ServerBasePacket {
	private final MuGate _to;
	private final int _direction;

	public SGateEnterAnsfer(MuGate to, int direction) {
		_to = to;
		_direction = direction;
	}

	@Override
	public byte[] getContent() throws IOException, Throwable {

		mC3Header(0x1C, 0x08);

		writeC(_to.getGateNb());
		writeC(_to.getMap());
		writeC(_to.getX1() + 1);
		writeC(_to.getY1() + 1);
		writeC(_direction);

		// byte [] t= {(byte)0xc3 ,(byte)0x08 ,(byte)0x1c ,(byte)0x17
		// ,(byte)0x03 ,(byte)0x96 ,(byte)0x05 ,(byte)0x05};
		return getBytes();
	}

	@Override
	public String getType() {
		return "enter ingate ansfer";
	}

	@Override
	public boolean testMe() {
		return true;
	}

}
