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

public class SLoginAuthAnsfer extends ServerBasePacket {

	private byte f = 0; // flafa

	public static final int PA_InvaltPassword = 0x00;
	public static final int PA_PassOK = 0x01;
	public static final int PA_AccInvalt = 0x02;
	public static final int PA_AccAlredyConnected = 0x03;
	public static final int PA_ServerIsFull = 0x04;
	public static final int PA_AccBlacked = 0x05;
	public static final int PA_NewVersionOfGamereq = 0x06;
	public static final int PA_NoChargeInfo = 0x09;

	public SLoginAuthAnsfer(byte fl) {
		super();
		f = fl;
		// TODO Auto-generated constructor stub
	}

	@Override
	public byte[] getContent() throws IOException {
		mC1Header(0xf1, 0x01, 0x05);
		_bao.write(f);
		_bao.write(0x00);
		System.out.println(f);
		return _bao.toByteArray();
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "SF101 Veryvication pass&user ansfer";
	}

	@Override
	public boolean testMe() {
		// TODO Auto-generated method stub
		return false;
	}

}
