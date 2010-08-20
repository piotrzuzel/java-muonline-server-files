/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.google.code.openmu.cs.IoHandler;

import com.google.code.openmu.cs.serverPackets.HelloClientData;
import com.google.code.openmu.cs.serverPackets.ServerAdressData;
import com.google.code.openmu.cs.serverPackets.ServerListData;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.google.code.openmu.cs.ServerList;
import com.google.code.openmu.mina.abstractModels.MuBaseMessage;

/**
 *
 * @author mikiones
 */
public class CSIoHandler implements IoHandler{
    
    public CSIoHandler(short i) {
     
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
                        session.write(new ServerListData(ServerList.getInstance().asArrayList()));break;
                    case 0x03:
                    	System.out.println((byte)m.message.getUnsigned(4));
                    	System.out.println((byte) m.message.getUnsigned(5));
                        session.write(new ServerAdressData(ServerList.getInstance().get((byte)m.message.getUnsigned(4), (byte) m.message.getUnsigned(5))));
                        
                        
                        break;

                }
            }
        }

    }

    public void messageSent(IoSession session, Object message) throws Exception {
       
    }

}
