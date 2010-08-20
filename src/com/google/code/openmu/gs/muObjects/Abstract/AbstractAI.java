/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.openmu.gs.muObjects.Abstract;

import com.google.code.openmu.gs.muObjects.MuMonsterInstance;

/**
 * 
 * @author Miki i Linka
 */
public abstract class AbstractAI {

	private final MuMonsterInstance _instance;

	public AbstractAI(MuMonsterInstance _instance) {
		this._instance = _instance;
	}

	public MuMonsterInstance getMonster() {
		return _instance;
	}

	public static final int ST_IDE = 0;
	public static final int ST_AutoMove = 1;
	public static final int ST_TargetSeek = 2;
	public static final int ST_AtackTarget = 3;

	abstract void StatusUpdate(int status);
}
