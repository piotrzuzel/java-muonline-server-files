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
public class CEnterInGateRequest extends ClientBasePacket{

    private int fMap;// map id
    private int GateNb;// number of gate in gate.bmp
    
    
    public CEnterInGateRequest(byte[] decrypt, ClientThread _client) {
        super(decrypt);
        GateNb=decrypt[1]&0xff; 
        System.out.println("|Request to enter in gate nb:"+GateNb);
        System.out.println("|--Still ned discovery");
        System.out.println("|--decrrypr[2]="+decrypt[2]);
        System.out.println("|__decrrypr[3]="+decrypt[3]);
                
        
        
    }

    
    
    public String getType() {
    return null;
    }

}
