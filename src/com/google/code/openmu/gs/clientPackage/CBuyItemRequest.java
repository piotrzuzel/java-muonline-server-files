/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.google.code.openmu.gs.clientPackage;

import com.google.code.openmu.gs.ClientThread;

/**
 * 
 * @author Miki i Linka
 */
public class CBuyItemRequest extends ClientBasePacket {

	public CBuyItemRequest(byte[] decrypt, ClientThread _client) {
		super(decrypt);
		System.out.println("Rquest to buy item fromslot:" + decrypt[1]);

	}

	@Override
	public String getType() {
		return "";
	}

}
