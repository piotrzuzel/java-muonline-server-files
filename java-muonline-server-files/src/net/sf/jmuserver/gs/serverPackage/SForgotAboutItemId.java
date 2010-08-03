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
public class SForgotAboutItemId extends ServerBasePacket{

    public byte[] getContent() throws IOException, Throwable {
        byte [] b ={(byte)0xc2 ,0x00 ,0x07 ,0x21 ,0x01 ,0x00 ,0x00};
        return b;
    }

    public String getType() {
       return "";
    }

    public boolean testMe() {
        return true;
    }

}
