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
 * @author Miki i Linka, MarcelGh
 */
public class CEnterInGateRequest extends ClientBasePacket {

    private int fMap;// map id
    private int GateNb;// number of gate in gate.bmp
    private int x; // x coordinate
    private int y; // y coordinate
    private int direction; // facing direction

    public CEnterInGateRequest(byte[] decrypt, ClientThread _client) {
        super(decrypt);
        GateNb = decrypt[1] & 0xff;
       // System.out.println("Request to enter in gate id:" + GateNb);
      //  fMap = decrypt[2] & 0xff;
     //   x = decrypt[3] & 0xff;
      //  y = decrypt[4] & 0xff;
      //  direction = decrypt[5] & 0xff;
        // TODO: must test packet mapid and coordinates to match current chars mapid and coords
        // and if those coords are in range defined by the gate and only then perform teleport
        // WARNING: sometimes received coordinates are 0x00 and direction is not valid
        MuGate gate = MuWorld.getInstance().getGate(GateNb);
        MuGate gateTo = MuWorld.getInstance().getGate(gate.getToGate());
        try {
            _client.getConnection().sendPacket(new SGateEnterAnsfer(gateTo, 
                    _client.getActiveChar().getDirection()));
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
