package net.sf.jmuserver.gs.muObiects;

import net.sf.jmuserver.gs.templates.MuItemHex;

/**
 * Class representing any type of item placed in inventories. It is
 * complementary to the MuItemOnGround class, and both include a reference
 * to the item type (stats) and an own set of hex values, given by the
 * MuItemHex class.
 * @see MuItemStats
 * @see MuItemHex
 * @see MuItemOnGround
 * @author Marcel
 */
public class MuStoreableItem  {

    private int _windowId;
    private int _position;
    MuItemHex _itemHex;
    MuItemStats _itemStats;

    /**
     * constructor 
     * @param windowId 
     * @param position
     * @param hex
     * @param stats
     */
    public MuStoreableItem(int windowId, int position, MuItemHex hex, MuItemStats stats) {
        setWindowId(windowId);
        setPosition(position);
        setItemHex(hex);
        setItemStats(stats);
    }

    /**
     * constructor
     * ItemStats is geted form ItemHex;
     * @param windowId
     * @param position
     * @param hex
     */
    public MuStoreableItem(int windowId,int position,MuItemHex hex) {        
        setWindowId(windowId);
        setPosition(position);
        setItemHex(hex);
        setItemStats(MuItemStats.getItemStats(hex));
        
    }

    
    @Override
    public String toString() {
        return "["+_windowId+"]"+"["+_position+"]"+"["+
                _itemStats.get_itemName()+"]"+_itemHex;
    }
    
    public void setItemStats(MuItemStats _itemStats) {
        this._itemStats = _itemStats;
    }
    
    public void setItemHex(MuItemHex _itemHex) {
        this._itemHex = _itemHex;
    }

    public void setPosition(int _position) {
        this._position = _position;
    }

    public void setWindowId(int _windowId) {
        if (_windowId != MuInventory.InventoryWindow && 
                _windowId != MuInventory.TradeWindow && 
                _windowId != MuInventory.VaultWindow)
            _windowId = MuInventory.InventoryWindow;            
        this._windowId = _windowId;
    }

    public MuItemStats getItemStats() {
        return _itemStats;
    }
    
    public MuItemHex getItemHex() {
        return _itemHex;
    }

    public int getPosition() {
        return _position;
    }

    public int getWindowId() {
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
