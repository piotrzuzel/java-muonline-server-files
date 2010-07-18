/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mina.codec.CS2CL;

import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.spi.CharsetProvider;
import java.util.logging.Level;
import java.util.logging.Logger;
import mina.codec.AbstractMuMessageData;
import mina.codec.AbstractMuMessageEncoder;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import sun.nio.cs.SingleByteEncoder;

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
            out.put((byte)0x00);
            out.putShort(Short.reverseBytes(message.port));



        } catch (CharacterCodingException ex) {
            Logger.getLogger(ServerAdressEnoder.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
