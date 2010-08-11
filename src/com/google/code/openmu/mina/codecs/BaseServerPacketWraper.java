/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.google.code.openmu.mina.codecs;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.google.code.openmu.gs.serverPackets.ServerBasePacket;

/**
 *
 * @author mikiones
 */
public class BaseServerPacketWraper implements ProtocolEncoder  {


    public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception, IOException {
        if( message instanceof ServerBasePacket)
        {
            try {
                ServerBasePacket s = (ServerBasePacket) message;
                IoBuffer buf = IoBuffer.wrap(s.getContent());
               out.write(buf);
            } catch (Throwable ex) {
                Logger.getLogger(BaseServerPacketWraper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }

    public void dispose(IoSession session) throws Exception {

    }

}
