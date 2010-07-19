package net.sf.jmuserver.gs.muObiects;

import net.sf.jmuserver.gs.templates.MuItemHex;

public class MuItemOnGround extends MuObject {

    private MuItemHex _itemHex;
    private MuItemStats _itemStats;

    public MuItemOnGround() {
        //sheld :P
        _itemHex=new MuItemHex();
        _itemHex.fromByteArray(new byte[]{(byte)0xc0, (byte) 0x00, (byte) 0x16, (byte) 0x01, (byte) 0x01},0);
        _itemStats = MuItemStats.getItemStats((byte)0xC0, (byte)0x00);
    }

//    @Override
//    public void ISpown() {
//        super.ISpown();
//        System.out.println("ISpown in ItemOnGround");
//    }    
    
    public MuItemStats getItemStats() {
        return _itemStats;
    }

    public MuItemHex getItemHex() {
        return _itemHex;
    }
    
    public void setItemStats(MuItemStats ItemStats) {
        _itemStats = ItemStats;
    }
    
    public void setItemHex(MuItemHex ItemHex) {
        _itemHex = ItemHex;
    }  
    
}
