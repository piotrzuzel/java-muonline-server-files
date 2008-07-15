package net.sf.jmuserver.gs.muObjects;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import net.sf.jmuserver.gs.muObjects.MuMap.MuMapPoint;

/**
 * This class ...
 * 
 * @version $Revision: 1.3 $ $Date: 2004/11/02 14:33:16 $
 */
public class MuMap {

    /**
     * MOvung to new position Update Maps
     * @param Who who move
     * @param x new x pos
     * @param y new y os
     * @return true when done
     */
    boolean MoveTo(MuCharacter Who, int x, int y) {
        int actX = Who.getX();
        int actY = Who.getY();
        int x1 = actX / 5;
        int y1 = actY / 5;
        int x2 = x / 5;
        int y2 = y / 5;
        //if there onthis some point
        if ((x1 == x2) && (y1 == y2)) {
            return true;
        }
        _mapa[x1][y1].punkt.remove(Who);
        _mapa[x2][y2].punkt.add(Who);
        return true;
    }

    /**
     * The MapPoint represent squer  5x5 points
     */
    class MuMapPoint {

        public MuMapPoint() {
            //System.out.println("next");
        }
        public Vector<MuObject> punkt = new Vector<MuObject>();

        public boolean isEmpty() {
            return punkt.isEmpty();
        }

        public void add(MuObject t) {
            punkt.add(t);
        }

        public boolean conteins(MuObject t) {
            for (Iterator<MuObject> it = punkt.iterator(); it.hasNext();) {
                MuObject muObject = it.next();
                if (muObject.getObjectId() == t.getObjectId()) {
                    return true;
                }
            }
            return false;
        }

        public void removeID(int Id) {
            int ss = -1;
            for (int s = 0; s < punkt.size(); s++) {
                MuObject t = punkt.elementAt(s);
                if (t.getObjectId() == Id) {
                    ss = s;
                }
            }
            if (ss == -1) {
                return;
            }

            punkt.remove(ss);
        }

        public void PrintData() {
            for (Iterator<MuObject> it = punkt.iterator(); it.hasNext();) {
                MuObject muObject = it.next();
                System.out.println(muObject);
            }
        }
    }
    MuMapPoint[][] _mapa = new MuMapPoint[51][51];
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
                _mapa[i][j] = new MuMapPoint();
            }
        }
    }

    /**
     * Added to map obiect
     * @param object
     */
    public void addVisibleObject(MuObject object) {
        //add to basic  list
        System.out.println("|Adding New Visibable obiect to map:" + _mapName);
        System.out.println("|--ObiectId [" + object.getObjectId() + "].");
        _visibleObjects.put(new Integer(object.getObjectId()), object);
        //add the obiect to specific  placyinmeet optionalise list
        int x = object.getX() / 5;
        int y = object.getY() / 5;
        if (_mapa[x][y] == null) {
            System.out.println("|--MuPoint [" + x + "," + y + "] not create new... Done");
            _mapa[x][y] = new MuMapPoint();
        }
        _mapa[x][y].add(object);
        System.out.println("|--Wsp [" + object.getX() + "," + object.getY() + "] At PointMap [" + x + "," + y + "].");
        if (object instanceof MuCharacter) {
        System.out.println("|--Obieect is MuCharater kind  Name:[" + ((MuCharacter) object).getName() + "].");
        }
        if (object instanceof MuPcInstance) {
            System.out.println("|--Obieect is PcInstance  Name:[" + ((MuPcInstance) object).getName() + "].");
            _allPlayers.put(((MuPcInstance) object).getName().toLowerCase(),
                    object);
        }
        System.out.println("|______________________________________");

    }

    /**
     * Remove selceted object from visitable obiects
     * @param object to remove
     */
    public void removeVisibleObject(MuObject object) {
        _visibleObjects.remove(new Integer(object.getObjectId()));
        int x = object.getX() / 5;
        int y = object.getY() / 5;
        //if (_mapa[x][y].conteins(object)) 
        //{
        _mapa[x][y].removeID(object.getObjectId());
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
        int blockrange = 1;
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
                t.addAll(_mapa[i][j].punkt);
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
