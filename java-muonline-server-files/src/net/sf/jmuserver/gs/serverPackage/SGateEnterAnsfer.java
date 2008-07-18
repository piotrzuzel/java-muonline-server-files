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
    byte[]gateans={
        (byte)0xc3 ,(byte)0x08 ,
        (byte)0x1c ,(byte)0x19 ,
        (byte)0x00 ,(byte)0xd6 ,
        (byte)0xf4 ,(byte)0x01
//        (byte)0xc3 ,(byte)0x07 ,
//        (byte)0x1c ,(byte)0x82 ,
//        (byte)0xa8 ,(byte)0x28 ,
//        (byte)0x03// ,(byte)0x73
    };
    
        
        //(byte)0xc3 ,(byte)0x07 ,(byte)0x1c ,(byte)0x82 ,(byte)0x00 ,(byte)168 ,(byte)172};   
    return gateans;
    }

    public String getType() {
        return "enter ingate ansfer";
    }

    public boolean testMe() {
        return true;
    }

}
