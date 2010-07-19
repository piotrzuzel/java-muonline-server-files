/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.jmuserver.gs.muObiects;

/**
 * Inherited from MuInventory, it represents the trade inventory.<br>
 * (NOTE) The idea is to create an instance of this class for each
 * trading session, that is available to both characters.
 * @see MuInventory
 * @see MuTradeInventory
 * @see MuVaultInventory
 * @author Marcel
 */
public class MuTradeInventory extends MuInventory {

    public MuPcInstance Trader1 = null;
    public MuPcInstance Trader2 = null;
    
    public MuTradeInventory() {
        _invXSize = MuInventory.TradeWindowXSize;
        _invYSize = MuInventory.TradeWindowYSize;
        _offset = MuInventory.OffsetOtherWindows;
    }
    
}
