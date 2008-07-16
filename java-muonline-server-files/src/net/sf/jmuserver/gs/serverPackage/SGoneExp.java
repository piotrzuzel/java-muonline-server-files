/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.jmuserver.gs.serverPackage;

import java.io.IOException;

/**
 * Protocol:GetExpFromId c3 16
 * @author Miki i Linka
 */
public class SGoneExp extends ServerBasePacket {

    private short _id;
    private int _exp;

    /**
     * send  packet iGetExp :
     * @param IdOfKilledMonster which we gt exp reward
     * @param ExpReward how exp we get
     */
    public SGoneExp(int IdOfKilledMonster, int ExpReward) {
        _id = (short) IdOfKilledMonster;
        _exp = ExpReward;
    }

    public byte[] getContent() throws IOException, Throwable {
        mC3Header(0x16, 0x08); //header
        writeI(_id);
        writeC(0x00);//3th bait with exp count
        writeIDiverted(_exp);
        return getBytes();
    }

    public String getType() {
        return "";
    }

    public boolean testMe() {
        return true;
    }
}
