/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.jmuserver.gs.muObjects.Abstract;

import net.sf.jmuserver.gs.muObiects.MuMonsterInstance;

/**
 *
 * @author Miki i Linka
 */
public abstract class AbstractAI {

    private MuMonsterInstance _instance;

    public AbstractAI(MuMonsterInstance _instance) {
        this._instance = _instance;
    }

    public MuMonsterInstance getMonster() {
        return _instance;
    }
    private int status = ST_IDE;
    public static final int ST_IDE = 0;
    public static final int ST_AutoMove = 1;
    public static final int ST_TargetSeek = 2;
    public static final int ST_AtackTarget = 3;

    abstract void StatusUpdate(int status);
}

