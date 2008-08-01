package net.sf.jmuserver.gs.muObjects;

import java.util.ArrayList;
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
import net.sf.jmuserver.gs.serverPackage.SForgetId;
import net.sf.jmuserver.gs.serverPackage.SMeetItemOnGround;
import net.sf.jmuserver.gs.serverPackage.SNpcMiting;
import net.sf.jmuserver.gs.serverPackage.SPlayersMeeting;
import net.sf.jmuserver.gs.serverPackage.SToMoveID;
import net.sf.jmuserver.gs.serverPackage.ServerBasePacket;

/**
 * MuMap represents the game entity known as map.<br>
 * It provides means to encapsulate visibile objects and manipulate them
 * in terms of spawn and movement.
 * @see MuObject
 * @see MuWorld
 * @author Marcel
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
        
        public void broadcastPacket(MuObject Client, ServerBasePacket Packet) {
            System.out.println("[MuMapPoint] Stored objects: "+_objects.size());
            for (FastMap.Entry<Integer, MuObject> e = _objects.head(), end = _objects.tail();
                (e = e.getNext()) != end;) {
                MuObject obj = e.getValue();
                if ((Client != obj) && (obj instanceof MuPcInstance)) {
                    ((MuPcInstance)obj).sendPacket(Packet);
                }
             }            
        }
        
        public void broadcastPacket(ServerBasePacket Packet) {
            broadcastPacket(null, Packet);
        }
        
        public void sendMeetingPackets(MuPcInstance Player) {
            System.out.println("[MuMapPoint] Stored objects: "+_objects.size());            
            for (FastMap.Entry<Integer, MuObject> e = _objects.head(), end = _objects.tail();
                (e = e.getNext()) != end;) {
                MuObject obj = e.getValue();
                ArrayList<MuObject> objA = new ArrayList<MuObject>();
                objA.add(obj);
                if (obj instanceof MuItemOnGround)
                    Player.sendPacket(new SMeetItemOnGround(objA));
                else if (obj instanceof MuMonsterInstance) 
                    Player.sendPacket(new SNpcMiting(objA));
                else
                    Player.sendPacket(new SPlayersMeeting(objA));
             }          
        }
        
    }    
    
    // All players visible on map
    private Map<String, MuObject> _allPlayers;  
    // All object visitable on map
    private Map<Integer, MuObject> _allObjects;    
    private MuMapPoint[][] _regions = new MuMapPoint[86][86];    
    private byte _mapCode;
    private String _mapName;
    
     /**
     * Initialize map.
     * @param m map id (byte code)
     * @param MapName name of map
     */
    public MuMap(int m, String MapName) {
        _mapCode = (byte) m;
        _mapName = MapName;
        _allPlayers = new HashMap<String, MuObject>();
        _allObjects = new HashMap<Integer, MuObject>();
        for (int i = 0; i <= 85; i++) {
            for (int j = 0; j <= 85; j++) {
                _regions[i][j] = new MuMapPoint();
            }
        }
    }

    /**
     * The method returns the map name.
     * @return the map name
     */
    public String getMapName() {
        return _mapName;
    }

    /**
     * The method returns the map ID.
     * @return return code of map
     */
    public byte getMapCode() {
        return _mapCode;
    }
    
    /**
     * Given the region X and Y coordinates (max. 85), the function broadcasts
     * a packet to all player instances, except the one represented by the
     * first paramater, that can be found in the visibile player range.
     * @param Client The exception player when broadcasting
     * @param RegionX The X coordinate of the region
     * @param RegionY The Y coordinate of the region
     * @param Packet The ServerBasePacket to be sent
     */
    public void broadcastPacketWideArea(MuObject Client, int RegionX, 
            int RegionY, ServerBasePacket Packet) {
        int x1 = RegionX - GameServerConfig.PLAYER_VISIBILITY;
        int x2 = RegionX + GameServerConfig.PLAYER_VISIBILITY;
        int y1 = RegionY - GameServerConfig.PLAYER_VISIBILITY;
        int y2 = RegionY + GameServerConfig.PLAYER_VISIBILITY;  
        System.out.println("Broadcasting packet in areas ["+x1+"]["+y1
                +"] to ["+x2+"]["+y2+"]");
        if (x1<0)
            x1 = 0;
        if (y1<0)
            y1 = 0;
        if (x2>85)
            x2 = 85;
        if (y2>85)
            y2 = 85;
        if (x1>x2) {
            x1 = x1 ^ x2;
            x2 = x1 ^ x2;
            x1 = x1 ^ x2;
        }
        if (y1>y2) {
            y1 = y1 ^ y2;
            y2 = y1 ^ y2;
            y1 = y1 ^ y2;
        }
        System.out.println("New values are ["+x1+"]["+y1
                +"] to ["+x2+"]["+y2+"]");        
        for (;x1<=x2;x1++)
            for (;y1<=y2;y1++) {
                System.out.println("Broadcasting to "+x1+" "+y1+" (limit: "+x2+" "+y2+")");
                _regions[x1][y1].broadcastPacket(Client, Packet);
            }               
    }
    
    /**
     * Given the region X and Y coordinates (max. 85), the function broadcasts
     * a packet to all player instances, except the one represented by the
     * first paramater, that can be found in the visibile player range.
     * @param Client The exception player when broadcasting
     * @param RegionX The X coordinate of the region
     * @param RegionY The Y coordinate of the region
     * @param Packet The ServerBasePacket to be sent
     */    
    public void broadcastPacketWideArea(int RegionX, int RegionY, 
            ServerBasePacket Packet) {
            broadcastPacketWideArea(null, RegionX, RegionY, Packet);
    }
    
    /**
     * This method sends packets to the given player "from" all the objects
     * in the visibility range. It is mostly used for "meeting" packets.
     * @param Player The player that receives the packets
     */
    public void sendMeetingPackets(MuPcInstance Player, int RegionX, int RegionY) {
        int x1 = RegionX - GameServerConfig.PLAYER_VISIBILITY;
        int x2 = RegionX + GameServerConfig.PLAYER_VISIBILITY;
        int y1 = RegionY - GameServerConfig.PLAYER_VISIBILITY;
        int y2 = RegionY + GameServerConfig.PLAYER_VISIBILITY;  
        System.out.println("Sending meeting packets from areas ["+x1+"]["+y1
                +"] to ["+x2+"]["+y2+"]");
        if (x1<0)
            x1 = 0;
        if (y1<0)
            y1 = 0;
        if (x2>85)
            x2 = 85;
        if (y2>85)
            y2 = 85;
        if (x1>x2) {
            x1 = x1 ^ x2;
            x2 = x1 ^ x2;
            x1 = x1 ^ x2;
        }
        if (y1>y2) {
            y1 = y1 ^ y2;
            y2 = y1 ^ y2;
            y1 = y1 ^ y2;
        }
        System.out.println("New values are ["+x1+"]["+y1
                +"] to ["+x2+"]["+y2+"]");        
        for (;x1<=x2;x1++)
            for (;y1<=y2;y1++) {
                System.out.println("Receiving meeting packets from: "+x1+" "+y1+" (limit: "+x2+" "+y2+")");
                _regions[x1][y1].sendMeetingPackets(Player);        
            }                
    }
    
    /**
     * Moves a MuCharacter to a new coordinate. The action considers the
     * player visibility range, and broadcasts movement, spawn and "forget"
     * packets appropriately. <br> The method uses the new and old coordinates
     * of the MuCharacter.
     * @param Who The character to be moved
     * @param x The new X coordinate
     * @param y The new Y coordinate
     * @return False if character is not allowed to move there, true otherwise
     */
    boolean moveCharacter(MuCharacter Who) {
        int x1 = (Who.getOldX() / 3);
        int y1 = (Who.getOldY() / 3);
        int x2 = (Who.getX() / 3);
        int y2 = (Who.getY() / 3);
        // Check if player is allowed to move there
        if ((Math.abs(x1-x2)>GameServerConfig.PLAYER_VISIBILITY) ||
                (Math.abs(y1-y2)>GameServerConfig.PLAYER_VISIBILITY))
            return false;   
        // Let players who see you that you move
        broadcastPacketWideArea(Who, x1, x2, new SToMoveID(
                    getMapCode(), Who.getX(), Who.getY(), (byte)0x01));
        // If character moved in the same MuMapPoint, do nothing else.
        if ((x1 == x2) && (y1 == y2))            
            return true;
        // If the character moved into a different MuMapPoint, we must
        // send the appropriate "meet" and "forget" packets, and add 
        // the object to the new MuMapPoint
        int xDirection = 0;
        int yDirection = 0;
        int i,j;
        ArrayList<MuObject> WhoArray = new ArrayList<MuObject>();
        WhoArray.add(Who);
        xDirection = (x2>x1)?1:-1;
        yDirection = (y2>y1)?1:-1;
        // Send and "receive" meeting packets
        for (i=(x1+xDirection*(GameServerConfig.PLAYER_VISIBILITY+1));
            i<(x2+xDirection*(GameServerConfig.PLAYER_VISIBILITY+1));
            i += xDirection)
            for (j=(y1+yDirection*(GameServerConfig.PLAYER_VISIBILITY+1));
                j<(y2+yDirection*(GameServerConfig.PLAYER_VISIBILITY+1));
                j += yDirection) {
                _regions[i][j].broadcastPacket(Who, new SPlayersMeeting(WhoArray));
                if (Who instanceof MuPcInstance)
                    _regions[i][j].sendMeetingPackets((MuPcInstance)Who);
            }
        // Send "forget" packets
        xDirection *= -1;
        yDirection *= -1;
        for (i=(x1+xDirection*(GameServerConfig.PLAYER_VISIBILITY+1));
            i<(x2+xDirection*(GameServerConfig.PLAYER_VISIBILITY+1));
            i += xDirection)
            for (j=(y1+yDirection*(GameServerConfig.PLAYER_VISIBILITY+1));
                j<(y2+yDirection*(GameServerConfig.PLAYER_VISIBILITY+1));
                j += yDirection)
                _regions[i][j].broadcastPacket(Who, new SForgetId(Who));
        return true;
    }

    /**
     * The function adds the given object into the map, at the right position
     * and region (MuMapPoint). <br>It includes broadcasting the appropriate 
     * "spawn" packets.
     * @param object The MuObject to be added.
     * @return False if the object is already on the map, true otherwise
     */
    public boolean addObject(MuObject object) {
        System.out.println("Total objects on map: "+_allObjects.size());
        // Test if object already exists on the map
        if (_allObjects.containsValue(object))
            return false;
        // Add object to full list
        System.out.println("|Adding New Visibable obiect to map:" + _mapName);
        System.out.println("|--ObiectId [" + object.getObjectId() + "].");
        _allObjects.put(new Integer(object.getObjectId()), object);
        // Add object to the corresponding MuMapPoint
        int x = object.getX() / 3;
        int y = object.getY() / 3;
        if (_regions[x][y] == null) {
            System.out.println("|--MuPoint [" + x + "," + y + "] not create new... Done");
            _regions[x][y] = new MuMapPoint();
        }
        _regions[x][y].addObject(object);        
        System.out.println("|--Wsp [" + object.getX() + "," + object.getY() + "] At PointMap [" + x + "," + y + "].");
//        if (object instanceof MuCharacter) {
//            System.out.println("|--Obieect is MuCharater kind  Name:[" + ((MuCharacter) object).getName() + "].");
//        }
        if (object instanceof MuPcInstance) {
            System.out.println("|--Obieect is PcInstance  Name:[" + ((MuPcInstance) object).getName() + "].");
            _allPlayers.put(((MuPcInstance) object).getName().toLowerCase(),
                    object);
        }
        System.out.println("|______________________________________");
        // Broadcast spawn packet
        ArrayList<MuObject> WhoArray = new ArrayList<MuObject>();
        WhoArray.add(object);        
        if (object instanceof MuItemOnGround)
            broadcastPacketWideArea(object, x, y, new SMeetItemOnGround(WhoArray));
        else if (object instanceof MuMonsterInstance)
            broadcastPacketWideArea(object, x, y, new SNpcMiting(WhoArray));
        else {
            broadcastPacketWideArea(object, x, y, new SPlayersMeeting(WhoArray));
            // Inform player of surrounding objects
            if (object instanceof MuPcInstance)
                sendMeetingPackets((MuPcInstance)object, x, y);        
        }
        return true;
    }

    /**
     * Remove the given object from the map.<br>
     * It includes sending the appropriate "forget" packets.
     * @param object The object to be remove
     */
    public void removeObject(MuObject object) {
        _allObjects.remove(new Integer(object.getObjectId()));
        int x = object.getX() / 3;
        int y = object.getY() / 3;
        _regions[x][y].removeObject(object.getObjectId());
        if (object instanceof MuPcInstance) {
            _allPlayers.remove(((MuPcInstance) object).getName().toLowerCase());
        }
    }

