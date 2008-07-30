/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.jmuserver.gs.serverPackage;

import java.io.IOException;

/**
 *
 * @author Miki
 */
public class SInwentoryList extends ServerBasePacket {

    private byte pac[] = {(byte) 0xc4, (byte) 0x00, (byte) 0x0b, (byte) 0xf3, (byte) 0x10, (byte) 0x01, (byte) 0x0c, (byte) 0xe3, (byte) 0x00, (byte) 0x00, (byte) 0x80, (byte) 0x00, 0x00};
    private byte pac1[] = {(byte) 0xc3, (byte) 0x0c, (byte) 0x24, (byte) 0x00, (byte) 0x00, (byte) 0xc0, (byte) 0x00, (byte) 0x16, (byte) 0x00, (byte) 0x00, (byte) 0xff, (byte) 0x0c};

    public SInwentoryList() {
        super();
        _bao.write(pac1, 0, pac1.length);
    }

    @Override
    public byte[] getContent() throws IOException, Throwable {
byte t[]={
    (byte)0xc4 ,(byte)0x00 ,
    (byte)12 ,(byte)0xf3 ,
    (byte)0x10 ,(byte)0x01 ,
    (byte)0x0f ,(byte)0x20 ,
    (byte)0x00 ,(byte)0x12  ,
    (byte)0x00 ,(byte)0x00// ,
    //(byte)0x0c 
};
        return t;
    }

    @Override
    public String getType() {
        return "";
    }

    @Override
    public boolean testMe() {
        return true;
    }
}
