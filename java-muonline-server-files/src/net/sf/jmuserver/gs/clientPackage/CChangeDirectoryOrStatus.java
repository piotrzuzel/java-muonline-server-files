package net.sf.jmuserver.gs.clientPackage;

import net.sf.jmuserver.gs.ClientThread;
import net.sf.jmuserver.gs.muObiects.MuObiect;
import net.sf.jmuserver.gs.muObiects.MuPcInstance;
import net.sf.jmuserver.gs.serverPackage.SDirectionOrStatusChange;


public class CChangeDirectoryOrStatus extends ClientBasePacket {
	private byte _direction;
	private byte _status;
	public CChangeDirectoryOrStatus(byte[] data, ClientThread _client) {
		super(data);
		_direction=data[1];
		_status=data[2];
		
                MuPcInstance pc = _client.getActiveChar();
                pc.setDirection(_direction);
                pc.setStatus(_status);
                if (pc.getCurrentWorldRegion() != null)
                    pc.getCurrentWorldRegion().broadcastPacketWideArea(
                    (MuObiect)pc, pc.getCurrentMuMapPointX(), pc.getCurrentMuMapPointY(),
                    new SDirectionOrStatusChange(pc, (short)_status));
                
		System.out.println("Object new direction to: "+ _direction + " and status: "+_status);
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

}
