/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.openmu.gs.clientPackage;

import com.google.code.openmu.gs.ClientThread;

/**
 * 
 * @author Miki
 */
public class CItemUseRequest extends ClientBasePacket {

	private final int _slot; // sot w oknie
	private final int _wid; // id okna

	public CItemUseRequest(byte[] data, ClientThread _client) {
		super(data);
		_slot = data[1] & 0xff;
		_wid = data[2] & 0xff;
		System.out.println("Request use item fro window [" + _wid
				+ "] on slot[" + _slot + "]");

	}

	@Override
	public String getType() {
		return "";
	}
}
