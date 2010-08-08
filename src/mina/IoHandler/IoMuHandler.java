/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mina.IoHandler;

import mina.AbstractModels.MuBaseMessage;
import mina.codec.CS2CL.HelloClientData;
import mina.codec.CS2CL.ServerListData;
import net.sf.jmuserver.gs.serverPackage.SHello;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

/**
 *
 * @author mikiones
 */
public class IoMuHandler implements IoHandler{
    ServerListData data= new ServerListData();
    public IoMuHandler(short i) {
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
                        session.write(data);
                }
            }
        }

    }

    public void messageSent(IoSession session, Object message) throws Exception {
        
    }

}
