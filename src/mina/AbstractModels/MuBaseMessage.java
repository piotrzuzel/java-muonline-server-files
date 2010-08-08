/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mina.AbstractModels;

import org.apache.mina.core.buffer.IoBuffer;

/**
 *
 * @author mikiones
 * The representation of mu message
 * after reived bufor and fragmented it into each package piece is wraped into this class
 * the buffor have 2 states:
 * To_DERYPT need to be decrypted
 * READY     data ready to move on next layer (decoding)
 */
public class MuBaseMessage {
    public static final String[] StrStatus={"To Decrypt" , "Ready"};
    public static final byte To_DECRYPT = 0x00;
    
    public static final byte READY = 0x01;
    /**
     * Message
     */
    public IoBuffer message;
    /**
     * Status of message
     */
    public byte status;

    @Override
    public String toString() {
        return "Message [status:" + StrStatus[status] + "] data [" + message.getHexDump() + "]";
    }
}
