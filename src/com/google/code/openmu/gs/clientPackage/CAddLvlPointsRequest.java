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

import com.google.code.openmu.gs.ClientThread;
import com.google.code.openmu.gs.serverPackets.SAddLvlPointsAnsfer;

public class CAddLvlPointsRequest extends ClientBasePacket {

	byte _whichPointToAdd;

	public CAddLvlPointsRequest(byte[] decrypt, ClientThread client) {
		super(decrypt);
		_whichPointToAdd = decrypt[2];
		final boolean czy = client.getActiveChar().getLp() > 0;
		if (czy) {
			switch (_whichPointToAdd) {
			case 0x00: {
				System.out.println("Add 1 pont to str");

				client.getActiveChar().incStr();
				client.getActiveChar().decLP();
			}
				break;
			case 0x01: {
				System.out.println("Add 1 pont to agi");
				client.getActiveChar().incAgi();
				client.getActiveChar().decLP();
			}
				break;
			case 0x02: {
				System.out.println("Add 1 pont to vit");
				client.getActiveChar().incVit();
				client.getActiveChar().decLP();
			}
				break;
			case 0x03: {
				System.out.println("Add 1 pont to ene");
				client.getActiveChar().incEne();
				client.getActiveChar().decLP();
			}
				break;
			default:
				break;
			}
			// TODO dodac odczytywanie noewj many i staminy
			client.getActiveChar().sendPacket(
					new SAddLvlPointsAnsfer(_whichPointToAdd, true, 15, 15));

		} else {
			client.getActiveChar().sendPacket(
					new SAddLvlPointsAnsfer((byte) 0x00, false, 10, 10));
		}

	}

	@Override
	public String getType() {

		return null;
	}

}
