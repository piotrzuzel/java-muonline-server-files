/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.google.code.openmu.cs.serverPackets;

import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.spi.CharsetProvider;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

import com.google.code.openmu.utils.Protocol;
import com.google.code.openmu.mina.codecs.AbstractMuMessageData;
import com.google.code.openmu.mina.codecs.AbstractMuMessageEncoder;


/**
 *
 * @author mikiones
 */
public class ServerAdressEnoder extends AbstractMuMessageEncoder<ServerAdressData>{

    @Override
    protected void encodeBody(IoSession session, ServerAdressData message, IoBuffer out) {
        try {
            out.put((byte) 0x03); //message id2
            out.putString(message.adres, Charset.forName("ISO-8859-1").newEncoder());
            int b = 16 - message.adres.length();
            byte [] t = new byte[b];
            out.put(t);
            out.putShort(Short.reverseBytes(message.port));
                    


        } catch (CharacterCodingException ex) {
            Logger.getLogger(ServerAdressEnoder.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
