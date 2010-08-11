package com.google.code.openmu.gs.muObjects;

import com.google.code.openmu.gs.IdFactory;
import com.google.code.openmu.gs.templates.MuNpc;

public class MuMapSpot {

	private final int _startX; // start x
	private final int _stratY; // y
	private final int _endX;
	private final int _endY;
	private final MuNpc _monster;// type of monster
	private final int _c; // sixe of monsters
	private final MuMap _map;
	private final String _name; // spot name

	MuMapSpot(String name, MuMap map, int startX, int startY, int endX,
			int endY, MuNpc monster, int c) {
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
		System.out.println("-----------------------------Start Spown Spot:"
				+ _name + "-------------------");
		for (int i = 0; i < _c; i++) {
			final MuMonsterInstance mo = new MuMonsterInstance(_monster);
			mo.setObiectId((short) IdFactory.getInstance().newId());
			mo.SetPos((short) (_startX + (Math.random() * (_endX - _startX))),
					(short) (_stratY + (Math.random() * (_endY - _stratY))),
					(int) (Math.random() * 6));
			mo.setWalkArea(new MuMobWalkArea(_startX, _stratY, _endX, _endY, 5));
			mo.setM(_map.getMapCode());
			mo.setCurrentWorldRegion(_map);
			System.out.println("Spown:" + mo);
			MuWorld.getInstance().addObject(mo);
		}
		System.out.println("-----------------------------Start Spown Spot:"
				+ _name + "-------------------");
	}
}
