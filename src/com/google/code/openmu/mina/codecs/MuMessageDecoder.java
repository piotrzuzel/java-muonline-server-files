/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.openmu.mina.codecs;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.demux.MessageDecoder;
import org.apache.mina.filter.codec.demux.MessageDecoderResult;

import com.google.code.openmu.mina.abstractModels.MuBaseMessage;

/**
 *
 * @author mikiones
 * Decoder to split incoming data to each of package
 */
public class MuMessageDecoder implements MessageDecoder {

    public void finishDecode(IoSession session, ProtocolDecoderOutput out) throws Exception {
    }

    public void dispose(IoSession session) throws Exception {
    }

    public MessageDecoderResult decodable(IoSession session, IoBuffer in) {

        if(!(in.getUnsigned(0)==0xc1||in.getUnsigned(0)==0xc2||in.getUnsigned(0)==0xc3||in.getUnsigned(0)==0xc4))return NOT_OK;
        short headOfHeader = in.getUnsigned(0);
        int size = ((headOfHeader == 0xC1 || headOfHeader == 0xC3) ? in.getUnsigned(1) : in.getUnsignedShort(1));
        if (in.remaining() < size) return NEED_DATA;
        return OK;

    }

    public MessageDecoderResult decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
      
        MuBaseMessage muMessage = new MuBaseMessage();
        short headOfHeader = in.getUnsigned(0);
        int size;
        short mesageid;
        if (headOfHeader == 0xC1 || headOfHeader == 0xC3) {
            size = in.getUnsigned(1);
            mesageid=in.getUnsigned(2);
        } else
        {
             size= in.getUnsignedShort(1);
             mesageid=in.getUnsigned(3);
        }
        if (in.remaining() < size) {
            return NEED_DATA;
        }

        byte[] messageBuffor = new byte[size];
        in.get(messageBuffor, 0, size);
        in.position(size);
        muMessage.message = IoBuffer.wrap(messageBuffor);
        muMessage.status = MuBaseMessage.To_DECRYPT;
        muMessage.messageID = mesageid;
        out.write(muMessage);
        return MessageDecoderResult.OK;

    }
}
