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
public class ServerListEncoder  extends  AbstractMuMessageEncoder<ServerListData>{

    @Override
    protected void encodeBody(IoSession session, ServerListData message, IoBuffer out) {
        out.put((byte)0x02);
        out.put((byte)message.servers.size());
        for(ServerEntry i : message.servers)
        {
            out.put((byte)i.pos);
            out.put((byte)i.grup);
            out.put((byte)i.load);
            out.put((byte)0xcc);
        }
    }

}
