package net.sf.jmuserver.gs.clientPackage;

import java.util.ArrayList;

import java.util.Vector;
import net.sf.jmuserver.gs.ClientThread;
import net.sf.jmuserver.gs.muObjects.MuMonsterInstance;
import net.sf.jmuserver.gs.muObjects.MuObject;
import net.sf.jmuserver.gs.muObjects.MuPcInstance;
import net.sf.jmuserver.gs.muObjects.MuWorld;
import net.sf.jmuserver.gs.serverPackage.SForgetId;
import net.sf.jmuserver.gs.serverPackage.SNpcMiting;

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
        if (pc.getKnownObjects() != null) {
            System.out.println("Znanych obj:" + pc.getKnownObjects().size());
            ArrayList<MuObject> knownObj = new ArrayList<MuObject>();
            knownObj.addAll(pc.getKnownObjects());
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
                MuWorld.getInstance().getVisibleObjects(pc, 10);
        System.out.println("nowych obj:" + visitable.size());
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
                        System.out.println("nowy pc mtting");
                        //dodajemy go do temp listy
                        newPc.add((MuObject)visitable.elementAt(i));
                    //jesli jest to potwor
                    } else if (visitable.elementAt(i) instanceof MuMonsterInstance) {
                        System.out.println("nowy npc mitting");
                        //dodajemy go do temp listy
                        newNpc.add((MuObject)visitable.elementAt(i));
                    } //} else if (visitable[i] instanceof MuItemStore) {
                    //	System.out.println("nowy item mitting");
                    //	newItem.add(visitable[i]);
                    //}
                    else {
                        System.out.println("Nieznany mitting!!!");
                    }
                    // w gazdym razie dodajemy go do znanych obiektow
                    pc.addKnownObject((MuObject)visitable.elementAt(i));
                    // i jemu dajemy ze mnie widi = zna
                    ((MuObject)visitable.elementAt(i)).addKnownObject(pc);
                }

            }
            if (!newNpc.isEmpty()) {
                SNpcMiting npc = new SNpcMiting(newNpc); // twoze paczke z nowymi Npc
                pc.sendPacket(npc);

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
