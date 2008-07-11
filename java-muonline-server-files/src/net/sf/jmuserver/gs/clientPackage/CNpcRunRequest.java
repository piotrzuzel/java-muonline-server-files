/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.jmuserver.gs.clientPackage ;

import net.sf.jmuserver.gs.ClientThread;
import net.sf.jmuserver.gs.muObjects.MuNpcInstance;
import net.sf.jmuserver.gs.muObjects.MuWorld;

/**
 *
 * @author Miki i Linka
 */
public class CNpcRunRequest extends ClientBasePacket{

    private int _idNpcToRun=0;
    public CNpcRunRequest(byte[] decrypt, ClientThread _client) {
       
       
	super(decrypt);
        _idNpcToRun=decrypt[1];
        System.out.print("Request to npc it :"+_idNpcToRun+" ");
      Object o =  MuWorld.getInstance().findObject(_idNpcToRun);
      if (o instanceof MuNpcInstance) 
      {
          MuNpcInstance npc=(MuNpcInstance)o;
          System.out.println("object name '"+npc.getName()+"'");
      } else 
            System.out.println("error objct requested is not kind of munpcinstance!!");
        
        
    }


    
    @Override
    public String getType() {
       return "0x30 Run Npc request";
    }

}
