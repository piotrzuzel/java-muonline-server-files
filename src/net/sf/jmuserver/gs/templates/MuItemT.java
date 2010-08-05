/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.jmuserver.gs.templates;

/**
 * tesst class for item
 * @author Miki i Linka
 */
public class MuItemT {

    private byte[] _item = {0, 0, 0, 0, 0}; // 5 bites

    public MuItemT() {
    }

    /**
     * constructor wich init bytes from b
     * @param b
     */
    public MuItemT(byte[] b) {
        for (int i = 0; i < 5; i++) {
            _item[i] = b[i];
        }
    }

    /**
     * read item from byte buffor
     * @param buf buffor with item
     * @param pos position in buffor
     */
    public void ReadItem(byte[] buf, int pos) {
        for (int i = 0; i <= 5; i++) {
            _item[i] = buf[i + pos];
        }
    }

    /**
     * set item bytes from buff
     * @param buf
     */
    public void setItem(byte[] buf) {
        for (int i = 0; i <= 5; i++) {
            _item[i] = buf[i];
        }
    }

    /**
     * 
     * get the bytesrepresentings item
     * @return
     */
    public byte[] getItem() {
        return _item;
    }
}
