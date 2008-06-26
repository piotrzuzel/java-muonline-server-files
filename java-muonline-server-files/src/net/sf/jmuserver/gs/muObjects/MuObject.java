package net.sf.jmuserver.gs.muObjects;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.jmuserver.utils.CopyOnWriteArrayList;







/**
 * @author Miki
 * @since basicly class ofevery obiect in game
 */

public class MuObject {

	private MuMap _region;
	private short _ObiectId;
	protected short _myType; //00 player // 1 npc // 2 mob // 3 item // 4 ?
	private short _x;// y


	private short _y;// y

	private short _m;// map;

	private short _s; // status
	/**
	 * list of knowns obiect
	 */
	protected List<MuObject> _knownObjects=new  CopyOnWriteArrayList();

	/**
	 * aet of players knowns
	 */
	private Set _knownPlayer = new HashSet();

	public MuObject()
	{

	}

	/**
	 * @param obiectId id obiektu
	 * @param _x wsp x na mapie
	 * @param _y wsp y na mapie
	 * @param _m mapa
	 */
	public MuObject(short obiectId, short _x, short _y, short _m) {
		super();
		_ObiectId = obiectId;
		this._x = _x;
		this._y = _y;
		this._m = _m;
	}
	/**
	 * added new obiect to kowns
	 * @param object added obiect
	 */
	public void addKnownObject(MuObject object) {
		_knownObjects.add(object);
		if (object instanceof MuPcInstance) {
			_knownPlayer.add(object);
		}
	}


	/**
	 * @param s change status on s
	 */
	public void changeStatus(int s)
	{
		_s=(short) s;
	}

	/**
	 * geting atual map
	 * @return zwraca mape
	 */
	public MuMap getCurrentWorldRegion() {
		return _region;
	}

	/**
	 * @return lista widzianch obiektow
	 */
	public List getKnownObjects() {
		return _knownObjects;
	}

	/**
	 * @return zbior widzianych graczy
	 */
	public Set getKnownPlayers() {
		return _knownPlayer;
	}

	/**
	 * @return mapaId
	 */
	public int getM() {
		return _m;
	}

	public short getMyType()
	{
		return _myType;
	}

	/**
	 * @return id Obiektu
	 */
	public int getObjectId() {
		return _ObiectId;
	}

	/**
	 * @return status obiektu
	 */
	public short getStatus()
	{
		return _s;
	}

	/**
	 * @return wsp x
	 */
	public int getX() {
		return _x;
	}

	/**
	 * @return wsp y
	 */
	public int getY() {
		return _y;
	}

        
    public boolean isVisible() {
      return true;
    }
	/**
	 * usuwa wszystkire koligacjie ztym obiektem
	 */
	public void removeAllKnownObjects() {
		MuObject[] notifyList = _knownObjects
				.toArray(new MuObject[_knownObjects.size()]);
		// clear our own list
		_knownObjects.clear();

		for (int i = 0; i < notifyList.length; i++) {
			notifyList[i].removeKnownObject(this);
		}
	}

	/**
	 * @param object usuwa z listy znanych dany obiekt obiejct
	 */
	public void removeKnownObject(MuObject object) {
		_knownObjects.remove(object);
		if (object instanceof MuPcInstance) {
			_knownPlayer.remove(object);
		}
	}


	/**
	 * luking for  id in knownsobiet
	 * @param id wyszukiwany obiekt
	 * @return czy znaleziono
	 */
	public boolean searchID(int id)
	{
		for(int i=0;i<_knownObjects.size();i++)//sometimesnull exception
		{
		if(_knownObjects.get(i).getObjectId()==id) {
		    return true;
		}
		}
		return false;
	}
	/**
	 * metoda ustawia aktualan mape
	 * @param region aktualna mapa
	 */
	public void setCurrentWorldRegion(MuMap region) {
		_region = region;
	};

	/**
	 * ustawia mape = m
	 * @param m
	 */
	public void setM(byte m) {
		_m = m;
	}

	/**
	 * ustawia id obiektu na id
	 * @param id
	 */
	public void setObiectId(short id) {
		_ObiectId = id;
	}

	/**
	 * @param s - status czyli gdzie obiekt patrzy itp
	 */
	public void setStatus(int s)
	{
		_s=(short) s;
	}

	/**
	 * ustawia x na _newx
	 * @param _newx
	 */
	public void setX(int _newx) {
		_x = (short) _newx;
	}

	/**
	 * ustawia y na _newy
	 * @param _newy
	 */
	public void setY(int _newy) {
		_y = (short) _newy;
	}
	/**
	 * update maps  after it changes
	 */
	public void updateCurrentWorldRegion() {
		MuMap newRegion = MuWorld.getInstance().getRegion(getM());
		if (!newRegion.equals(_region)) {
			if (_region != null) {
			    _region.removeVisibleObject(this);
			}
			newRegion.addVisibleObject(this);
			_region = newRegion;
		}
	}
public void Spown()
{
    
    
}
/**
 * Fast set location inf on map
 * @param x x wsp
 * @param y wsp
 * @param f where look
 */
public void SetPos(int x,int y,int f)
{
    setX(x);
    setY(y);
    setStatus(f);
}
public void UseeMe(MuObject o)
{
    int SeeId=o.getObjectId();
    if (!searchID(SeeId)) addKnownObject(o);
    System.out.println("Gobiekt:"+SeeId + " toll me i will se it");
    
}
        
}
