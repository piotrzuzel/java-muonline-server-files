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
import java.util.Collection;

import com.google.code.openmu.gs.muObjects.MuCharacterInventory;
import com.google.code.openmu.gs.muObjects.MuStoreableItem;

/**
 * 
 * @author Miki
 */
public class SInwentoryList extends ServerBasePacket {

	public final int ItemSize = 5;
	MuCharacterInventory _inw;

	public SInwentoryList(MuCharacterInventory inw) {
		super();
		_inw = inw;

	}

	private void makeHead(int ItemCout) {
		final int size = 6 + (ItemCout * (ItemSize + 1));
		mC4Header(0xf3, 0x10, size);
		writeC(ItemCout);
	}

	private void makeSub(MuStoreableItem item) {
		final int pos = item.getPosition();
		writeC(pos); // 1
		writeB(item.toByteArray()); // 5
		// size 6
	}

	@Override
	public byte[] getContent() throws IOException, Throwable {
		// byte t[] = {(byte) 0xc4, (byte) 0x00, (byte) 12, (byte) 0xf3, (byte)
		// 0x10, (byte) 0x01, (byte) 0x0f, (byte) 0x20, (byte) 0x00, (byte)
		// 0x12, (byte) 0x00, (byte) 0x00};
		final Collection<MuStoreableItem> inw = _inw.getItems();
		makeHead(_inw.getCoutofItems());
		for (final MuStoreableItem muStoreableItem : inw) {
			makeSub(muStoreableItem);
		}
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
