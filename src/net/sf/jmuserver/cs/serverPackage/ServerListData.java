/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.jmuserver.cs.serverPackage;

import java.util.ArrayList;
import mina.codec.AbstractMuMessageData;

/**
 * 
 * @author mikiones
 */
public class ServerListData implements AbstractMuMessageData {

	public ServerListData(ArrayList<ServerEntry> arr) {
		servers = arr;
	}

	public ArrayList<ServerEntry> servers = new ArrayList<ServerEntry>();

	public void addServer(String name,String host, short port, byte pos, byte grup,
			byte load) {
		servers.add(new ServerEntry(name,host, port, pos, grup, load));
	}

	public ServerAdressData getServer(byte pos, byte grup) {
		for (ServerEntry e : servers) {
			if ((e.grup == grup) && (e.pos == pos))
				return new ServerAdressData(e.host, (short)e.port);
		}
		return null;
	}

	public byte getHeadType() {
		return C2HEADER;
	}

	public byte getMessageID() {
		return (byte) 0xf4;
	}

	public short getMessageSize() {
		return (short) (6 + (servers.size() * 4));
	}

}
