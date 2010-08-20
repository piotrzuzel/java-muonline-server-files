/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.openmu.gs.serverPackets;

import java.io.IOException;

/**
 * Protocol:GetExpFromId c3 16
 * 
 * @author Miki i Linka
 */
public class SGoneExp extends ServerBasePacket {

	private final short _id;
	private final int _exp;

	/**
	 * send packet iGetExp :
	 * 
	 * @param IdOfKilledMonster
	 *            which we gt exp reward
	 * @param ExpReward
	 *            how exp we get
	 */
	public SGoneExp(int IdOfKilledMonster, int ExpReward) {
		_id = (short) IdOfKilledMonster;
		_exp = ExpReward;
	}

	@Override
	public byte[] getContent() throws IOException, Throwable {
		// 03 87
		// c6 f0 00
		mC3Header(0x16, 0x09); // header
		writeC(0x00); // 1
		writeC(_id); // 1
		writeL(_exp);// 4 b
		// writeC(0x00);//3th bait with exp count
		// writeC(0x00);
		// writeC(_exp);
		// writeC(0x0A);

		return getBytes();
	}

	@Override
	public String getType() {
		return "";
	}

	@Override
	public boolean testMe() {
		return true;
	}
}
