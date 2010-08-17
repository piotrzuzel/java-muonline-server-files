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

package com.google.code.openmu.gs.commands;

import com.google.code.openmu.gs.muObjects.MuInventory;

/**
 * Debugging command. Used to retrieve the internal server representation of the
 * character inventory, in the form of a binary matrix. Value "1" means the slot
 * is occupied, "0" means the slot is free.
 * 
 * @author Marcel
 */
public class CmdShowInvSlots extends GsBaseCommand {

	@Override
	public boolean RunCommand() {
		final MuInventory inv = _cli.getActiveChar().getInventory();
		for (byte i = 0; i < 8; i++) {
			for (byte j = 0; j < 8; j++) {
				if (inv._slots[i][j]) {
					System.out.print("1 ");
				} else {
					System.out.print("0 ");
				}
			}
			System.out.println();
		}
		return true;
	}

	@Override
	public String getCmdString() {
		return "ShowInvSlots";
	}

	@Override
	public String getHelpToCommand() {
		return "Shows the binary matrix of character inventory.";
	}

	@Override
	public String getShortDesc() {
		return "ShowInventory";
	}

}
