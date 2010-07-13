/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.jmuserver.gs.clientPackage;

import net.sf.jmuserver.gs.ClientThread;

/**
 *
 * @author Miki i Linka
 */
public class CItemPickUpRequest extends ClientBasePacket {
  int id; // id wich item to get

    public CItemPickUpRequest(byte[] decrypt, ClientThread _client) {
        super(decrypt) ;
        //decrypt[1]=0x00 :// to fix with |0x80
        id=decrypt[2];
        System.out.println("Request to pickup item id:"+id);
    }
    @Override
    public String getType() {
       return "pck up item";
    }
  //0x22 opcode
  
  
}
