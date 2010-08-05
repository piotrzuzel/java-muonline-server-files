/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.jmuserver.gs.serverPackage;

/**
 *
 * @author Marcel
 */
public class SMoveItemResult extends ServerBasePacket {

    private byte[] _item;
    private int _pos;
    private int _wid;
    
    public SMoveItemResult(int Window, int Position, byte[] ItemHex) {
        _item = ItemHex;
        _pos = Position;
        _wid = Window;
    }
    
    public byte[] getContent() {
        mC3Header(0x24, 0x0A);
        writeC(_wid);
        writeC(_pos);
        writeB(_item);
        return getBytes();
    }

    public String getType() {
        return "Move item from one inventory to another.";
    }

    public boolean testMe() {
        return true;
    }
    
}
