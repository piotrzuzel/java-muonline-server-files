package net.sf.jmuserver.gs.clientPackage;

import net.sf.jmuserver.gs.ClientThread;
import net.sf.jmuserver.gs.muObjects.MuMonsterInstance;
import net.sf.jmuserver.gs.muObjects.MuWorld;

public class CAttackOnId extends ClientBasePacket {
	private int _id;
	private short _r;
	public CAttackOnId(byte[] decrypt, ClientThread _client) {
		super(decrypt);
		
		_id=(decrypt[1]& 0xff) ;
                _id|= _decrypt[2] << 8 & 0xff00;
		_r|=(short) (decrypt[4]);//r
		
		System.out.println("Atack On target "+ _id+ "in r "+_r);
		MuMonsterInstance mon=(MuMonsterInstance) MuWorld.getInstance().findObject(_id);
		_client.getActiveChar().startAttack(mon);
		_client.getActiveChar().StartCombat();
                mon.setTarget(_client.getActiveChar());
                //mon.StartCombat();
//		mon.printMyStatus();
		//}
		
	}



	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

}
