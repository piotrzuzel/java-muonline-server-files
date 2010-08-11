/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.google.code.openmu.gs.serverPackets;

import java.io.IOException;

/**
 * 
 * @author Miki i Linka
 */
public class SPublicMsg extends ServerBasePacket {
	private final String _who;
	private final String _what;

	public SPublicMsg(String _who, String _what) {
		this._who = _who;
		this._what = _what;
	}

	@Override
	public byte[] getContent() throws IOException, Throwable {
		mC1Header(0x00, _what.length() + 14);
		writeNick(_who);
		writeS(_what);
		writeC(0x00);
		return getBytes();
	}

	@Override
	public String getType() {
		return "00 public msg";
	}

	@Override
	public boolean testMe() {
		return true;
	}

}
