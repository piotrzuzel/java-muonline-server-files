package net.sf.jmuserver.gs.muObjects;

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
        }
    MuMapPoint[][] _mapa = new MuMapPoint[256][256];
    private byte _mapCode;
    private String _mapName;
    /**
     * wszyscy gracze na mapie
     */
    private Map<String, MuObject> _allPlayers;
    /**
     * wszystkie obiekty widocznie na mapie
     */
    private Map<Integer, MuObject> _visibleObjects;

    //private ArrayList _surroundingRegions;
    public MuMap(int m) {
        _mapCode = (byte) m;
        _allPlayers = new HashMap<String, MuObject>();
        _visibleObjects = new HashMap<Integer, MuObject>();
        for(int i =0;i<256;i++)
            for(int j=0;j<256;j++)
                _mapa[i][j]=new MuMapPoint();
    //	_surroundingRegions = new ArrayList();
    // _surroundingRegions.add(this); //done in L2World.initRegions()
    }

    /**
     * dodaje do mapy obiet jako widoczny jesli jest graczem dodjae go do listy graczy rowiez
     * @param object
     */
    public void addVisibleObject(MuObject object) {
        //add to basic  list
        _visibleObjects.put(new Integer(object.getObjectId()), object);
        //add the obiect to specific  placyinmeet optionalise list
        int x = object.getX();
        int y = object.getY();
        if(_mapa[x][y]==null)_mapa[x][y]=new MuMapPoint();
        _mapa[x][y].add(object);

        if (object instanceof MuPcInstance) {
            _allPlayers.put(((MuPcInstance) object).getName().toLowerCase(),
                    object);
        }
        System.out.println(_visibleObjects.size() + "]Added new to map -" + object.getObjectId() + " as " + object.getMyType());
    }

    /**
     * usuwa dany obiekt z mapy
     * @param object
     */
    public void removeVisibleObject(MuObject object) {
        _visibleObjects.remove(new Integer(object.getObjectId()));
        int x = object.getX();
        int y = object.getY();
        //if (_mapa[x][y].conteins(object)) 
        //{
        _mapa[x][y].removeID(object.getObjectId());
        //}

        if (object instanceof MuPcInstance) {
            _allPlayers.remove(((MuPcInstance) object).getName().toLowerCase());

        }
    }

    /**
     * zwraca liste wszystkich graczy na  danej mapie
     * @return
     */
    public MuPcInstance[] getAllPlayers() {
        System.out.println("get all player :" + _allPlayers.size());
        return (MuPcInstance[]) _allPlayers.values().toArray(
                new MuPcInstance[_allPlayers.size()]);

    }

    public Vector getVisibleObjects(MuObject object, int radius) {
        Vector t = new Vector();

        //troche obliiczen na poczatek
        //zakresy
        int x1 = object.getX() - radius;
        int x2 = object.getX() + radius;
        int y1 = object.getY() - radius;
        int y2 = object.getY() + radius;
        //sprawdzanie krawnendzi;
        if (x1 < 0) {
            x1 = 0;
        } // minimalny 
        if (y1 < 0) {
            y1 = 0;
        } // minimalny
        if (x2 > 256) {
            x2 = 256;
        }//max
        if (y2 > 256) {
            y2 = 256;
        }//max
        //teraz sumam zbiorow wylaczajac zbior z  pozycjiobiectxy 
        for (int xpos = x1; xpos < x2; xpos++) {
            for (int ypos = y1; ypos < y2; ypos++) {
                if ((xpos == object.getX()) && (ypos == object.getY())) {
                    if (_mapa[xpos][ypos].punkt.size() != 1) {
                        for (int t1 = 0;
                                t1 < (_mapa[xpos][ypos].punkt.size()); t1++) {
                            if (_mapa[xpos][ypos].punkt.elementAt(t1).getObjectId() == object.getObjectId()) {
                                continue;
                            }
                            t.add(_mapa[xpos][ypos].punkt.elementAt(t1));
                        }
                    }
                } else {
                    t.addAll(_mapa[xpos][ypos].punkt);
                }

            }
        }


        return t;
    }

    /**
     * pobiera wszystkie obiekty a mapie
     * @return
     */
    public MuObject[] getVisibleObjects() {
        System.out.println("get vis obj :" + _visibleObjects.size());
        return (MuObject[]) _visibleObjects.values().toArray(
                new MuObject[_visibleObjects.size()]);
    }
}
