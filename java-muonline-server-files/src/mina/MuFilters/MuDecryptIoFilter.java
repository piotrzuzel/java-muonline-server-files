/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mina.MuFilters;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.session.IoSession;

/**
 *
 * @author mikiones
 */
public class MuDecryptIoFilter extends IoFilterAdapter {

    private final byte[] key = {(byte) 0xe7, (byte) 0x6D, (byte) 0x3a, (byte) 0x89,
        (byte) 0xbc, (byte) 0xB2, (byte) 0x9f, (byte) 0x73, (byte) 0x23,
        (byte) 0xa8, (byte) 0xfe, (byte) 0xb6, (byte) 0x49, (byte) 0x5d,
        (byte) 0x39, (byte) 0x5d, (byte) 0x8a, (byte) 0xcb, (byte) 0x63,
        (byte) 0x8d, (byte) 0xea, (byte) 0x7d, (byte) 0x2b, (byte) 0x5f,
        (byte) 0xc3, (byte) 0xb1, (byte) 0xe9, (byte) 0x83, (byte) 0x29,
        (byte) 0x51, (byte) 0xe8, (byte) 0x56
    };

    /**
     * Decrypt whole array
     * @param buf to decrypt
     * @param pos offset in arary
     * @return uncrypted arry baits
     */
    public byte[] XorDecrypt(byte[] buf, int pos) {
        byte[] a = new byte[buf.length];

        a[0] = buf[0];
        byte t = 0;


        int b = pos + 1;
        for (int i = 0; i < (buf.length - 1); i++, b++) {

            if (b >= 32) {
                b = 0;
            }
            t = SingleByteXor(buf[i], buf[i + 1], b);
            a[i + 1] = t;
        }
        return a;

    }

    /**
     * Decrypt xor algorytm one bajt
     * @param a actual bait to decrypt
     * @param n nextbait
     * @param pos position in array
     * @return decrypted bajt
     */
    public byte SingleByteXor(byte a, byte n, int pos) {
        byte t = (byte) (a ^ key[pos]);
        byte t2 = (byte) (n ^ t);
        return t2;
    }

    @Override
    public void messageReceived(NextFilter nextFilter, IoSession session, Object message) throws Exception {
        if (!(message instanceof IoBuffer)) {
            nextFilter.messageReceived(session, message);
            return;
        }
        IoBuffer inBuff = (IoBuffer) message;
        byte[] in = inBuff.array();
        byte[] out = in.clone();

        switch (in[0]) {
            case (byte) 0xc1:
            case (byte) 0xc3: {
                out = XorDecrypt(in, 2);
            }
            break;
            case (byte) 0xc2:
            case (byte) 0xc4: {
                out = XorDecrypt(in, 3);
            }
            break;
        }

        IoBuffer outbuf = IoBuffer.wrap(out);
        nextFilter.messageReceived(session, outbuf);

    }
}


