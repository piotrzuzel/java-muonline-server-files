/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mina.codec.CS2CL;

import mina.codec.AbstractMuMessageData;

/**
 *
 * @author mikiones
 */
public class ServerAdressData implements AbstractMuMessageData {

    String adres;
    short port;

    ServerAdressData(String name, short port) {
        adres=name;
        this.port = port;
    }

    public final byte getHeadType() {
       return C1HEADER;
    }

    public final byte getMessageID() {
        return (byte)0xf4;
    }

    public final short getMessageSize() {
        return (short) (adres.length() + 6);
    }

}
