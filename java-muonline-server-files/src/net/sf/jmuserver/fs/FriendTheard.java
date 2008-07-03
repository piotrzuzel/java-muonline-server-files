/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.jmuserver.fs;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jmuserver.gs.MuConnection;

/**
 *
 * @author Miki i Linka
 */
public class FriendTheard extends Thread{
 private byte[] _cryptkey = {(byte) 0x94, (byte) 0x35, (byte) 0x00,
        (byte) 0x00, (byte) 0xa1, (byte) 0x6c, (byte) 0x54, (byte) 0x87 };
    private MuConnection _connection;
    private net.sf.jmuserver.fs.PacketHandler _handler;
    public FriendTheard(Socket _con) throws IOException {
        _connection = new MuConnection(_con, _cryptkey);
        _handler = new net.sf.jmuserver.fs.PacketHandler(this);
        start();
    }

    public MuConnection getConnection() {
        return _connection;
        
    }

    @Override
    public void run() {
         boolean _while = true;
            while (_while) {
            try {

                // System.out.println("czekam na odpowiedz");
                byte[] decrypt = _connection.getPacket();
                if (decrypt != null) {
                    _handler.handlePacket(decrypt);
                } else {
                    _while = false;
                }
                // System.out.println("odebralem");
            } catch (IOException ex) {
                Logger.getLogger(FriendTheard.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Throwable ex) {
                Logger.getLogger(FriendTheard.class.getName()).log(Level.SEVERE, null, ex);
           
            }
            // System.out.println("odebralem");
    }
    
    }
}
