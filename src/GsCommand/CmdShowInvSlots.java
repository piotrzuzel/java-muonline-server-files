/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GsCommand;

import net.sf.jmuserver.gs.muObjects.MuInventory;

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
