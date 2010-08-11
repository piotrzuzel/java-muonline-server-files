package com.google.code.openmu.gs.serverPackets;

import java.io.IOException;

public class SLiveStats extends ServerBasePacket {

	private int _maxLive;
	private final byte _tryb;
	final static public byte _UPDATE_MAX = 0;
	final static public byte _UPDATE_CUR = 1;

	public SLiveStats(byte tryb) {

		_tryb = tryb;
	}

	@Override
	public byte[] getContent() throws IOException {
		// C1 07 26 FE 00 3F 00
		switch (_tryb) {
		case _UPDATE_CUR:
			mC1Header(0x26, 0xff, 07);
			break;
		case _UPDATE_MAX:
			mC1Header(0x26, 0xfe, 07);
			break;
		}
		writeC(0x00);
		writeI(_maxLive);
		return _bao.toByteArray();
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setLive(int maxLive) {
		_maxLive = maxLive;

	}

	@Override
	public boolean testMe() {

		return false;
	}

}
