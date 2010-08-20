package com.google.code.openmu.gs.clientPackage;

import com.google.code.openmu.gs.ClientThread;
import com.google.code.openmu.gs.muObjects.MuPcInstance;
import com.google.code.openmu.gs.serverPackets.SDirectionOrStatusChange;

public class CChangeDirectoryOrStatus extends ClientBasePacket {
	private final byte _direction;
	private final byte _status;

	public CChangeDirectoryOrStatus(byte[] data, ClientThread _client) {
		super(data);
		_direction = data[1];
		_status = data[2];

		final MuPcInstance pc = _client.getActiveChar();
		pc.setDirection(_direction);
		pc.setStatus(_status);
		if (pc.getCurrentWorldRegion() != null) {
			pc.getCurrentWorldRegion().broadcastPacketWideArea(pc,
					pc.getCurrentMuMapPointX(), pc.getCurrentMuMapPointY(),
					new SDirectionOrStatusChange(pc, _status));
		}

		System.out.println("Object new direction to: " + _direction
				+ " and status: " + _status);
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

}
