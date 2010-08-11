/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.google.code.openmu.gs.muObjects;

/**
 * Inherited from MuInventory, it represents the player account's vault.
 * 
 * @see MuInventory
 * @see MuTradeInventory
 * @see MuVaultInventory
 * @author Marcel
 */
public class MuVaultInventory extends MuInventory {

	public MuVaultInventory() {
		_invXSize = MuInventory.VaultWindowXSize;
		_invYSize = MuInventory.VaultWindowYSize;
		_offset = MuInventory.OffsetOtherWindows;
	}

}
