/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.jmuserver.gs.serverPackage;

import java.io.IOException;

/**
 *
 * @author Miki i Linka
 */
public class SPublicMsg extends ServerBasePacket {
private String _who;
private String _what;

    public SPublicMsg(String _who, String _what) {
        this._who = _who;
        this._what = _what;
    }

    public byte[] getContent() throws IOException, Throwable {
    mC1Header(0x00, _what.length()+14);
    writeNick(_who);
    writeS(_what);
    writeC(0x00);
        return getBytes();
    }

    public String getType() {
        return "00 public msg";
    }

    public boolean testMe() {
       return true;
    }

}
