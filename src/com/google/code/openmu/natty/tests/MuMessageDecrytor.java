/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.openmu.natty.tests;

import java.util.logging.Logger;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

/**
 * 
 * @author mikiones
 */
public class MuMessageDecrytor extends OneToOneEncoder {
	private final byte[] key = { (byte) 0xe7, (byte) 0x6D, (byte) 0x3a,
			(byte) 0x89, (byte) 0xbc, (byte) 0xB2, (byte) 0x9f, (byte) 0x73,
			(byte) 0x23, (byte) 0xa8, (byte) 0xfe, (byte) 0xb6, (byte) 0x49,
			(byte) 0x5d, (byte) 0x39, (byte) 0x5d, (byte) 0x8a, (byte) 0xcb,
			(byte) 0x63, (byte) 0x8d, (byte) 0xea, (byte) 0x7d, (byte) 0x2b,
			(byte) 0x5f, (byte) 0xc3, (byte) 0xb1, (byte) 0xe9, (byte) 0x83,
			(byte) 0x29, (byte) 0x51, (byte) 0xe8, (byte) 0x56 };

	/**
	 * Decrypt whole array
	 * 
	 * @param inBuffor
	 *            to decrypt
	 * @param specOffset
	 *            offset in arary
	 * @return uncrypted arry baits
	 */
	public byte[] XorDecrypt(byte[] inBuffor, int specOffset) {
		byte[] outBuffor = new byte[inBuffor.length];

		for (int i = 0; i <= specOffset; i++)
			outBuffor[i] = inBuffor[i];

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jboss.netty.handler.codec.oneone.OneToOneEncoder#handleDownstream
	 * (org.jboss.netty.channel.ChannelHandlerContext,
	 * org.jboss.netty.channel.ChannelEvent)
	 */
	@Override
	public void handleDownstream(ChannelHandlerContext ctx, ChannelEvent evt)
			throws Exception {
		Logger.getLogger(this.getClass().getName()).info(evt.toString());
		super.handleDownstream(ctx, evt);
	}

	/**
	 * Decrypt xor algorytm one bajt
	 * 
	 * @param firstByte
	 *            actual bait to decrypt
	 * @param nextByte
	 *            nextbait
	 * @param offset
	 *            position in array
	 * @return decrypted bajt
	 */
	public byte SingleByteXor(byte firstByte, byte nextByte, int offset) {
		byte stage1 = (byte) (firstByte ^ key[offset]);
		byte stage2 = (byte) (nextByte ^ stage1);
		return stage2;
	}

	public MuBaseMessage decryptMessage(MuBaseMessage message) {

		if (message.status == MuBaseMessage.READY)
			return message;
		byte[] inByteMessage = message.message.array();
		int offset = (inByteMessage[0] == (byte) 0xc1 || inByteMessage[0] == (byte) 0xc3) ? 2
				: 3;
		byte[] outByteMesage = XorDecrypt(inByteMessage, offset);

		message.message = ChannelBuffers.copiedBuffer(outByteMesage);
		message.status = MuBaseMessage.READY;
		return message;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jboss.netty.handler.codec.oneone.OneToOneEncoder#encode(org.jboss
	 * .netty.channel.ChannelHandlerContext, org.jboss.netty.channel.Channel,
	 * java.lang.Object)
	 */
	@Override
	protected Object encode(ChannelHandlerContext ctx, Channel channel,
			Object msg) throws Exception {
		if (!(msg instanceof MuBaseMessage))
			return msg;
		MuBaseMessage muMessage = (MuBaseMessage) msg;
		if (muMessage.status == MuBaseMessage.To_DECRYPT) {
			return decryptMessage(muMessage);
		} else
			return msg;
	}
}
