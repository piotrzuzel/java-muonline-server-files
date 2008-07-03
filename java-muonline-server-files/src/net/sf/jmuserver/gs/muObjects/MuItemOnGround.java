package net.sf.jmuserver.gs.muObjects;

import net.sf.jmuserver.gs.templates.MuItemT;

public class MuItemOnGround extends MuObject {

    private MuItemT _itemHex;

    public MuItemT getItem() {
        return _itemHex;
    }

    public MuItemOnGround() {
        //sheld :P
        _itemHex=new MuItemT(new byte[]{(byte)0xc0, (byte) 0x00, (byte) 0x16, (byte) 0x00, (byte) 0x00});
        
    }

    @Override
    public void ISpown() {
        super.ISpown();
        System.out.println("ISpoe in ItemOnGround");
    }
    
    
}
