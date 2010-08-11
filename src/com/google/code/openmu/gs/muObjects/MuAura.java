/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.google.code.openmu.gs.muObjects;

/**
 * 
 * @author Miki i Linka
 * @ToDO write all staff to using, auras
 */
public class MuAura {
	public static final byte NoAura = 0x00;

	/**
	 * 
	 * @return the modificator to HP
	 */
	public int getHpMod() {
		return 0;
	}

	/**
	 * 
	 * @return the Dmg modyficator
	 */
	public int getDmgMod() {
		return 0;
	}

	/**
	 * 
	 * @return modyficator to Defence
	 */
	public int getDefMod() {
		return 0;
	}

	/**
	 * 
	 * @return return the byte confersion of auras
	 */
	public byte toByte() {
		return 0x00;
	}

}
