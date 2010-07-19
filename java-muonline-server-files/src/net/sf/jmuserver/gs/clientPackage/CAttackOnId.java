package net.sf.jmuserver.gs.clientPackage;

import net.sf.jmuserver.gs.ClientThread;
import net.sf.jmuserver.gs.muObiects.MuMonsterInstance;
import net.sf.jmuserver.gs.muObiects.MuObiect;
import net.sf.jmuserver.gs.muObiects.MuPcActorInstance;
import net.sf.jmuserver.gs.muObiects.MuPcInstance;
import net.sf.jmuserver.gs.muObiects.MuWorld;

public class CAttackOnId extends ClientBasePacket {

    private int _id;
    private short _r;

    public CAttackOnId(byte[] decrypt, ClientThread _client) {
        super(decrypt);

        _id = (decrypt[2] & 0xff);
        //_id|= _decrypt[2] << 8 & 0xff00;
        _r |= (short) (decrypt[4]);//r

        //System.out.println("Atack On target "+ _id+ "in r "+_r);
        MuObiect t = MuWorld.getInstance().getObject(_id);
        if (t instanceof MuMonsterInstance) {
            MuMonsterInstance mon = (MuMonsterInstance) t;
            _client.getActiveChar().startAttack(mon);
            _client.getActiveChar().StartCombat();
            mon.setTarget(_client.getActiveChar());
        } else if (t instanceof MuPcInstance) {
            System.out.println("Atack on player not done yet11");
        } else {
            System.out.println("Hmm atack to id" + _id + " ??? " + t + " its not caind of MuPcInstance");
        //mon.StartCombat();
//		mon.printMyStatus();
        //}
        }
    }

    @Override
    public String getType() {
        // TODO Auto-generated method stub
        return null;
    }
}
