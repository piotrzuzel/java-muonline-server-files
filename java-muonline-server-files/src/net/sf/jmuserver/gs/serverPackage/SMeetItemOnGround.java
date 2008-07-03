/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.jmuserver.gs.serverPackage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;
import net.sf.jmuserver.gs.muObjects.MuItemOnGround;
import net.sf.jmuserver.gs.muObjects.MuObject;

/**
 *
 * @author Miki i Linka
 */
public class SMeetItemOnGround extends ServerBasePacket {

    ArrayList<MuObject> _item;

    public SMeetItemOnGround(ArrayList<MuObject> _items) {
        _item = _items;
    }

    public byte[] getContent() throws IOException, Throwable {
        int size = (9 * _item.size()) + 5; //(size of block items+ id+wsp)* itemscout + itemcontbit + head
        mC2Header(0x20, size);
        writeC(_item.size()); // count of items
        for (int i = 0; i < _item.size(); i++) {
            if (_item.get(i) instanceof MuItemOnGround) {
                makeSub((MuItemOnGround) _item.get(i));
            }
        }
        return getBytes();
    }

    private void makeSub(MuItemOnGround i) {
        writeI(i.getObjectId()); // write id of item 2
        writeC(i.getX()); // write x pos 1 
        writeC(i.getY()); // write y pos 1
        writeB(i.getItem().getItem());// 5 bytes of item


    }

    public String getType() {
        return "meet itemson ground";
    }

    public boolean testMe() {
        return true;
    }
}
