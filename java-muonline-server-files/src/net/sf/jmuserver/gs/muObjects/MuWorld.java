package net.sf.jmuserver.gs.muObjects;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import java.util.Vector;
import javolution.util.FastMap;
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
    private Map<Integer, MuMap> _worldRegions;
    private Map<Integer, MuGate> _gates;

    private MuWorld() {
        _allPlayers = new FastMap<Integer, MuObject>().setShared(true);
        _allObjects = new FastMap().setShared(true);
        _worldRegions = new FastMap<Integer, MuMap>().setShared(true);
        _gates = new FastMap<Integer, MuGate>().setShared(true);
    //initRegions();
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
        Object[] temp = object.oldgetKnownObjects().values().toArray();
        for (int i = 0; i <
                temp.length; i++) {
            MuObject temp1 = (MuObject) temp[i];
            temp1.removeKnownObject(object,object.RemKnow_ForgetID);
            object.removeKnownObject(temp1,object.RemKnow_ForgetID);
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
    public Vector<MuObject> getVisibleObjects(MuObject object) {
        MuMap _regions = object.getCurrentWorldRegion();
        Vector<MuObject> _objects =
                _regions.getVisibleObjects(object);
        // remove self
        _objects.remove(object);
        return _objects;
    }

    public MuMap getRegion(int x) {
        return _worldRegions.get(x);
    }

    public void initRegions() {

        _worldRegions.put(0, new MuMap(0, "Lorencia"));


        //this staff shold be moved to MuMonstersMng where aare spown and setup all monsters in wourld with spots
        //but after
        MuNpc blacksmith = new MuNpc();
        blacksmith.setName("Hanzo the Blacksmith");
        blacksmith.setNpcId(251);
        blacksmith.setMaxHp(999999);

        MuNpc paj = new MuNpc();
        paj.setName("Hound");
        paj.setNpcId(1);
        paj.setMaxHp(5000);
        //1		0	0	2	40	0	6	8	1	0	10	1	2	0	1	5	400	1800	10	2	120	10

        //items 
        MuItemOnGround i1 = new MuItemOnGround();
        i1.setObiectId((short) IdFactory.getInstance().newId());
        i1.SetPos(179, 125, 0);
        i1.setM((byte) 0);
        i1.setCurrentWorldRegion(_worldRegions.get(0));
        i1.ISpown(); // spown on map

        MuItemOnGround i2 = new MuItemOnGround();
        i2.setObiectId((short) IdFactory.getInstance().newId());
        i2.SetPos(179, 123, 0);
        i2.setM((byte) 0);
        i2.setCurrentWorldRegion(_worldRegions.get(0));
        i2.ISpown(); // spown on map

        //pc actor
        MuPcActorInstance actor = new MuPcActorInstance();
        actor.setName("ElfActor");
        actor.setClas(0x40); // elf
        actor.setDirection((byte) 0x02);
        actor.setMurderStatus((byte) 0x02);
        actor.SetPos(176, 125, 0);
        actor.setObiectId((short) IdFactory.getInstance().newId());
        actor.setM((byte) 0);
        actor.setCurrentWorldRegion(_worldRegions.get(0));
        ((MuCharacter) actor).ISpown();

        //monsters
        MuMapSpot spot1=new MuMapSpot("Hound", _worldRegions.get(0), 176, 128, 199, 160, paj, 240);
        spot1.InitSpot();
        MuMonsterInstance mo = new MuMonsterInstance(paj);
        mo.setObiectId((short) IdFactory.getInstance().newId());
        mo.SetPos(176, 126, 0);
        mo.setWalkArea(new MuMobWalkArea(166, 116, 186, 136, 3));
        mo.setM((byte) 0);
        mo.setCurrentWorldRegion(_worldRegions.get(0));
        mo.ISpown();
        MuMonsterInstance mo2 = new MuMonsterInstance(paj);
        mo2.setObiectId((short) IdFactory.getInstance().newId());
        mo2.SetPos(177, 126, 0);
        mo2.setWalkArea(new MuMobWalkArea(166, 116, 186, 136, 3));
        mo2.setM((byte) 0);
        mo2.setCurrentWorldRegion(_worldRegions.get(0));
        mo2.ISpown();

        //npc's
        MuMonsterInstance npc1 = new MuMonsterInstance(blacksmith);
        npc1.setObiectId((short) IdFactory.getInstance().newId());
        npc1.SetPos(164, 129, 0x03);
        npc1.setWalkArea(new MuMobWalkArea(166, 116, 186, 136, 3));
        npc1.setM((byte) 0);
        npc1.setCurrentWorldRegion(_worldRegions.get(0));
        npc1.ISpown();


        _worldRegions.put(1, new MuMap(1, "Dungeon"));
        _worldRegions.put(2, new MuMap(2, "Devias"));
        _worldRegions.put(3, new MuMap(3, "Noria"));
        _worldRegions.put(4, new MuMap(4, "Lost Tower"));
        _worldRegions.put(7, new MuMap(7, "Atlants"));
        _worldRegions.put(8, new MuMap(8, "Tarkan"));
        System.out.println("Uset Mem after setup maps total:" + Runtime.getRuntime().totalMemory() + "used " + Runtime.getRuntime().freeMemory());
    }

    public void InitGates() {
        _gates.put(1, new MuGate(1, 1, 0, 121, 232, 123, 233, 2, 0, 20));
        _gates.put(2, new MuGate(2, 2, 1, 107, 247, 110, 247, 0, 1, 20));

        _gates.put(3, new MuGate(3, 1, 1, 108, 248, 109, 248, 4, 0, 0));
        _gates.put(4, new MuGate(4, 2, 0, 121, 231, 123, 231, 0, 1, 0));

        _gates.put(5, new MuGate(5, 1, 1, 239, 149, 239, 150, 6, 0, 40));
        _gates.put(6, new MuGate(6, 2, 1, 231, 126, 234, 127, 0, 1, 40));

        _gates.put(7, new MuGate(7, 1, 1, 232, 127, 233, 128, 8, 0, 40));
        _gates.put(8, new MuGate(8, 2, 1, 240, 148, 241, 151, 0, 3, 40));

        _gates.put(9, new MuGate(9, 1, 1, 2, 17, 2, 18, 10, 0, 50));
        _gates.put(10, new MuGate(10, 2, 1, 3, 83, 4, 86, 0, 3, 50));

        _gates.put(11, new MuGate(11, 1, 1, 2, 84, 2, 85, 12, 0, 50));
        _gates.put(12, new MuGate(12, 2, 1, 3, 16, 6, 17, 0, 3, 50));

        _gates.put(23, new MuGate(23, 1, 0, 213, 246, 217, 247, 24, 0, 10));
        _gates.put(24, new MuGate(24, 2, 3, 148, 5, 155, 6, 0, 5, 10));

        _gates.put(25, new MuGate(25, 1, 3, 148, 3, 155, 4, 26, 0, 10));
        _gates.put(26, new MuGate(26, 2, 0, 213, 244, 217, 245, 0, 1, 10));
    }

    /**
     * get gate  number
     * @param gn number of gate
     * @return the gate object
     */
    public MuGate getGate(int gn) {
        return _gates.get(gn);
    }
    ;
}