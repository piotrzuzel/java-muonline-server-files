package net.sf.jmuserver.gs.muObjects;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.jmuserver.utils.CopyOnWriteArrayList;







/**
 * @author Miki
 * @since podstawowa klasa do zazadzania obiektami na mapie
 */

public class MuObject {

	private MuMap _region;
	private short _ObiectId;
	protected short _myType; //00 player // 1 npc // 2 mob // 3 item // 4 ?
	private short _x;// y


	private short _y;// y

	private short _m;// mapa;

	private short _s; // status
	/**
	 * lista znanych obiektow prez dany obiekt
	 */
	protected List<MuObject> _knownObjects=new  CopyOnWriteArrayList();

	/**
	 * zbior graczy znanych przez obiekt
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
	 * metoda dodaje nowy obiekt do listy znanych[widzianych]
	 * @param object dodwany obiekt do znanych
	 */
	public void addKnownObject(MuObject object) {
		_knownObjects.add(object);
		if (object instanceof MuPcInstance) {
			_knownPlayer.add(object);
		}
	}


	/**
	 * @param s zmienia status na s
	 */
	public void changeStatus(int s)
	{
		_s=(short) s;
	}

	/**
	 * pobiera aktualna mape
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
	 * metda przeszukuje znane obiekty szukajac danego id'a
	 * @param id wyszukiwany obiekt
	 * @return czy znaleziono
	 */
	public boolean searchID(int id)
	{
		for(int i=0;i<_knownObjects.size();i++)//KORWA MAC!!!!!!!!! NULL EXCEPTION
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
	 * aktualizuje mapy i liste znanych obiektow po zmianie mapy
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
public void UseeMe(MuObject o)
{
    int SeeId=o.getObjectId();
    if (!searchID(SeeId)) addKnownObject(o);
    System.out.println("Gobiekt:"+SeeId + " dal mi cyk  ze mnie widzi");
    
}
        
}
