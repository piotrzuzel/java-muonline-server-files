/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.openmu.mina.filters;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.session.IoSession;

import com.google.code.openmu.mina.abstractModels.MuBaseMessage;

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
     * @param inBuffor to decrypt
     * @param specOffset offset in arary
     * @return uncrypted arry baits
     */
    public byte[] XorDecrypt(byte[] inBuffor, int specOffset) {
        byte[] outBuffor = new byte[inBuffor.length];

        for (int i =0 ; i<=specOffset;i++)
            outBuffor[i]= inBuffor[i];
        
        byte byteTemp = 0;

        int keyOffset = specOffset + 1;
        for (int i = 2; i < (inBuffor.length - 1); i++, keyOffset++) {

            if (keyOffset >= 32) {
                keyOffset = 0;
            }
            byteTemp = SingleByteXor(inBuffor[i], inBuffor[i + 1], keyOffset);
            outBuffor[i + 1] = byteTemp;
        }
        return outBuffor;

    }

    /**
     * Decrypt xor algorytm one bajt
     * @param firstByte actual bait to decrypt
     * @param nextByte nextbait
     * @param offset position in array
     * @return decrypted bajt
     */
    public byte SingleByteXor(byte firstByte, byte nextByte, int offset) {
        byte stage1 = (byte) (firstByte ^ key[offset]);
        byte stage2 = (byte) (nextByte ^ stage1);
        return stage2;
    }

    @Override
    public void messageReceived(NextFilter nextFilter, IoSession session, Object message) throws Exception {
         //If not MuBaseMessage pass it
        if (!(message instanceof MuBaseMessage)) {
            nextFilter.messageReceived(session, message);
            return;
        } else { // if allreadyencrypted pass it
            if (((MuBaseMessage) message).status != MuBaseMessage.To_DECRYPT) {
                nextFilter.messageReceived(session, message);
                return;
            }
        }

        MuBaseMessage muMessage = (MuBaseMessage) message;
        muMessage.message.position(0);
        byte[] inByteMessage = muMessage.message.array();
        
        int offset = (inByteMessage[0] == (byte)0xc1 || inByteMessage[0] == (byte)0xc3)? 2 :3 ;
        byte[] outByteMesage = XorDecrypt(inByteMessage, offset);

        muMessage.message = IoBuffer.wrap(outByteMesage);
        muMessage.status=MuBaseMessage.READY;

        nextFilter.messageReceived(session, muMessage);

    }
}


