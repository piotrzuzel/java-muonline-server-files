package net.sf.jmuserver.gs.muObjects;

import net.sf.jmuserver.gs.IdFactory;
import net.sf.jmuserver.gs.templates.MuNpc;

public class MuMapSpot {

    private int _startX;	//start x
    private int _stratY;	//y
    private int _endX;
    private int _endY;
    private MuNpc _monster;// type of monster
    private int _c;			//sixe of monsters
    private MuMap _map;
    private String _name;	//spot name

 
    MuMapSpot(String name, MuMap map, int startX, int startY, int endX, int endY, MuNpc monster, int c) {
        _name = name;
        _map = map;
        _startX = startX;
        _stratY = startY;
        _endX = endX;
        _endY = endY;
        _monster = monster;
        _c = c;
    }

    public void InitSpot() {
        System.out.println("-----------------------------Start Spown Spot:"+_name +"-------------------");
        for (int i = 0; i < _c; i++) {
            MuMonsterInstance mo = new MuMonsterInstance(_monster);
            mo.setObiectId((short) IdFactory.getInstance().newId());
            mo.SetPos(
                    (short) (_startX + (Math.random() * ( _endX-_startX ))),
                    (short) (_stratY + (Math.random() * ( _endY-_stratY))),
                    (int) (Math.random() * 6));
            mo.setWalkArea(new MuMobWalkArea( _startX, _stratY,_endX, _endY, 5));
            mo.setM(_map.getMapCode());
            mo.setCurrentWorldRegion(_map);
            System.out.println("Spown:"+mo);
            MuWorld.getInstance().addObject(mo);
        }
        System.out.println("-----------------------------Start Spown Spot:"+_name +"-------------------");
    }
}
