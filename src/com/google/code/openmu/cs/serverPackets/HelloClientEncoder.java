/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.google.code.openmu.cs.serverPackets;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

import com.google.code.openmu.mina.codecs.AbstractMuMessageEncoder;

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
