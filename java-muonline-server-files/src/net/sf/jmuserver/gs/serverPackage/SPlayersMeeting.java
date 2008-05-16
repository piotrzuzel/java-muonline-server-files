/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.jmuserver.gs.serverPackage;

import java.io.IOException;
import java.util.ArrayList;
import net.sf.jmuserver.gs.muObjects.MuObject;

/**
 *
 * @author Miki i Linka
 */
public class SPlayersMeeting extends ServerBasePacket{
private ArrayList<MuObject> _newPc;
    public SPlayersMeeting(ArrayList<MuObject> newPc) {
       _newPc=newPc;
    }

    public byte[] getContent() throws IOException, Throwable {
        return null;
    }

    public String getType() {
        return "pc Miting";
    }

    public boolean testMe() {
        return true;
    }

}
