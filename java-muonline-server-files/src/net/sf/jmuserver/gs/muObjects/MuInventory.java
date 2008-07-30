package net.sf.jmuserver.gs.muObjects;

import java.util.Map;
import javolution.util.FastMap;

/**
 * This class represents any type of inventory in game. Specific inventories
 * inherit this class and make modifications to the size and position offset.<br>
 * The position offset gives the index of the first slot in the inventory.
 * @see MuCharacterInventory
 * @see MuTradeInventory
 * @see MuVaultInventory
 * @author Marcel
 */
public class MuInventory {

    public static final int TradeWindow = (byte)0x80;
    public static final int TradeWindowXSize = (byte)0x08;
    public static final int TradeWindowYSize = (byte)0x04;
    public static final int InventoryWindow = (byte)0x00;
    public static final int InventoryWindowSize = (byte)0x08;
    public static final int VaultWindow = (byte)0x02;
    public static final int VaultWindowXSize = (byte)0x08;
    public static final int VaultWindowYSize = (byte)0x10;    
    public static final int OffsetInventoryWindow = (byte)0x0C;
    public static final int OffsetOtherWindows = (byte) 0x00;
    
    protected byte _offset;
    protected byte _invXSize;
    protected byte _invYSize;
    protected Map _inventory = new FastMap<Integer, MuInventoryItem>().setShared(true);
    public boolean[][] _slots;

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
        if (Position >= 0) {
            Position += _offset;            
            Item.setPosition(Position);
            markSlots(line, column, Item.getItemStats().get_xSize(),
                    Item.getItemStats().get_ySize(), true);
            _inventory.put(new Integer(Position), Item);
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
        Position -= _offset;
        int line = getLine(Position);
        int column = getColumn(Position);      ;
        if (checkSlots(line, column, Item.getItemStats().get_xSize(),
                Item.getItemStats().get_ySize())) {
            markSlots(line, column, Item.getItemStats().get_xSize(),
                    Item.getItemStats().get_ySize(), true);
            Position += _offset;
            Item.setPosition(Position);
            _inventory.put(new Integer(Position), Item);
            return true;
        }        
        else
            return false;        
    }
    
    /**
     * Removes the item that can be found at a given position in inventory.
     * @param Position
     * @return True is succeeded, false otherwise
     */
    public boolean removeItem(int Position) {
        MuInventoryItem Item = getItem(Position);
        return removeItem(Item);
    }
    
    /**
     * Given the item reference, it removes it from the inventory.
     * @param MuInventoryItem
     * @return True is succeeded, false otherwise
     */
    public boolean removeItem(MuInventoryItem Item) {
        if (_inventory.containsValue(Item)) {
            markSlots(getLine(Item.getPosition()-_offset), getColumn(Item.getPosition()-_offset),
                    Item.getItemStats().get_xSize(), Item.getItemStats().get_ySize(), false);
            _inventory.remove(Item.getPosition());
            return true;
        }
        else
            return false;
    }
    
    /**
     * Retrieves the MuInventoryItem reference of the item found at a
     * given position in the inventory.
     * @param Position
     * @return MuInventoryItem
     */
    public MuInventoryItem getItem(int Position) {
        return (MuInventoryItem) _inventory.get(new Integer(Position));
    }
    
    /**
     * Checks the inventory to see if an item can be stored at the given
     * line and column.
     * @param line
     * @param column
     * @param itemXSize
     * @param itemYSize
     * @return True if position in inventory is available, false otherwise
     */
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
    
    /**
     * Checks the inventory to see the first empty position at which an
     * item can be stored, given its dimensions.
     * @param itemXSize
     * @param itemYSize
     * @return The value of the available position. "-1" shows full inventory.
     */
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
        return -1;
    }
    
    /**
     * Marks inventory slots according to the flag, which indicates an empty
     * or an used slot.
     * @param line
     * @param column
     * @param itemXSize
     * @param itemYSize
     * @param flag
     */
    protected void markSlots(int line, int column, byte itemXSize, byte itemYSize, boolean flag) {
        for (int i=line; i<=line+itemYSize-1; i++)
            for (int j=column; j<=column+itemXSize-1; j++)
                _slots[i][j] = flag;
    }
    
    /**
     * Given the inventory position, the function returns the line index.
     * @param Position
     * @return line index
     */
    protected int getLine(int Position) {
        return Position / _invXSize;
    }
    
    /**
     * Given the inventory position, the function returns the column index.
     * @param Position
     * @return colum index
     */
    protected int getColumn(int Position) {
        return Position % _invYSize;
    }
    
}