//    /**
//     * return all players on this map
//     * @return
//     */
//    public MuPcInstance[] getAllPlayers() {
//        System.out.println("get all player :" + _allPlayers.size());
//        return (MuPcInstance[]) _allPlayers.values().toArray(
//                new MuPcInstance[_allPlayers.size()]);
//
//    }
//
//    /**
//     * @return Colection of  9'th pointson map 
//     */
//    private Collection<MuObject> GetObiectsFrom9ts(int x, int y) {
//        // Currently checking 3x3 blocks (of 5x5 squares, from map partition)
//        // Player is in middle
//        // Formula to calculate total blocks: (blockrange*2+1)^2
//        int blockrange = GameServerConfig.PLAYER_VISIBILITY;
//        Collection<MuObject> t = new Vector<MuObject>();
//        int x1 = x - blockrange;
//        int x2 = x + blockrange;
//        int y1 = y - blockrange;
//        int y2 = y + blockrange;
//        if (x1 < 0) {
//            x1 = 0;
//        }
//        if (x2 > 50) {
//            x2 = 50;
//        }
//        if (y1 < 0) {
//            y1 = 0;
//        }
//        if (y2 > 50) {
//            y2 = 0;
//        }
//        for (int i = x1; i <= x2; i++) {
//            for (int j = y1; j <= y2; j++) {
//                t.addAll(_regions[i][j].punkt);
//            }
//        }
//        return t;
//    }
//
//    /**
//     * getting all visitable  obiect for obiect
//     * @param object
//     * @return vector
//     */
//    public Vector<MuObject> getVisibleObjects(MuObject object) {
//        //Vector t = new Vector();
//
//        Collection t = GetObiectsFrom9ts(object.getX() / 5, object.getY() / 5);
//        //remove myslf
//        t.remove(object);
//        return new Vector<MuObject>(t);
//    }
//
//    /**
//     * get all Plyers Nearto Obiect
//     * @param object
//     * @return Vector of players
//     */
//    public Vector getVisiblePlayers(MuObject object) {
//        Vector<MuPcInstance> _list = new Vector<MuPcInstance>();
//        Collection t = getVisibleObjects(object);
//        for (Iterator it = t.iterator(); it.hasNext();) {
//            Object object1 = it.next();
//            if (object1 instanceof MuPcInstance) {
//                _list.add((MuPcInstance) object1);
//            }
//        }
//        return _list;
//    }
//
//    public void printVisiblePlayers(MuObject obj) {
//        Vector list = getVisiblePlayers(obj);
//        System.out.println("VisiblleObject for" + obj);
//        int i = 0;
//        for (Object object : list) {
//            i++;
//            System.out.println(i + "] " + obj);
//
//        }
//    }
//    ;
//
//    /**
//     * get al visitable obiects
//     * @return
//     */
//    public MuObject[] getVisibleObjects() {
//        System.out.println("get vis obj :" + _allObjects.size());
//        return (MuObject[]) _allObjects.values().toArray(
//                new MuObject[_allObjects.size()]);
//    }
}
