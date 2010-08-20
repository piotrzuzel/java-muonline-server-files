/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.openmu.natty.tests;




/**
 *
 * @author mikiones
 * Decoder to split incoming data to each of package
 */
public class MuMessageDecoder  {

  
   

    

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
