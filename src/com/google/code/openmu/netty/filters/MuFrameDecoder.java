package com.google.code.openmu.netty.filters;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

import com.google.code.openmu.netty.abstracts.MuBaseMessage;

/**
 * 
 * @author mikiones
 * 
 * The MuFrameDecoder  see {@link FrameDecoder} decode income data into separated <br>
 * frames {@link MuBaseMessage} with flag encode
 * 
 */
public class MuFrameDecoder extends FrameDecoder {
	MuMessageDecrytor decryptor = new MuMessageDecrytor();

	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel,
			ChannelBuffer buf) throws Exception {
	
		if (buf.readableBytes() < 1) { // minimal 1 bytes head|size|
			
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

		//insert data into MuBaseMesage
		MuBaseMessage message = new MuBaseMessage();
		message.messageID=buf.readUnsignedByte(); // read the spec byte
		buf.resetReaderIndex();		// return to begin of buffer
		message.message = buf.readBytes(frameLenght); //coppy all bytes to meddage
		message.status = MuBaseMessage.READY; //set flag to ... well supose to be ToEncode :P
		return message;

	}

	
	
}
