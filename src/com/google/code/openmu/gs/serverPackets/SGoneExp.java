/*
 * Copyright [mikiones] [Michal Kinasiewicz]
 * 			 [marcel]   [Marcel Gheorghita] 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
