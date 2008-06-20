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
        public static final int SubSize=35;
        public SPlayerSubMiting(MuPcInstance p) {
            _pc=p;
        }

        public byte[] getContent() throws IOException, Throwable {
            //17  EF  90  7C  60  A8  FF  F1  11  1F  12  48  03  84  00  00  00  00  00  00  5B  4D  47  5D  4A  45  54  00  00  00  91  79  13  00
            //ob  id| xp | y|clas|                                               un | au|  me|            name                       |x2   y2 lok               
            writeI(_pc.getObjectId());//2
            writeC(_pc.getX());//3
            writeC(_pc.getY());//4
            writeC(_pc.getClas());
            writeB(_pc.GetWearLook().getBytes()); // look ofcharacter
            writeC(0x00); //unknown
            writeC(0x00); //aura
            writeC(0x00);//magic  effect
            writeNick(_pc.getName());
            writeC(_pc.getNewX());
            writeC(_pc.getNewY());
            writeC(_pc.getStatus());
            writeC(0x00); // how show?
            return getBytes();
        }

        public String getType() {
            return "playersmeting sub";
        }

        public boolean testMe() {
            return true;
        }
        
    }
private ArrayList<MuObject> _newPc;
    public SPlayersMeeting(ArrayList<MuObject> newPc) {
       _newPc=newPc;
    }
    
    public byte[] getContent() throws IOException, Throwable {
        int size = 5+(_newPc.size()*SPlayerSubMiting.SubSize);
        mC2Header(0x27,  size);
        SPlayerSubMiting t;
        for (int i = 0; i < _newPc.size(); i++) {
            MuPcInstance muObject = (MuPcInstance) _newPc.get(i);
            t= new SPlayerSubMiting(muObject);
            writeB(t.getContent());
            
        }
        
        return getBytes()
        
        ;
    }

    public String getType() {
        return "pc Miting";
    }

    public boolean testMe() {
        return true;
    }

}
