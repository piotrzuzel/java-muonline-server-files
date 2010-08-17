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

package com.google.code.openmu.gs.clientPackage;

import java.io.IOException;

import com.google.code.openmu.gs.ClientThread;
import com.google.code.openmu.gs.muObjects.MuPcInstance;


public class CSelectedCharacterEnterRequest extends ClientBasePacket {

	public CSelectedCharacterEnterRequest(byte[] decrypt, ClientThread c)
			throws IOException {
		super(decrypt);
		final String selected = SelectedName();
		final MuPcInstance cha = c.loadCharFromDisk(selected);

		System.out.println("Selected Character: " + selected);
		c.setActiveChar(cha);

	}

	public String SelectedName() {
		return readS(2, 10);
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

}
