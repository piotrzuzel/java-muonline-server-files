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

/**
 * 
 * @author Marcel
 */
public class SDeleteChar extends ServerBasePacket {
	private final int _result;

	public SDeleteChar(String name, int result) {
		super();
		_result = result;
	}

	@Override
	public byte[] getContent() {
		mC1Header(0xf3, 0x02, 0x05);
		// 0x00 - you can not delete your character since the guild can not be
		// removed
		// 0x01 - delete ok
		// 0x02 - invalid personal id number
		// 0x03 - the character is item blocked
		writeC(_result);
		return getBytes();
	}

	@Override
	public String getType() {
		return null;
	}

	@Override
	public boolean testMe() {
		return false;
	}
}
