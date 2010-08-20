/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.openmu.utils;

/**
 * 
 * @author Miki i Linka
 */
public class MuTimeCotroler {

	private final long _serverStartTime;
	private static MuTimeCotroler _instance = null;

	public static MuTimeCotroler getInstance() {
		if (_instance == null) {
			_instance = new MuTimeCotroler();
		}
		return _instance;
	}

	private MuTimeCotroler() {
		_serverStartTime = System.currentTimeMillis();
	}

	/**
	 * 
	 * @return the time
	 */
	public int getGameTime() {
		final long time = (System.currentTimeMillis() - _serverStartTime) / 1000;
		return (int) time;
	}
}
