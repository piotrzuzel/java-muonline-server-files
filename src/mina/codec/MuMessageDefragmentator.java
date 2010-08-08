/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mina.codec;

import java.nio.IntBuffer;
import mina.AbstractModels.MuBaseMessage;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

/**
 *
 * @author mikiones
 * Decoder to split incoming data to each of package
 */
public class MuMessageDefragmentator implements  ProtocolDecoder{

    
    public void decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
        MuBaseMessage muMessage = new MuBaseMessage();
        short headOfHeader = in.getUnsigned(0);
        int size=   ((headOfHeader == 0xC1 || headOfHeader == 0xC3) ? in.getUnsigned(1) : in.getUnsignedShort(1));
        byte [] messageBuffor = new byte[size];
        in.get(messageBuffor, 0, size);
        muMessage.message = IoBuffer.wrap(messageBuffor);
        muMessage.status=muMessage.To_DECRYPT;
        out.write(muMessage);

    }

    public void finishDecode(IoSession session, ProtocolDecoderOutput out) throws Exception {

    }

    public void dispose(IoSession session) throws Exception {

    }

}
