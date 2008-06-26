/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.jmuserver.gs.serverPackage;

import java.io.IOException;

/**
 *
 * @author Miki i Linka
 */
public class SItemMoveInInwentory extends ServerBasePacket {
public static final byte InwentoryWnd=0x00;
public static final byte TradeWnd=(byte) 0x80;
public static final byte FromNowhere=(byte)0xff;  //The item is being dropped on another item (2 apples on 2 apples, we get 3 and 1 apple)
public static final byte ValoutWnd=0x01;
private byte[] item = {(byte)0xC3,(byte)0x0A  ,(byte)0x24  ,(byte)0x00  ,(byte)0x0D  ,(byte)0xB4  ,(byte)0x10  ,(byte)0x20  ,(byte)0xC0  ,(byte)0x00};

    public byte[] getContent() throws IOException, Throwable {
      return item;
    }

    public String getType() {
        return "Item move in inwentory";
    }

    public boolean testMe() {
        return true;
    }

}
