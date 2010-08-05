/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.jmuserver.gs.MuTask;

import net.sf.jmuserver.utils.MuTimeCotroler;

/**
 * The TimeTicket class
 * @author Miki i Linka
 * @version 
 */
public class MuTimeTicket {

    private int tick;

    /**
     * set ticket to s sec
     * @param s how long ticket is ok
     */
    public MuTimeTicket(int s) {
        tick = MuTimeCotroler.getInstance().getGameTime() + s;
    }

    /**
     * 
     * @return true when ticket expired
     */
    public boolean TicketEnd() {
        return tick < MuTimeCotroler.getInstance().getGameTime();
    }

    /**
     * 
     * @return time when ticket expired
     */
    public int getTime() {
        return tick;
    }

    /**
     * compare 2 tickets
     * @param a 1'st ticket
     * @param b 2'nd ticket
     * @return true when 1'st ticket expired  before secend
     */
    public boolean compareTicket(MuTimeTicket a, MuTimeTicket b) {
        return a.getTime() < b.getTime();
    }
}
