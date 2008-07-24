/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.jmuserver.gs.muObjects;

/**
 *
 * @author Marcel
 */
public class MuVaultInventory extends MuInventory {
    
    public MuVaultInventory() {
        _invXSize = MuInventory.VaultWindowXSize;
        _invYSize = MuInventory.VaultWindowYSize;
        _offset = MuInventory.OffsetOtherWindows;
    }

}
