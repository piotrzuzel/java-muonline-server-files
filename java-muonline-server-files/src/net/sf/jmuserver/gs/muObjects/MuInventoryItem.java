package net.sf.jmuserver.gs.muObjects;

import net.sf.jmuserver.gs.templates.MuItemHex;

/**
 * Class representing any type of item placed in inventories.
 * @author Marcel
 */
public class MuInventoryItem  {

    private byte _windowId;
    private byte _position;
    MuItemHex _itemHex;
    MuItem _itemStats;

    public MuInventoryItem(byte windowId, byte position, MuItemHex hex, MuItem stats) {
        setWindowId(windowId);
        setPosition(position);
        setItemHex(hex);
        setItemStats(stats);
    }

    public void setItemStats(MuItem _itemStats) {
        this._itemStats = _itemStats;
    }
    
    public void setItemHex(MuItemHex _itemHex) {
        this._itemHex = _itemHex;
    }

    public void setPosition(byte _position) {
        this._position = _position;
    }

    public void setWindowId(byte _windowId) {
        if (_windowId != MuInventory.InventoryWindow && 
                _windowId != MuInventory.TradeWindow && 
                _windowId != MuInventory.VaultWindow)
            _windowId = MuInventory.InventoryWindow;            
        this._windowId = _windowId;
    }

    public MuItem getItemStats() {
        return _itemStats;
    }
    
    public MuItemHex getItemHex() {
        return _itemHex;
    }

    public byte getPosition() {
        return _position;
    }

    public byte getWindowId() {
        return _windowId;
    }

    public void moveTo(byte newWindowId, byte newPosition) {
	setWindowId(newWindowId);
	setPosition(newPosition);
    }

    public byte[] toByteArray() {
	return _itemHex.toByteArray();
    }
    
}
