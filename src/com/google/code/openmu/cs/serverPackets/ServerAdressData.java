/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.google.code.openmu.cs.serverPackets;

import com.google.code.openmu.mina.codecs.AbstractMuMessageData;

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
    public ServerAdressData(ServerEntry e) {
	adres = e.host;
	port = (short) e.port;
	}

    public final byte getHeadType() {
       return C1HEADER;
    }

    public final byte getMessageID() {
        return (byte)0xf4;
    }

    public final short getMessageSize() {
        return (short) 0x16;
    }

}
