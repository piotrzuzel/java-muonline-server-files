package net.sf.jmuserver.gs.muObjects;

import java.util.HashMap;
import java.util.Map;

public class MuInventory {

    public static byte TradeWindow = (byte)0x80;
    public static byte TradeWindowXSize = (byte)0x08;
    public static byte TradeWindowYSize = (byte)0x04;
    public static byte InventoryWindow = (byte)0x00;
    public static byte InventoryWindowSize = (byte)0x08;
    public static byte VaultWindow = (byte)0x02;
    public static byte VaultWindowXSize = (byte)0x08;
    public static byte VaultWindowYSize = (byte)0x10;    
    public static byte OffsetInventoryWindow = (byte)0x0C;
    public static byte OffsetOtherWindows = (byte) 0x00;
    
    protected byte _offset;
    protected byte _invXSize;
    protected byte _invYSize;
    protected Map _inventory = new HashMap<Integer, MuInventoryItem>();
    protected boolean[][] _slots;

    public MuInventory() {
        _invXSize = MuInventory.InventoryWindowSize;
        _invYSize = MuInventory.InventoryWindowSize;
        _offset = MuInventory.OffsetInventoryWindow;
    }
    
    /**
     * Attempts to store item in the first available position in inventory.<br>
     * If successful, the new position is stored in item too.
     * @param Item
     * @return boolean
     */ 
    public boolean storeItem(MuInventoryItem Item) {
        byte Position = getAvailablePosition(
                Item.getItemStats().get_xSize(),
                Item.getItemStats().get_ySize());        
        int line = getLine(Position);
        int column = getColumn(Position);
        Position += _offset;
        if (Position >= 0) {
            Item.setPosition(Position);
            markSlots(line, column, Item.getItemStats().get_ySize(),
                    Item.getItemStats().get_ySize(), true);
            _inventory.put(Position, Item);
            return true;
        }        
        else
            return false;
    }
      
    /**
     * Attempts to store item at specific position
     * @param Position
     * @return boolean
     */
    public boolean storeItem(MuInventoryItem Item, int Position) {
        int line = getLine(Position);
        int column = getColumn(Position);
        if (checkSlots(line, column, Item.getItemStats().get_xSize(),
                Item.getItemStats().get_ySize())) {
            markSlots(line, column, Item.getItemStats().get_xSize(),
                    Item.getItemStats().get_ySize(), true);
            _inventory.put(Item.getPosition(), Item);
            return true;
        }        
        else
            return false;        
    }
    
    public boolean removeItem(int Position) {
        MuInventoryItem Item = getItem(Position);
        return removeItem(Item);
    }
    
    public boolean removeItem(MuInventoryItem Item) {
        if (_inventory.containsValue(Item)) {
            markSlots(getLine(Item.getPosition()), getColumn(Item.getPosition()),
                    Item.getItemStats().get_xSize(), Item.getItemStats().get_ySize(), false);
            _inventory.remove(Item.getPosition());
            return true;
        }
        else
            return false;
    }
    
    public MuInventoryItem getItem(int Position) {
        return (MuInventoryItem) _inventory.get(Integer.valueOf(Position));
    }
    
    protected boolean checkSlots(int line, int column, byte itemXSize, byte itemYSize) {
        if (line+itemYSize-1>_invYSize)
            return false;
        if (column+itemXSize-1>_invXSize)
            return false; 
        for (int i=line; i<=line+itemYSize-1; i++)             
            for (int j=column; j<=column+itemXSize-1; j++)
                if (_slots[i][j]) 
                    return false;
        return true;
    }
    
    protected byte getAvailablePosition(byte itemXSize, byte itemYSize) {
        boolean checkPlace;
        int totalSpots = (_invXSize & 0xFF) * (_invYSize & 0xFF);
        int line;
        int column;       
        for (byte k=0; k<totalSpots-1; k++) {
            line = getLine(k);
            column = getColumn(k);
            if (line+itemYSize>_invYSize)
                continue;
            if (column+itemXSize>_invXSize)
                continue;
            checkPlace = true;
            for (int i=line; i<=line+itemYSize-1; i++)             
                for (int j=column; j<=column+itemXSize-1; j++)
                    if (_slots[i][j])
                        checkPlace = false;
            if (checkPlace)
                return k;
        }
        return 0;
    }
    
    protected void markSlots(int line, int column, byte itemXSize, byte itemYSize, boolean flag) {
        for (int i=line; i<=line+itemYSize-1; i++)
            for (int j=column; j<=column+itemXSize-1; j++)
                _slots[i][j] = flag;
    }
    
    protected int getLine(int Position) {
        return Position / _invXSize;
    }
    
    protected int getColumn(int Position) {
        return Position % _invYSize;
    }
    
}
