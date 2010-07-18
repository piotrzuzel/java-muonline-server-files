/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mina.codec.CS2CL;

import java.util.ArrayList;
import mina.codec.AbstractMuMessageData;

/**
 *
 * @author mikiones
 */
public class ServerListData implements AbstractMuMessageData{
    public class ServerEntry{
        public String name;
        public short port;
        public byte pos;
        public byte grup;
        public byte load;

        public ServerEntry(String name, short port, byte pos, byte grup, byte load) {
            this.name = name;
            this.port = port;
            this.pos = pos;
            this.grup = grup;
            this.load = load;
        }
    }

    public ArrayList<ServerEntry> servers= new ArrayList<ServerEntry>();

    public void addServer(String name, short port, byte pos, byte grup, byte load)
    {
        servers.add(new ServerEntry(name, port, pos, grup, load));
    }



    public byte getHeadType() {
        return C2HEADER;
    }

    public byte getMessageID() {
        return (byte)0xf4;
    }

    public short getMessageSize() {
        return (short) (6 + (servers.size() * 4));
    }

}
