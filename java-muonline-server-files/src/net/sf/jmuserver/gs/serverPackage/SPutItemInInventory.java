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
    
    private byte[] _itemHex;
    private int _pos;
    
    public SPutItemInInventory(MuInventoryItem Item) {  
        _itemHex = Item.getItemHex().toByteArray();
        _pos = Item.getPosition();
    }
    
    public SPutItemInInventory(byte[] Item, byte Position) {
        _itemHex = Item;
        _pos = Position;
    }
    
    public byte[] getContent() {
        mC3Header(0x22, 0x09);
        writeC(_pos);
        writeB(_itemHex);
        return getBytes();
    }

    public String getType() {
        return "Request to place item in character inventory.";
    }

    public boolean testMe() {
        return true;
    }

}
