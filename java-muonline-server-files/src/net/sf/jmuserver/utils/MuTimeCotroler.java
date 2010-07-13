/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.jmuserver.utils;

/**
 *
 * @author Miki i Linka
 */
public class MuTimeCotroler {

    private long _serverStartTime;
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
        long time = (System.currentTimeMillis() - _serverStartTime) / 1000;
        return (int) time;
    }
}
