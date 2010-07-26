/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.jmuserver.cs.serverPackage;

import mina.codec.AbstractMuMessageData;

/**
 *
 * @author mikiones
 */
public class HelloClientData implements AbstractMuMessageData {

    public byte getHeadType() {
        return C1HEADER;
    }

    public byte getMessageID() {
        return 0x00;
    }

    public short getMessageSize() {
        return 0x04;
    }

}
