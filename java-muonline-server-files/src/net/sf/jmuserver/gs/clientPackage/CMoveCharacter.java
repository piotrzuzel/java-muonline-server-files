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
                    if (!checkInRage(pc, obj)) {
                        System.out.println("to delete"+obj.toString());
                        toDelete.add(obj);
                    }
                }

            }
            if (!toDelete.isEmpty()) {
                 pc.sendPacket(new SForgetId(toDelete));
                for (int i = 0; i < toDelete.size(); i++) {
                    pc.removeKnownObject(toDelete.get(i));
                    toDelete.get(i).removeKnownObject(pc);
                   
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
            //looking for all
            for (int i = 0; i < visitable.size(); i++) {
                //when this if isstill unknown for as
                if (!pc.searchID(((MuObject)visitable.elementAt(i)).getObjectId())) {
                    //If  thisis players
                    if (visitable.elementAt(i) instanceof MuPcInstance) {
                        System.out.println("New Player Meeting: "+((MuPcInstance)visitable.elementAt(i)).getName());
                        //Added fim to list
                        newPc.add((MuObject)visitable.elementAt(i));
                    //if  thisis monster 
                    } else if (visitable.elementAt(i) instanceof MuMonsterInstance) {
                        System.out.println("New NPC Meeting");
                        //added him tolist
                        newNpc.add((MuObject)visitable.elementAt(i));
                    //if item
                    } else if (visitable.elementAt(i) instanceof MuItemOnGround) {
                        System.out.println("New Item on Ground");
                        //added to list
                        newItem.add((MuObject)visitable.elementAt(i));
                    }
                    else {
                        System.out.println("New Unkown Object Meeting!!!");
                    }
                    // Added all to my known lust
                    pc.addKnownObject((MuObject)visitable.elementAt(i));
                    //and also uptade him to know me
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
    /**
     * 
     * @TODO We dont need  this method eny more
     * @param obj
     * @param pc
     * @return
     */
    private int distance(MuObject obj, MuObject pc) {
        int dX = obj.getX() - pc.getX();
        int dY = obj.getY() - pc.getY();
        return (dX * dX + dY * dY);
    }
    /**
     * chceck objeci is in range
     * @param pc to what
     * @param t objec
     * @return tru when in range
     */
    public boolean checkInRage(MuPcInstance pc,MuObject t) {
        int chx = t.getX() / 5;
        int chy = t.getY() / 5;
        int myx = pc.getX() / 5;
        int myy = pc.getY() / 5;
        int rangeX1 = myx - 3;
        int rangeX2 = myx + 3;
        int rangeY1 = myy - 3;
        int rangeY2 = myy + 3;
        
        if (( rangeX1<=chx) && (chx <= rangeX2) && (rangeY1 <= chy) && (chy <= rangeY2)) {
            return true;
        }
        return false;
               
        
    }
    
    @Override
    public String getType() {
        
        return "Charater MOve ";
    }
}
