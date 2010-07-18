/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mina.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.demux.MessageEncoder;

/**
 *
 * @author mikiones
 */
public abstract class AbstractMuMessageEncoder<T extends AbstractMuMessageData> implements MessageEncoder<T> {

    public static final byte C1type = 1;
    public static final byte C2type = 2;

    public void encode(IoSession session, T message, ProtocolEncoderOutput out) throws Exception {

        byte type = C2type;
        if (message.getHeadType() == message.C1HEADER
                || message.getHeadType() == message.C3HEADER) {

            type = C1type;
        }
        IoBuffer buf = IoBuffer.allocate(message.getMessageSize());
        buf.setAutoExpand(true); // Enable auto-expand for easier encoding

        // Encode a header

        if (message.getHeadType() == message.C1HEADER
                || message.getHeadType() == message.C3HEADER) {

            byte[] m = {message.getHeadType(), (byte) message.getMessageSize(), message.getMessageID()};
            buf.put(m, 0, m.length);
            buf.position(m.length);

        } else {
            byte[] m = {message.getHeadType(), 0, 0, message.getMessageID()};
            buf.put(m, 0, m.length);
            buf.putShort(1, message.getMessageSize());

            buf.position(4);

        }

        // Encode a body

        encodeBody(session, message, buf);

        buf.flip();
        out.write(buf);
    }

    /**
     *
     * @param session
     * @param message
     * @param out
     */
    protected abstract void encodeBody(IoSession session, T message, IoBuffer out);
}
