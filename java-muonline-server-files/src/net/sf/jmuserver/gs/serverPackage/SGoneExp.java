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
public class SGoneExp extends ServerBasePacket{

    public byte[] getContent() throws IOException, Throwable {
      byte [] b = {(byte)0xc3 ,0x08 ,0x16 ,(byte)0x9c ,(byte)0x82 ,(byte)0x8a ,(byte)0x46 ,(byte)0xaf ,(byte)0x28};
    return b;
    }

    public String getType() {
        return "";
    }

    public boolean testMe() {
        return true;
    }

}
