package com.google.code.openmu.gs.clientPackage;

import com.google.code.openmu.gs.ClientThread;
import com.google.code.openmu.gs.muObjects.MuMonsterInstance;
import com.google.code.openmu.gs.muObjects.MuObject;
import com.google.code.openmu.gs.muObjects.MuPcInstance;
import com.google.code.openmu.gs.muObjects.MuWorld;

public class CAttackOnId extends ClientBasePacket {

	private int _id = 0;
	private short _r = 0;

	public CAttackOnId(byte[] decrypt, ClientThread _client) {
		super(decrypt);

		_id = (decrypt[2] & 0xff);
		// _id|= _decrypt[2] << 8 & 0xff00;
		_r |= (decrypt[4]);// r

		// System.out.println("Atack On target "+ _id+ "in r "+_r);
		final MuObject t = MuWorld.getInstance().getObject(_id);
		if (t instanceof MuMonsterInstance) {
			final MuMonsterInstance mon = (MuMonsterInstance) t;
			_client.getActiveChar().startAttack(mon);
			_client.getActiveChar().StartCombat();
			mon.setTarget(_client.getActiveChar());
		} else if (t instanceof MuPcInstance) {
			System.out.println("Atack on player not done yet11");
		} else {
			System.out.println("Hmm atack to id" + _id + " ??? " + t
					+ " its not caind of MuPcInstance");
			// mon.StartCombat();
			// mon.printMyStatus();
			// }
		}
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}
}
