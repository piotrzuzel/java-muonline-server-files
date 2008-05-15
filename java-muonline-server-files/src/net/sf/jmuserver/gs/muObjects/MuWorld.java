
package net.sf.jmuserver.gs.muObjects;

import java.util.ArrayList;
import java.util.HashMap;
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

	private MuMap[] _worldRegions={null,null,null,null};

	private MuWorld() {
		_allPlayers = new HashMap<Integer,MuObject>();
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
		System.out.println(_allObjects.size()+"] Dodaje nowego : "+ temp.getObjectId()+" jako "+temp.getMyType());
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

	public MuPcInstance[] getAllPlayers() {
		return (MuPcInstance[]) _allPlayers.values().toArray(
				new MuPcInstance[_allPlayers.size()]);
	}

	public MuPcInstance getPlayer(String name) {
		return (MuPcInstance) _allPlayers.get(name.toLowerCase());
	}

	/*
	 * New L2WorldRegion stuff
	 */

	public void addVisibleObject(MuObject object) {
		// update region info for object
		// object.updateCurrentWorldRegion();

		if (object instanceof MuPcInstance) {
			// old stuff to remember all players
			_allPlayers.put(((MuPcInstance) object).getName().toLowerCase(),
					object);

			// get all visible objects around
			Vector visible = getVisibleObjects(object, 2000);

			// tell the player about the surroundings
			for (int i = 0; i < visible.size(); i++) {
				object.addKnownObject((MuObject)visible.elementAt(i));
				//if (object instanceof MuItemInstance
				//		&& visible[i].getKnownObjects().contains(object)) {
					// special case for droped items. they are already known
					// when they appear in the world
			//	} 
				//else {
					((MuObject)visible.elementAt(i)).addKnownObject(object);
				//}
			}
		} else if (!(object instanceof MuPetInstance)
			//	&& !(object instanceof MuItemInstance)
				) 
		{// for npcs to be
															// seen by players
															// if they pop up in
															// world.
			int x = object.getX();
			int y = object.getY();
			int sqRadius = 2000 * 2000;

			// get all regions around
			MuMap _regions = object.getCurrentWorldRegion();

			// update info for each player in surrounding regions if needed
			MuPcInstance[] _players = _regions.getAllPlayers();
			System.out.println("znalazlem :"+_players.length+"graczy" );
			for (int j = 0; j < _players.length; j++) {
				int x1 = _players[j].getX();
				int y1 = _players[j].getY();

				long dx = x1 - x;
				long dy = y1 - y;
				long sqDist = dx * dx + dy * dy;
				if (sqDist < sqRadius) {
					_players[j].addKnownObject(object);
					object.addKnownObject(_players[j]);
				}

			}
		}

	}

	public void removeVisibleObject(MuObject object) {
		// update known objects
		Object[] temp = object.getKnownObjects().toArray();
		for (int i = 0; i < temp.length; i++) {
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
	 * returns all visible objects in range
	 * 
	 * @param object
	 * @param radius
	 * @return
	 */

                public Vector getVisibleObjects(MuObject object, int radius) {
                    
//		int x = object.getX();
//		int y = object.getY();
//		int sqRadius = radius * radius;
//		ArrayList result = new ArrayList();
//
//		// get all regions around
		MuMap _regions = object.getCurrentWorldRegion();
//
//		// get visible objects in region & do stuff if needed
		Vector _objects =
			 _regions.getVisibleObjects(object,radius);
//		for (int j = 0; j < _objects.length; j++) {
//			System.out.println(j+"]Obj:"+_objects[j].getObjectId()+"		wsp["+_objects[j].getX()+","+_objects[j].getY()+"].");
//			if (_objects[j].equals(object))
//				continue; // skip our own character
//			
//			int x1 = _objects[j].getX();
//			int y1 = _objects[j].getY();
//
	//		long dx = x1 - x;
	//		long dy = y1 - y;
	//		long sqDist = dx * dx + dy * dy;
	//		if (sqDist < sqRadius) {
	//			result.add(_objects[j]);
	//		}
	//	}

	//	return (MuObject[]) result.toArray(new MuObject[result.size()]);
                return _objects;
	}

	public MuMap getRegion(int x) {
		return _worldRegions[x];
	}

	private void initRegions() {
		_worldRegions[0]= new MuMap(0);
		MuNpc paj=new MuNpc();
		paj.setName("Spider");
		paj.setNpcId(1);
		paj.setMaxHp(5000);
		//1		0	0	2	40	0	6	8	1	0	10	1	2	0	1	5	400	1800	10	2	120	10

		MuMonsterInstance mo=new MuMonsterInstance(paj);
		mo.setObiectId((short) IdFactory.getInstance().newId());
		//mo.setNpcTemplate(paj);
		mo.setX(176);
		mo.setY(126);
		mo.setM((byte) 0);
		mo.setCurrentWorldRegion(_worldRegions[0]);
		MuMonsterInstance m1=new MuMonsterInstance(paj);
		m1.setObiectId((short) IdFactory.getInstance().newId());
		//m1.setNpcTemplate(paj);
		m1.setX(177);
		m1.setY(126);
		m1.setM((byte) 0);
		m1.setCurrentWorldRegion(_worldRegions[0]);
		
		storeObject(mo);
		storeObject(m1);
		_worldRegions[1]= new MuMap(1);
		_worldRegions[2]= new MuMap(2);

	}
}