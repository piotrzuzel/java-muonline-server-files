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
 * template for all npc's
 * 
 * @author Mikione
 */
public class MuNpc {

	private int _npcId;
	private int rate;
	private String _name;
	private int _lvl;
	private int _maxHp;
	private int _maxMP;
	private int _minDMG;
	private int _maxDMG;
	private int _def;
	private int _mDef;
	private int _attakRate;
	private int _succesfulbloking;
	private int _moveRange;
	private int _attackType;
	private int _attackRange;
	private int _viewRange;
	private int _moveSpeed;
	private int _attackSpeed;
	private int _regenTime;
	private int _itemRate;
	private int _monyRate;
	private int _maxItemLvl;
	private int _skill;

	public int getAttackRange() {
		return _attackRange;
	}

	public void setAttackRange(int attackRange) {
		_attackRange = attackRange;
	}

	public int getAttackSpeed() {
		return _attackSpeed;
	}

	public void setAttackSpeed(int attackSpeed) {
		_attackSpeed = attackSpeed;
	}

	public int getAttackType() {
		return _attackType;
	}

	public void setAttackType(int attackType) {
		_attackType = attackType;
	}

	public int getAttakRate() {
		return _attakRate;
	}

	public void setAttakRate(int attakRate) {
		_attakRate = attakRate;
	}

	public int getDef() {
		return _def;
	}

	public void setDef(int def) {
		_def = def;
	}

	public int getItemRate() {
		return _itemRate;
	}

	public void setItemRate(int itemRate) {
		_itemRate = itemRate;
	}

	public int getLvl() {
		return _lvl;
	}

	public void setLvl(int lvl) {
		_lvl = lvl;
	}

	public int getMaxDMG() {
		return _maxDMG;
	}

	public void setMaxDMG(int maxDMG) {
		_maxDMG = maxDMG;
	}

	public int getMaxHp() {
		return _maxHp;
	}

	public void setMaxHp(int maxHp) {
		_maxHp = maxHp;
	}

	public int getMaxItemLvl() {
		return _maxItemLvl;
	}

	public void setMaxItemLvl(int maxItemLvl) {
		_maxItemLvl = maxItemLvl;
	}

	public int getMaxMP() {
		return _maxMP;
	}

	public void setMaxMP(int maxMP) {
		_maxMP = maxMP;
	}

	public int getMDef() {
		return _mDef;
	}

	public void setMDef(int def) {
		_mDef = def;
	}

	public int getMinDMG() {
		return _minDMG;
	}

	public void setMinDMG(int minDMG) {
		_minDMG = minDMG;
	}

	public int getMonyRate() {
		return _monyRate;
	}

	public void setMonyRate(int monyRate) {
		_monyRate = monyRate;
	}

	public int getMoveRange() {
		return _moveRange;
	}

	public void setMoveRange(int moveRange) {
		_moveRange = moveRange;
	}

	public int getMoveSpeed() {
		return _moveSpeed;
	}

	public void setMoveSpeed(int moveSpeed) {
		_moveSpeed = moveSpeed;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public int getNpcId() {
		return _npcId;
	}

	public void setNpcId(int npcId) {
		_npcId = npcId;
	}

	public int getRegenTime() {
		return _regenTime;
	}

	public void setRegenTime(int regenTime) {
		_regenTime = regenTime;
	}

	public int getSkill() {
		return _skill;
	}

	public void setSkill(int skill) {
		_skill = skill;
	}

	public int getSuccesfulbloking() {
		return _succesfulbloking;
	}

	public void setSuccesfulbloking(int succesfulbloking) {
		_succesfulbloking = succesfulbloking;
	}

	public int getViewRange() {
		return _viewRange;
	}

	public void setViewRange(int viewRange) {
		_viewRange = viewRange;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}
}
