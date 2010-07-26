/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.jmuserver.cs.IoHandler;

import mina.AbstractModels.MuBaseMessage;
import net.sf.jmuserver.cs.serverPackage.HelloClientData;
import net.sf.jmuserver.cs.serverPackage.ServerListData;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

/**
 *
 * @author mikiones
 */
public class CSIoHandler implements IoHandler{
    ServerListData data= new ServerListData();
    public CSIoHandler(short i) {
     data.addServer("189.0.0.1", (short) 55901,(byte)0 ,(byte) 0, (byte)1);
    }

    public void sessionCreated(IoSession session) throws Exception {
       
    }

    public void sessionOpened(IoSession session) throws Exception {
      session.write(new HelloClientData());


    }

    public void sessionClosed(IoSession session) throws Exception {
        
    }

    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        
    }

    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        
    }

    public void messageReceived(IoSession session, Object message) throws Exception {
        if (!(message instanceof MuBaseMessage)) return;
        MuBaseMessage m = (MuBaseMessage)message;
        switch (m.messageID)
        {
            case 0xf4:{
                switch (m.message.getUnsigned(3)){
                    case 0x02:
                        session.write(data);break;
                    case 0x03:
                        session.write(data.getServer( (byte)m.message.getUnsigned(4), (byte) m.message.getUnsigned(5)));

                }
            }
        }

    }

    public void messageSent(IoSession session, Object message) throws Exception {
       
    }

}
