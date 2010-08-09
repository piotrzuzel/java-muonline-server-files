package net.sf.jmuserver.gs.clientPackage;

import net.sf.jmuserver.gs.MuClientSession;

public class CSelectCharacterOrExitRequest extends ClientBasePacket {
	public CSelectCharacterOrExitRequest(byte[] decrypt, MuClientSession _client) {
		super(decrypt);
		switch (decrypt[2]) {
		case 0x00:
			System.out.println("exit game request");
			break;
		case 0x01:
			System.out.println("Change server request");
			break;
		case 0x02:
			System.out.println("Change character request");
			break;
		}

	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

}
