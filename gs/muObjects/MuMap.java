package net.sf.jmuserver.gs.muObjects;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import javolution.util.FastMap;
import net.sf.jmuserver.gs.ClientThread;
import net.sf.jmuserver.gs.GameServerConfig;
import net.sf.jmuserver.gs.muObjects.MuMap.MuMapPoint;
import net.sf.jmuserver.gs.muObjects.MuObject;
import net.sf.jmuserver.gs.serverPackage.ServerBasePacket;

/**
 * MuMap represents the game entity known as map.<br>
 * It provides means to encapsulate visibile objects and manipulate them
 * in terms of spawn and movement.
 * @see MuObject
 * @see MuWorld
 * @author Marcel, Miki
 */
public class MuMap {

    /**
     * MuMapPoint encapsulates all objects visible on a 3x3 square on map.<br>
     * Each map is partitioned in 86 MuMapPoints, and the player visibility
     * range is measured in number of MuMapPoints away from his own.
     * @author Marcel, Miki
     */
    class MuMapPoint {

        public FastMap<Integer,MuObject> _objects = new FastMap<Integer, MuObject>().setShared(true);
              
        public boolean isEmpty() {
            return _objects.isEmpty();
        }

        public void addObject(MuObject MuObj) {
            _objects.put(new Integer(MuObj.getObjectId()), MuObj);
        }

        public boolean containsObject(MuObject MuObj) {
            return _objects.containsValue(MuObj);
        }
        
        public boolean containsObject(int objectId) {
            return _objects.containsKey(new Integer(objectId));
        }

        public boolean removeObject(int objectId) {
            return (_objects.remove(new Integer(objectId)) != null);            
        }
        
        public void broadcastPacket(MuCharacter Client, ServerBasePacket Packet) {
            for (FastMap.Entry<Integer, MuObject> e = _objects.head(), end = _objects.tail();
                (e = e.getNext()) != end;) {
                MuObject obj = e.getValue();
                if ((Client != obj) && (obj instanceof MuPcInstance))
                    ((MuPcInstance)obj).sendPacket(Packet);
             }            
        }
        
        public void broadcastPacket(ServerBasePacket Packet) {
            broadcastPacket(null, Packet);
        }
    }    
    
    MuMapPoint[][] _regions = new MuMapPoint[86][86];    
    
    /**
     * Given the region X and Y coordinates (max. 85), the function broadcasts
     * a packet to all player instances, except the one represented by the
     * first paramater, that can be found in the visibile player range.
     * @param Client The exception player when broadcasting
     * @param RegionX The X coordinate of the region
     * @param RegionY The Y coordinate of the region
     * @param Packet The ServerBasePacket to be sent
     */
    public void broadcastPacketWideArea(MuCharacter Client, int RegionX, 
            int RegionY, ServerBasePacket Packet) {
        byte x1 = (byte) (RegionX - GameServerConfig.PLAYER_VISIBILITY);
        byte x2 = (byte) (RegionY + GameServerConfig.PLAYER_VISIBILITY);
        byte y1 = (byte) (RegionX - GameServerConfig.PLAYER_VISIBILITY);
        byte y2 = (byte) (RegionY + GameServerConfig.PLAYER_VISIBILITY);   
        if (x1<0)
            x1 = 0;
        if (y1<0)
            y1 = 0;
        if (x2>85)
            x2 = 85;
        if (y2>85)
            y2 = 85;
        for (;x1<=x2;x1++)
            for (;y1<=y2;y1++)
                _regions[x1][y1].broadcastPacket(Client, Packet);
    }
    
    public void broadcastPacketWideArea(int RegionX, int RegionY, 
            ServerBasePacket Packet) {
        
    }
    
    /**
     * Moves a MuCharacter to a new coordinate. The action considers the
     * player visibility range, and broadcasts movement and spawn packets
     * (meeting new objects) appropriately.
     * @param Who The character to be moved
     * @param x The new X coordinate
     * @param y The new Y coordinate
     * @return true when done
     */
    boolean moveTo(MuCharacter Who, int x, int y) {
        int curX = Who.getX();
        int curY = Who.getY();
        int x1 = curX / 8;
        int y1 = curY / 8;
        int x2 = x / 8;
        int y2 = y / 8;
        // If the character is in the same region, broadcast moving packet.
        if ((x1 == x2) && (y1 == y2)) {
            broadcastPacketWideArea(Who.g, curX, curY, Packet);
            return true;
        }
        _regions[x1][y1].punkt.remove(Who);
        _regions[x2][y2].punkt.add(Who);
        return true;
    }
    
    private byte _mapCode;
    private String _mapName;
    /**
     * all players visitable on map
     */
    private Map<String, MuObject> _allPlayers;
    /**
     * all Obiect visitable on map
     */
    private Map<Integer, MuObject> _visibleObjects;

