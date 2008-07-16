/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.jmuserver.gs.serverPackage;

import java.io.IOException;
import net.sf.jmuserver.gs.muObjects.MuGate;

/**
 *
 * @author Miki i Linka
 */
public class SGateEnterAnsfer extends ServerBasePacket{
MuGate _to;

    public SGateEnterAnsfer(MuGate to) {
        _to=to;
    }

    
    
    public byte[] getContent() throws IOException, Throwable {
        mC3Header(0x1c, 0x07);
        writeC(0x82); //??  what is it ?
        writeC(_to.getMap());
        writeC(_to.getX1());
        writeC(_to.getY1());
        
       //            ??       world      x          y     
    byte[]gateans={(byte)0xc3 ,(byte)0x07 ,(byte)0x1c ,(byte)0x82 ,(byte)0x00 ,(byte)168 ,(byte)172};   
    return getBytes();
    }

    public String getType() {
        return "enter ingate ansfer";
    }

    public boolean testMe() {
        return true;
    }

}
