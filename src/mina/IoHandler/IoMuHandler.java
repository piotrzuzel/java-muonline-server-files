/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mina.IoHandler;

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
    short id;
    public IoMuHandler(short i) {
        id =i;
    }

    public void sessionCreated(IoSession session) throws Exception {
       
    }

    public void sessionOpened(IoSession session) throws Exception {
      SHello s = new SHello((id),"09928");
      session.write( s);


    }

    public void sessionClosed(IoSession session) throws Exception {
        
    }

    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        
    }

    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        
    }

    public void messageReceived(IoSession session, Object message) throws Exception {
        
    }

    public void messageSent(IoSession session, Object message) throws Exception {
        
    }

}
