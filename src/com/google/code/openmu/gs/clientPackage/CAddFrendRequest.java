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

import com.google.code.openmu.gs.ClientThread;

/**
 * 
 * @author Miki i Linka
 */
public class CAddFrendRequest extends ClientBasePacket {

	public CAddFrendRequest(byte[] _decrypt, ClientThread _client) {
		super(_decrypt);
		System.out.println("|-Add Friend Request:");
		String _name = readS(2, 10);
		_name = _name.trim();
		System.out.println("_name = " + _name);
	}

	@Override
	public String getType() {
		return "";
	}

}
