package mina.codec;

import mina.AbstractModels.MuBaseMessage;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.demux.MessageDecoderResult;

public class BaseSererMessageEncoder implements ProtocolDecoder {

	@Override
	public void dispose(IoSession arg0) throws Exception {
		

	}

	

	@Override
	public void decode(IoSession arg0, IoBuffer in, ProtocolDecoderOutput out)
			throws Exception {
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
            return ;
        }

        byte[] messageBuffor = new byte[size];
        in.get(messageBuffor, 0, size);
        in.position(size);
        muMessage.message = IoBuffer.wrap(messageBuffor);
        muMessage.status = MuBaseMessage.To_DECRYPT;
        muMessage.messageID = mesageid;
        out.write(muMessage);
        return ;
	        
		
	}

	@Override
	public void finishDecode(IoSession arg0, ProtocolDecoderOutput arg1)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
