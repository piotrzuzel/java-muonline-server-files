/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.jmuserver.gs.clientPackage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jmuserver.gs.ClientThread;
import net.sf.jmuserver.gs.muObjects.MuGate;
import net.sf.jmuserver.gs.muObjects.MuWorld;
import net.sf.jmuserver.gs.serverPackage.SGateEnterAnsfer;

/**
 *
 * @author Miki i Linka
 */
public class CEnterInGateRequest extends ClientBasePacket {

    private int fMap;// map id
    private int GateNb;// number of gate in gate.bmp

    public CEnterInGateRequest(byte[] decrypt, ClientThread _client) {
        super(decrypt);
        GateNb = decrypt[1] & 0xff;
        System.out.println("|Request to enter in gate nb:" + GateNb);
        MuGate gate = MuWorld.getInstance().getGate(GateNb);
        MuGate gateTo = MuWorld.getInstance().getGate(gate.getToGate());
        System.out.println("|--Still ned discovery");
        System.out.println("|--decrrypr[2]=" + decrypt[2]);
        System.out.println("|__decrrypr[3]=" + decrypt[3]);
        try {
            _client.getConnection().sendPacket(new SGateEnterAnsfer(gateTo));
        } catch (IOException ex) {
            Logger.getLogger(CEnterInGateRequest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Throwable ex) {
            Logger.getLogger(CEnterInGateRequest.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    public String getType() {
        return null;
    }
}
