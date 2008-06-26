package net.sf.jmuserver.gs.muObjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import java.util.Vector;
import net.sf.jmuserver.gs.IdFactory;
import net.sf.jmuserver.gs.templates.MuNpc;

/**
 * This class ...
 * 
 * @version $Revision: 1.21 $ $Date: 2004/11/10 11:00:46 $
 */
public class MuWorld {

    private Map _allPlayers;
    private Map _allObjects;
    private static MuWorld _instance;
    private MuMap[] _worldRegions = {null, null, null, null};

    private MuWorld() {
        _allPlayers = new HashMap<Integer, MuObject>();
        _allObjects = new HashMap();

        initRegions();
    }

    public static MuWorld getInstance() {
        if (_instance == null) {
            _instance = new MuWorld();
        }
        return _instance;
    }

    /*
     * Old methods
     */
    public void storeObject(MuObject temp) {
        _allObjects.put(new Integer(temp.getObjectId()), temp);
        temp.getCurrentWorldRegion().addVisibleObject(temp);
    }

    public void removeObject(MuObject object) {
        _allObjects.remove(new Integer(object.getObjectId())); // suggestion by
    // whatev
    }

    /**
     * find the object that belongs to an ID
     * 
     * @param oID
     * @return null if no object was found.
     */
    public MuObject findObject(int oID) {
        return (MuObject) _allObjects.get(new Integer(oID));
    }

    /**
     * 
     * @return all playerson world
     */
    public MuPcInstance[] getAllPlayers() {
        return (MuPcInstance[]) _allPlayers.values().toArray(
                new MuPcInstance[_allPlayers.size()]);
    }

    /**
     *  return PlayersId for nick
     * @param name
     * @return
     */
    public MuPcInstance getPlayer(String name) {
        return (MuPcInstance) _allPlayers.get(name.toLowerCase());
    }

    /**
     * Added Obiect to world depends maps
     * @param object
     */
    public void addVisibleObject(MuObject object) {
        // update region info for object
        // object.updateCurrentWorldRegion();

        if (object instanceof MuPcInstance) {
            // old stuff to remember all players
            _allPlayers.put(((MuPcInstance) object).getName().toLowerCase(),
                    object);

            // get all visible objects around
            Vector<MuObject> visible = getVisibleObjects(object);

            // tell the player about the surroundings
            for (int i = 0; i < visible.size(); i++) {
                object.addKnownObject(visible.elementAt(i));
                ((MuObject) visible.elementAt(i)).addKnownObject(object);
            }
        } else if (!(object instanceof MuPetInstance)) {
            // seen by players
            // if they pop up in
            // world.

            MuMap _regions = object.getCurrentWorldRegion();

            // update info for each player in surrounding regions if needed
            Vector _players = _regions.getVisiblePlayers(object);
            for (Iterator it = _players.iterator(); it.hasNext();) {
                MuPcInstance Player = (MuPcInstance) it.next();
                Player.addKnownObject(object);
                object.addKnownObject(Player);

            }
        }
    }

    public void removeVisibleObject(MuObject object) {
        // update known objects
        Object[] temp = object.getKnownObjects().toArray();
        for (int i = 0; i <
                temp.length; i++) {
            MuObject temp1 = (MuObject) temp[i];
            temp1.removeKnownObject(object);
            object.removeKnownObject(temp1);
        }

// old stuff to rember all players
        if (object instanceof MuPcInstance) {
            _allPlayers.remove(((MuPcInstance) object).getName().toLowerCase());
        }

// remove visible object from it's region
        object.getCurrentWorldRegion().removeVisibleObject(object);
    }

    /**
     * return obiects near obiect
     * @param object
     * @return
     */
    public Vector getVisibleObjects(MuObject object) {
        MuMap _regions = object.getCurrentWorldRegion();
        Vector _objects =
                _regions.getVisibleObjects(object);
        return _objects;
    }

    public MuMap getRegion(int x) {
        return _worldRegions[x];
    }

    private void initRegions() {
        _worldRegions[0] = new MuMap(0);
        MuNpc paj = new MuNpc();
        paj.setName("Spider");
        paj.setNpcId(1);
        paj.setMaxHp(5000);
        //1		0	0	2	40	0	6	8	1	0	10	1	2	0	1	5	400	1800	10	2	120	10
        //set pos for actor
        MuPcActorInstance actor = new MuPcActorInstance();
        actor.setName("Actor1");
        actor.SetPos(176, 125, 0);
        actor.setObiectId((short) IdFactory.getInstance().newId());
        actor.setM((byte) 0);
        actor.setCurrentWorldRegion(_worldRegions[0]);

        MuMonsterInstance mo = new MuMonsterInstance(paj);
        mo.setObiectId((short) IdFactory.getInstance().newId());
        //mo.setNpcTemplate(paj);
        //mo.setX(176);
        //mo.setY(126);
        mo.SetPos(176, 126, 0);
        mo.setWalkArea(new MuMobWalkArea(166, 116, 186, 136, 3));
        mo.setM((byte) 0);
        mo.setCurrentWorldRegion(_worldRegions[0]);

        MuMonsterInstance m1 = new MuMonsterInstance(paj);
        m1.setObiectId((short) IdFactory.getInstance().newId());
        //m1.setNpcTemplate(paj);
        //m1.setX(177);
        //m1.setY(126);
        m1.SetPos(177, 126, 0);
        m1.setWalkArea(new MuMobWalkArea(166, 116, 186, 136, 3));
        m1.setM((byte) 0);
        m1.setCurrentWorldRegion(_worldRegions[0]);
        storeObject(actor);
        storeObject(mo);
        storeObject(m1);
        mo.startRandomWalking();
        m1.startRandomWalking();
        _worldRegions[1] = new MuMap(1);
        _worldRegions[2] = new MuMap(2);

    }
}