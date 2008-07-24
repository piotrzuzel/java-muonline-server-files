/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.jmuserver.gs.serverPackage;

import net.sf.jmuserver.gs.muObjects.MuInventoryItem;
import net.sf.jmuserver.gs.templates.MuItemHex;

/**
 *
 * @author Marcel
 */
public class SPutItemInInventory extends ServerBasePacket {
    
    private MuItemHex _itemHex;
    private boolean _result;
    
    public SPutItemInInventory(MuInventoryItem Item, boolean result) {    
        _itemHex = Item.getItemHex();
        _result = result;
    }
    
    public byte[] getContent() {
        mC3Header(0x22, 0x09);
        if (_result)
            writeC(0x01);
        else
            writeC(0x00);
        writeB(_itemHex.toByteArray());
        return getBytes();
    }

    public String getType() {
        return "Request to place item in character inventory.";
    }

    public boolean testMe() {
        return true;
    }

}
