/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.jmuserver.cs.serverPackage;

import mina.codec.AbstractMuMessageEncoder;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

/**
 *
 * @author mikiones
 */
public class HelloClientEncoder extends  AbstractMuMessageEncoder<HelloClientData>{

    
    public void dispose(IoSession session) throws Exception {

    }

    @Override
    protected void encodeBody(IoSession session, HelloClientData message, IoBuffer out) {
        out.put((byte)0x01);
    }

}
