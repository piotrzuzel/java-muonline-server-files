package com.google.code.openmu.natty.tests;

import java.awt.Frame;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;
import org.jboss.netty.handler.codec.protobuf.ProtobufDecoder;

import com.mchange.v2.c3p0.impl.NewPooledConnection;
/**
 * 
 * @author mikiones
 * 
 * MuFrameDecoder decode frames into whole messages
 */
public class MuFrameDecoder extends FrameDecoder {
	MuMessageDecrytor decryptor=new MuMessageDecrytor();
	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel,
			ChannelBuffer buf) throws Exception {
	     
	     if (buf.readableBytes() < 3) { // minimal 3 bytes head|size|
	        return null;
	     }
	     //save curent position
	     buf.markReaderIndex();

	     // Read the length field.
	     int head = buf.readUnsignedByte(); //[head]
	     int frameLenght= (head==0xc1||head==0xc3)?buf.readUnsignedByte():buf.readUnsignedShort();//[size]
	     // Make sure if there's enough bytes in the buffer.
	     if (buf.readableBytes() < frameLenght) {
	        buf.resetReaderIndex();
	        return null;
	     }
	     
	     MuBaseMessage message = new MuBaseMessage();
	     message.messageID=buf.readUnsignedByte();
	     buf.resetReaderIndex(); //we read whole message
	     message.message = buf.readBytes(frameLenght);
	     message.status=MuBaseMessage.To_DECRYPT;
	     decryptor.decryptMessage(message);
	     return message;

	}

}
