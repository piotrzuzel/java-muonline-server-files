/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.google.code.openmu.fs.clientPackets;

import com.google.code.openmu.fs.FriendTheard;

import com.google.code.openmu.gs.clientPackage.ClientBasePacket;

/**
 * 
 * @author mikiones
 */
public class ServerListRequest extends ClientBasePacket {

	public ServerListRequest(byte[] data, FriendTheard _friend) {
		super(data);
		System.out.println("CS:> Request server list");
	}

	@Override
	public String getType() {
		return "";
	}

}
