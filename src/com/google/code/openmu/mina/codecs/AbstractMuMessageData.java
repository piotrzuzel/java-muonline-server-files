/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.google.code.openmu.mina.codecs;

/**
 * The interface for eny package
 * @author mikiones
 */
public abstract interface  AbstractMuMessageData {

    public static final byte C1HEADER=(byte)0xc1;
    public static final byte C2HEADER=(byte)0xc2;
    public static final byte C3HEADER=(byte)0xc3;
    public static final byte C4HEADER=(byte)0xc4;
    /**
     * the method must return MessageHeadId C1 ||C2 || c3 || c4
     * @return MessageHeadId C1 ||C2 || c3 || c4
     */
    public abstract byte getHeadType();
    /**
     *
     * @return the first message opcode byte value
     */
    public abstract byte getMessageID();

    /**
     * 
     * @return the message size in bytes
     */
    public abstract short getMessageSize();
}
