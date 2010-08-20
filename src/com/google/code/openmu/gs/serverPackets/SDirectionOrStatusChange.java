package com.google.code.openmu.gs.serverPackets;

import java.io.IOException;

import com.google.code.openmu.gs.muObjects.MuPcInstance;

public class SDirectionOrStatusChange extends ServerBasePacket {
	private final MuPcInstance player;
	private final short status;

	public SDirectionOrStatusChange(MuPcInstance player, short status) {
		super();
		this.player = player;
		this.status = status;
	}

	@Override
	public byte[] getContent() throws IOException {
		mC1Header(0x18, 0x07);
		writeC(player.getObjectId() >> 8);
		writeC(player.getObjectId() & 0x00FF);
		writeC(player.getDirection());
		writeC(status);
		_bao.write(0x00);
		return _bao.toByteArray();
	}

	@Override
	public String getType() {

		return "Action result";
	}

	@Override
	public boolean testMe() {
		// TODO Auto-generated method stub
		return false;
	}
}