    /**
     * init map
     * @param m map byte code
     * @param MapName name of map
     */
    public MuMap(int m, String MapName) {
        _mapCode = (byte) m;
        _mapName = MapName;
        _allPlayers = new HashMap<String, MuObject>();
        _visibleObjects = new HashMap<Integer, MuObject>();
        for (int i = 0; i <= 50; i++) {
            for (int j = 0; j <= 50; j++) {
                _regions[i][j] = new MuMapPoint();
            }
        }
    }

    /**
     * 
     * @return the map name
     */
    public String getMapName() {
        return _mapName;
    }

    /**
     * 
     * @return return code of map
     */
    public byte getByteCode() {
        return _mapCode;
    }

    /**
     * Added to map obiect
     * @param object
     */
    public void addVisibleObject(MuObject object) {
        //addObject to basic  list
        //System.out.println("|Adding New Visibable obiect to map:" + _mapName);
        // System.out.println("|--ObiectId [" + object.getObjectId() + "].");
        _visibleObjects.put(new Integer(object.getObjectId()), object);
        //addObject the obiect to specific  placyinmeet optionalise list
        int x = object.getX() / 5;
        int y = object.getY() / 5;
        if (_regions[x][y] == null) {
            //    System.out.println("|--MuPoint [" + x + "," + y + "] not create new... Done");
            _regions[x][y] = new MuMapPoint();
        }
        _regions[x][y].addObject(object);
        // System.out.println("|--Wsp [" + object.getX() + "," + object.getY() + "] At PointMap [" + x + "," + y + "].");
        if (object instanceof MuCharacter) {
            //      System.out.println("|--Obieect is MuCharater kind  Name:[" + ((MuCharacter) object).getName() + "].");
        }
        if (object instanceof MuPcInstance) {
            //    System.out.println("|--Obieect is PcInstance  Name:[" + ((MuPcInstance) object).getName() + "].");
            _allPlayers.put(((MuPcInstance) object).getName().toLowerCase(),
                    object);
        }
    //  System.out.println("|______________________________________");

    }

    /**
     * Remove selceted object from visitable obiects
     * @param object to remove
     */
    public void removeVisibleObject(MuObject object) {
        _visibleObjects.remove(new Integer(object.getObjectId()));
        int x = object.getX() / 5;
        int y = object.getY() / 5;
        //if (_regions[x][y].containsObject(object)) 
        //{
        _regions[x][y].removeID(object.getObjectId());
        //}

        if (object instanceof MuPcInstance) {
            _allPlayers.remove(((MuPcInstance) object).getName().toLowerCase());

        }
    }

    /**
     * return all players on this map
     * @return
     */
    public MuPcInstance[] getAllPlayers() {
        System.out.println("get all player :" + _allPlayers.size());
        return (MuPcInstance[]) _allPlayers.values().toArray(
                new MuPcInstance[_allPlayers.size()]);

    }

    /**
     * @return Colection of  9'th pointson map 
     */
    private Collection<MuObject> GetObiectsFrom9ts(int x, int y) {
        // Currently checking 3x3 blocks (of 5x5 squares, from map partition)
        // Player is in middle
        // Formula to calculate total blocks: (blockrange*2+1)^2
        int blockrange = GameServerConfig.PLAYER_VISIBILITY;
        Collection<MuObject> t = new Vector<MuObject>();
        int x1 = x - blockrange;
        int x2 = x + blockrange;
        int y1 = y - blockrange;
        int y2 = y + blockrange;
        if (x1 < 0) {
            x1 = 0;
        }
        if (x2 > 50) {
            x2 = 50;
        }
        if (y1 < 0) {
            y1 = 0;
        }
        if (y2 > 50) {
            y2 = 0;
        }
        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                t.addAll(_regions[i][j].punkt);
            }
        }
        return t;
    }

    /**
     * getting all visitable  obiect for obiect
     * @param object
     * @return vector
     */
    public Vector<MuObject> getVisibleObjects(MuObject object) {
        //Vector t = new Vector();

        Collection t = GetObiectsFrom9ts(object.getX() / 5, object.getY() / 5);
        //remove myslf
        t.remove(object);
        return new Vector<MuObject>(t);
    }

    /**
     * get all Plyers Nearto Obiect
     * @param object
     * @return Vector of players
     */
    public Vector getVisiblePlayers(MuObject object) {
        Vector<MuPcInstance> _list = new Vector<MuPcInstance>();
        Collection t = getVisibleObjects(object);
        for (Iterator it = t.iterator(); it.hasNext();) {
            Object object1 = it.next();
            if (object1 instanceof MuPcInstance) {
                _list.add((MuPcInstance) object1);
            }
        }
        return _list;
    }

    public void printVisiblePlayers(MuObject obj) {
        Vector list = getVisiblePlayers(obj);
        System.out.println("VisiblleObject for" + obj);
        int i = 0;
        for (Object object : list) {
            i++;
            System.out.println(i + "] " + obj);

        }
    }
    ;

    /**
     * get al visitable obiects
     * @return
     */
    public MuObject[] getVisibleObjects() {
        System.out.println("get vis obj :" + _visibleObjects.size());
        return (MuObject[]) _visibleObjects.values().toArray(
                new MuObject[_visibleObjects.size()]);
    }
}
