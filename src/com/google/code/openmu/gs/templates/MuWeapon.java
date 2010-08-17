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

package com.google.code.openmu.gs.templates;

/**
 * 
 * @author mikiones
 *
 */
 
  public class MuWeapon {
 
	public static final int _W_TYPE_unk = 0x00;
	public static final int _W_TYPE_SWORD = 0x01;

	public static final int _W_1HAND = 0;
	public static final int _W_2HAND = 1;

	private int _waponType;
	private int _2hand;

	private int _minDmg;
	private int _maxDmg;
	private int _attackSpeed;

	/**
	 * @return zwraca szybkosc ataku danej broni
	 */
	public int getAttackSpeed() {
		return _attackSpeed;
	}

	/**
	 * ustawia szybkosc ataku
	 * 
	 * @param attackSpeed
	 *            nowa wartosc
	 */
	public void setAttackSpeed(int attackSpeed) {
		_attackSpeed = attackSpeed;
	}

	/**
	 * @return maxymalny dmg
	 */
	public int getMaxDmg() {
		return _maxDmg;
	}

	/**
	 * ustwaia max dmg
	 * 
	 * @param maxDmg
	 *            wartosc
	 */
	public void setMaxDmg(int maxDmg) {
		_maxDmg = maxDmg;
	}

	/**
	 * @return zwraca min dmg broni
	 */
	public int getMinDmg() {
		return _minDmg;
	}

	public void setMinDmg(int minDmg) {
		_minDmg = minDmg;
	}

	public int get2hand() {
		return _2hand;
	}

	public void set2hand(int name) {
		_2hand = name;
	}

	public int getWaponType() {
		return _waponType;
	}

	public void setWaponType(int waponType) {
		_waponType = waponType;
	}

}
