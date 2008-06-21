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
public class SPlayersMeeting extends ServerBasePacket {

    private class SPlayerSubMiting extends ServerBasePacket {

        private MuPcInstance player;
        public static final int SubSize = 35;

        public SPlayerSubMiting(MuPcInstance p) {
            player = p;
        }

        public byte[] getContent() throws IOException, Throwable {
            writeI(player.getObjectId());//2
            writeC(player.getX());//3
            writeC(player.getY());//4
            writeC(player.getClas());
            writeB(player.GetWearLook().getBytes()); // look ofcharacter
            writeC(0x00); //unknown
            writeC(player.getAura().toByte()); //aura
            writeC(0x00);//magic  effect
            writeNick(player.getName());
            writeC(player.getX());
            writeC(player.getY());
            writeC(player.getStatus());
            writeC(0x00); // how spown? 
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
        _newPc = newPc;
    }

    public byte[] getContent() throws IOException, Throwable {
        int size = 4 + (_newPc.size() * SPlayerSubMiting.SubSize);
        mC2Header(0x12, size);
        writeC(_newPc.size());
        SPlayerSubMiting t;
        for (int i = 0; i < _newPc.size(); i++) {
            MuPcInstance muObject = (MuPcInstance) _newPc.get(i);
            t = new SPlayerSubMiting(muObject);
            writeB(t.getContent());
        }
        return getBytes();
    }

    public String getType() {
        return "pc Miting";
    }

    public boolean testMe() {
        return true;
    }
}
