/*
 * Copyright [mikiones] [Michal Kinasiewicz]
 * 			 [marcel]   [Marcel Gheorghita] 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.code.openmu.gs.muObjects;

import com.google.code.openmu.gs.templates.MuNpc;
import com.google.code.openmu.gs.templates.MuWeapon;

public class MuNpcInstance extends MuCharacter {
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.code.openmu.gs.muObjects.MuCharacter#startAttack(com.google.code.openmu
	 * .gs.muObjects.MuCharacter)
	 */

	@Override
	public void startAttack(MuCharacter target) {
		super.startAttack(target);
	}

	public MuNpcInstance(MuNpc temp) {
		_npcTemplate = temp;
		_objectType = 1;
		setName(temp.getName());

	}

	private boolean _isAtackable;
	private MuNpc _npcTemplate;

	/**
	 * set weward exp when die
	 * 
	 * @param i
	 *            exp
	 */
	public void setExpReward(int i) {
	}

	/**
	 * @return reward exp for die a
	 */
	public int getExpReward() {
		return 10;// _expReward;
	}

	/**
	 * @return zwraca id moba/mpc
	 */
	public int getNpcId() {
		return _npcTemplate.getNpcId();
	}

	/**
	 * @return czy mozna go atakowac
	 */
	public boolean isAtackable() {
		return _isAtackable;
	}

	/**
	 * ustawia flage czy mozna go atakowac
	 * 
	 * @param b
	 *            flaga
	 */
	public void setAtackable(boolean b) {
		_isAtackable = b;
	}

	/**
	 * @return bazowe statystyki npc
	 */
	public MuNpc getNpcTemplate() {
		return _npcTemplate;
	}

	/**
	 * ustawia npctemplae
	 * 
	 * @param npcTemplate
	 */
	public void setNpcTemplate(MuNpc npcTemplate) {
		_npcTemplate = npcTemplate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.code.openmu.gs.muObjects.MuCharacter#getActiveWeapon()
	 */
	@Override
	public MuWeapon getActiveWeapon() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	/**
	 * get name from npc teplate 
	 */
	public String getName() {
		return _npcTemplate.getName();
	}

	@Override
	public void moveTo(int x, int y) {
		super.moveTo(x, y);
	}

	@Override
	public void updateKnownsLists() {
		super.updateKnownsLists();

	}
}
