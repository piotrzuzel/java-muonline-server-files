/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.jmuserver.gs.serverPackage;

import java.io.IOException;
import java.util.ArrayList;
import net.sf.jmuserver.gs.muObjects.MuObject;
import net.sf.jmuserver.gs.muObjects.MuPcInstance;

/**
 *
 * @author Miki i Linka
 */
public class SPlayersMeeting extends ServerBasePacket{
    private class SPlayerSubMiting extends ServerBasePacket{
        private MuPcInstance _pc;
        public SPlayerSubMiting(MuPcInstance p) {
            _pc=p;
        }

        public byte[] getContent() throws IOException, Throwable {
            writeI(_pc.getObjectId());//2
            writeC(_pc.getX());//3
            writeC(_pc.getY());//4
            writeC(01);
            _bao.write(_pc.GetWearLook().getBytes()); // look ofcharacter
           // writeS(text, from, ile);
            return _bao.toByteArray();
        }

        public String getType() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public boolean testMe() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
    }
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
