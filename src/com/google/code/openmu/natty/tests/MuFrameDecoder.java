package com.google.code.openmu.natty.tests;

import java.awt.Frame;
import java.util.logging.Logger;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;
import org.jboss.netty.handler.codec.protobuf.ProtobufDecoder;

import com.mchange.v2.c3p0.impl.NewPooledConnection;

/**
 * 
 * @author mikiones
 * 
 *         MuFrameDecoder decode frames into whole messages
 */
public class MuFrameDecoder extends FrameDecoder {
	MuMessageDecrytor decryptor = new MuMessageDecrytor();

	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel,
			ChannelBuffer buf) throws Exception {
		System.out.println("MuFrameDecoder");
		if (buf.readableBytes() < 2) { // minimal 3 bytes head|size|
			System.out.println("MuFrameDecoder:not enof bytes");
			return null;
		}
		// save curent position
		buf.markReaderIndex();

		// Read the length field.
		int head = buf.readUnsignedByte(); // [head]
		int of = 0;
		int frameLenght = 0;
		if (head == 0xc1 || head == 0xc3) {
			frameLenght=buf.readUnsignedByte();
			of=2;
		} else {
			frameLenght=buf.readUnsignedShort();// [size]
			of=3;
		}
		// Make sure if there's enough bytes in the buffer.
		if (buf.readableBytes()+of < frameLenght) {
			buf.resetReaderIndex();

			return null;
		}

		MuBaseMessage message = new MuBaseMessage();
		buf.resetReaderIndex();		
		
		message.message = buf.readBytes(frameLenght);
		
		message.status = MuBaseMessage.READY;
		return message;

	}

	
	
}
