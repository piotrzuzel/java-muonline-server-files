/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.jmuserver.gs.muObjects;

/**
 *
 * @author Marcel
 */
public class MuTradeInventory extends MuInventory {

    public MuTradeInventory() {
        _invXSize = MuInventory.TradeWindowXSize;
        _invYSize = MuInventory.TradeWindowYSize;
        _offset = MuInventory.OffsetOtherWindows;
    }
    
}
