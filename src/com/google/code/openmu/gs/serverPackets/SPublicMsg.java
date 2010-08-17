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
 * 
 * @author Miki i Linka
 */
public class SPublicMsg extends ServerBasePacket {
	private final String _who;
	private final String _what;

	public SPublicMsg(String _who, String _what) {
		this._who = _who;
		this._what = _what;
	}

	@Override
	public byte[] getContent() throws IOException, Throwable {
		mC1Header(0x00, _what.length() + 14);
		writeNick(_who);
		writeS(_what);
		writeC(0x00);
		return getBytes();
	}

	@Override
	public String getType() {
		return "00 public msg";
	}

	@Override
	public boolean testMe() {
		return true;
	}

}
