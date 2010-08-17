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

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

import com.google.code.openmu.netty.abstracts.MuMessageFrame;

/**
 * 
 * @author mikiones
 * 
 * The MuFrameDecoder  see {@link FrameDecoder} decode income data into separated <br>
 * frames {@link MuMessageFrame} with flag encode
 * 
 */
public class MuFrameDecoder extends FrameDecoder {
	MuMessageFrameEncryptor decryptor = new MuMessageFrameEncryptor();

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
		MuMessageFrame message = new MuMessageFrame();
		message.messageID=buf.readUnsignedByte(); // read the spec byte
		buf.resetReaderIndex();		// return to begin of buffer
		message.message = buf.readBytes(frameLenght); //coppy all bytes to meddage
		message.status = MuMessageFrame.READY; //set flag to ... well supose to be ToEncode :P
		return message;

	}

	
	
}
