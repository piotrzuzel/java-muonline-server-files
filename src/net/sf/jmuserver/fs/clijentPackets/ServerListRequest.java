/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.jmuserver.fs.clijentPackets;

import net.sf.jmuserver.fs.FriendTheard;
import net.sf.jmuserver.gs.clientPackage.ClientBasePacket;
/**
 *
 * @author mikiones
 */
public class ServerListRequest extends ClientBasePacket {

    public ServerListRequest(byte[] data, FriendTheard _friend) {
        super(data);
        System.out.println("CS:> Request server list");
    }

    @Override
    public String getType() {
        return "";
    }

}
