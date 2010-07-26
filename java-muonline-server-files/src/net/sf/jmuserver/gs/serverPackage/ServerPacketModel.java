/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.jmuserver.gs.serverPackage;

import java.io.IOException;

/**
 *
 * @author Miki
 */
public interface ServerPacketModel {

    /**
     *
     * @return zwraca zarartosc w byte[]
     */
    byte[] getBytes();

    byte[] getContent() throws IOException,Throwable;

    int getLength();

    /**
     * just for information and debug purposes
     *
     * @return
     */
    String getType();

    

    boolean testMe();

}
