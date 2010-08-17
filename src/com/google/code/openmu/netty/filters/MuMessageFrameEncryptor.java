/*
 * Copyright [mikiones] [Michal Kinasiewicz]
 * 			 [marcel]   [Marcel Gheorghita] 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.code.openmu.netty.filters;

import java.util.logging.Logger;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.handler.codec.oneone.OneToOneDecoder;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

import com.google.code.openmu.netty.abstracts.MuMessageFrame;

/**
 * 
 * @author mikiones
 */
public class MuMessageFrameEncryptor extends OneToOneDecoder {
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

	public MuMessageFrame decryptMessage(MuMessageFrame message) {

		if (message.status == MuMessageFrame.READY)
			return message;
		byte[] inByteMessage = message.message.array();
		int offset = (inByteMessage[0] == (byte) 0xc1 || inByteMessage[0] == (byte) 0xc3) ? 2
				: 3;
		byte[] outByteMesage = XorDecrypt(inByteMessage, offset);
		System.out.println(ChannelBuffers.hexDump(message.message));
		message.message = ChannelBuffers.copiedBuffer(outByteMesage);
		System.out.println(ChannelBuffers.hexDump(message.message));
		message.status = MuMessageFrame.READY;
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
	protected Object decode(ChannelHandlerContext ctx, Channel channel,
			Object msg) throws Exception {
		System.out.println("MessageDecryptor");
		if (msg instanceof MuMessageFrame) {

			MuMessageFrame muMessage = (MuMessageFrame) msg;
			if (muMessage.status == MuMessageFrame.To_DECRYPT) {
				System.out.println("Decoding");
				return decryptMessage(muMessage);
			} else
				return msg;
		} else {
			if (msg instanceof ChannelBuffer) {
				System.out.println("MuMessageDecryptor:Its channel buhher");
				System.out.println(ChannelBuffers.hexDump((ChannelBuffer) msg));
			}
			return msg;
		}
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jboss.netty.handler.codec.oneone.OneToOneDecoder#handleUpstream(org
	 * .jboss.netty.channel.ChannelHandlerContext,
	 * org.jboss.netty.channel.ChannelEvent)
	 */

}
