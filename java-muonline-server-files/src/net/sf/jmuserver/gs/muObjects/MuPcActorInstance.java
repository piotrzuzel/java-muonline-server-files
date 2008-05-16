

package net.sf.jmuserver.gs.muObjects;

import net.sf.jmuserver.gs.serverPackage.ServerBasePacket;

/**
 * test class actor for pc instances to test interactivings betwens players
 * 
 * @author Miki 
 */
public class MuPcActorInstance extends MuPcInstance{

    @Override
    public void sendPacket(ServerBasePacket packet) {
    System.out.println("pruba wyslaniapakietu przez aktora :");
    System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
    System.out.println(packet.printData(packet.getBytes(), packet.getBytes().length, ""));
    System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
    }

    
    
    
    

}
