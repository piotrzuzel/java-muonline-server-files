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

package com.google.code.openmu.gs.muObjects;

import com.google.code.openmu.gs.templates.MuItemHex;

public class MuItemOnGround extends MuObject {

	private MuItemHex _itemHex;
	private MuItemStats _itemStats;

	public MuItemOnGround() {
		// sheld :P
		_itemHex = new MuItemHex();
		_itemHex.fromByteArray(new byte[] { (byte) 0xc0, (byte) 0x00,
				(byte) 0x16, (byte) 0x01, (byte) 0x01 }, 0);
		_itemStats = MuItemStats.getItemStats((byte) 0xC0, (byte) 0x00);
	}

	// @Override
	// public void ISpown() {
	// super.ISpown();
	// System.out.println("ISpown in ItemOnGround");
	// }

	public MuItemStats getItemStats() {
		return _itemStats;
	}

	public MuItemHex getItemHex() {
		return _itemHex;
	}

	public void setItemStats(MuItemStats ItemStats) {
		_itemStats = ItemStats;
	}

	public void setItemHex(MuItemHex ItemHex) {
		_itemHex = ItemHex;
	}

}
