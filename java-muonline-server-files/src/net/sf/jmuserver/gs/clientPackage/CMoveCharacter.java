package net.sf.jmuserver.gs.clientPackage;

import java.util.ArrayList;

import java.util.Vector;
import net.sf.jmuserver.gs.ClientThread;
import net.sf.jmuserver.gs.muObjects.MuItemOnGround;
import net.sf.jmuserver.gs.muObjects.MuMonsterInstance;
import net.sf.jmuserver.gs.muObjects.MuObject;
import net.sf.jmuserver.gs.muObjects.MuPcActorInstance;
import net.sf.jmuserver.gs.muObjects.MuPcInstance;
import net.sf.jmuserver.gs.muObjects.MuWorld;
import net.sf.jmuserver.gs.serverPackage.SForgetId;
import net.sf.jmuserver.gs.serverPackage.SMeetItemOnGround;
import net.sf.jmuserver.gs.serverPackage.SNpcMiting;
import net.sf.jmuserver.gs.serverPackage.SPlayersMeeting;

public class CMoveCharacter extends ClientBasePacket {

    private short _nX;
    private short _nY;
    public CMoveCharacter(byte[] data, ClientThread _client) {
        super(data);
        _nX = (short) (data[1] & 0xff);
        _nY = (short) (data[2] & 0xff);
        // System.out.println("Postac sie porusza na wsp :["+_nX+","+_nY+"].");
        MuPcInstance pc = _client.getActiveChar();
        pc.moveTo(_nX, _nY);
        if (pc.oldgetKnownObjects() != null) {
            System.out.println("Known Objects count: " + pc.oldgetKnownObjects().size());
            ArrayList<MuObject> knownObj = new ArrayList<MuObject>();
            knownObj.addAll(pc.oldgetKnownObjects());
            ArrayList<MuObject> toDelete = new ArrayList<MuObject>();

            if (!knownObj.isEmpty()) {
                for (int i = 0; i < knownObj.size(); i++) {
                    MuObject obj = knownObj.get(i);
                    // std::cout << "O:" << obj->getOId() << "-D>
                    // "<<distance(obj,activeChar)<<"\n";
                    if (distance(obj, pc) > 100) {

                        toDelete.add(obj);
                    }
                }

            }
            if (!toDelete.isEmpty()) {
                for (int i = 0; i < toDelete.size(); i++) {
                    pc.removeKnownObject(toDelete.get(i));
                    toDelete.get(i).removeKnownObject(pc);
                    pc.sendPacket(new SForgetId(toDelete.get(i).getObjectId()));
                }
            }
        }
        Vector visitable =
                MuWorld.getInstance().getVisibleObjects(pc);
        System.out.println("Visible Objects count: " + visitable.size());
        if (visitable.size() != 0) {
            ArrayList<MuObject> newPc = new ArrayList<MuObject>();
            ArrayList<MuObject> newNpc = new ArrayList<MuObject>();
            ArrayList<MuObject> newItem = new ArrayList<MuObject>();
            //jdziemy po wzystkich
            for (int i = 0; i < visitable.size(); i++) {
                //jesli dany id  nie znajduje sie na w moich znanych...	
                if (!pc.searchID(((MuObject)visitable.elementAt(i)).getObjectId())) {
                    //jsli jest to gracz
                    if (visitable.elementAt(i) instanceof MuPcInstance) {
                        System.out.println("New Player Meeting: "+((MuPcInstance)visitable.elementAt(i)).getName());
                        //dodajemy go do temp listy
                        newPc.add((MuObject)visitable.elementAt(i));
                    //jesli jest to potwor
                    } else if (visitable.elementAt(i) instanceof MuMonsterInstance) {
                        System.out.println("New NPC Meeting");
                        //dodajemy go do temp listy
                        newNpc.add((MuObject)visitable.elementAt(i));
                    // } else if (visitable[i] instanceof MuItemStore) {
                    //	System.out.println("nowy item mitting");
                    //	newItem.add(visitable[i]);
                    } else if (visitable.elementAt(i) instanceof MuItemOnGround) {
                        System.out.println("New Item on Ground");
                        newItem.add((MuObject)visitable.elementAt(i));
                    }
                    else {
                        System.out.println("New Unkown Object Meeting!!!");
                    }
                    // w gazdym razie dodajemy go do znanych obiektow
                    pc.addKnownObject((MuObject)visitable.elementAt(i));
                    // i jemu dajemy ze mnie widi = zna
                    ((MuObject)visitable.elementAt(i)).addKnownObject(pc);
                }

            }
            if(!newPc.isEmpty()){
                SPlayersMeeting pcp=new SPlayersMeeting(newPc);
                pc.sendPacket(pcp);
                // Notify new visible players of current player
                ArrayList<MuObject> thisPlayer = new ArrayList<MuObject>();
                thisPlayer.add(pc);
                for (int i = 0; i < newPc.size(); i++) {
                    MuPcInstance newPlayer = (MuPcInstance) newPc.get(i);
                    if (!(newPlayer instanceof MuPcActorInstance))
                            newPlayer.sendPacket(new SPlayersMeeting(thisPlayer));
                }
            }
            if (!newNpc.isEmpty()) {
                SNpcMiting npc = new SNpcMiting(newNpc); // twoze paczke z nowymi Npc
                pc.sendPacket(npc);

            }
            if (!newItem.isEmpty()) {
                SMeetItemOnGround items = new SMeetItemOnGround(newItem);
                pc.sendPacket(items);
            }
        }

    }
    private int distance(MuObject obj, MuObject pc) {
        int dX = obj.getX() - pc.getX();
        int dY = obj.getY() - pc.getY();
        return (dX * dX + dY * dY);
    }
    @Override
    public String getType() {
        // TODO Auto-generated method stub
        return null;
    }
}
