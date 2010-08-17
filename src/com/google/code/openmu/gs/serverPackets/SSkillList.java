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

public class SSkillList extends ServerBasePacket {

	@Override
	public byte[] getContent() throws IOException {
		// 02 001101 010404

		final byte[] t = {
				// c1 08 f3 11 01 00 11 01
				// (byte) 0xc1 ,0x08 ,(byte) 0xf3 ,0x11 ,0x01 ,0x00 ,0x11 ,0x01
				(byte) 0xc1, (byte) 0x0b, (byte) 0xf3, (byte) 0x11, // header
				(byte) 0x02, // count skills
				(byte) 0x00, (byte) 0x11, (byte) 0x01, // skill
				(byte) 0x01, (byte) 0x04, (byte) 0x04 };
		// mC1Header(0xf3, 0x11, 8);
		// writeC(1);
		// writeC(0x00);writeC(0x11);writeC(0x01);
		System.out.println(t.length);
		return t;
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean testMe() {
		// TODO Auto-generated method stub
		return false;
	}
	/**
	 * @param args
	 */
}
